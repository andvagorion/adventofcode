const { range, as_lines } = require("./util/util");

const lines = as_lines("20");

class FlipFlop {
    constructor(id) {
        this.id = id;
        this.state = 0;
        this.outputs = [];
    }

    add_output(output) {
        this.outputs.push(output);
    }

    receive(id, signal) {
        // HIGH signal => nothing happens
        if (signal === 1) return [this.id, [], this.state];
        this.state = this.state === 1 ? 0 : 1;
        return [this.id, this.outputs, this.state];
    }
}

class Conjunction {
    constructor(id) {
        this.id = id;
        this.inputs = {};
        this.outputs = [];
    }

    add_input(id) {
        this.inputs[id] = 0;
    }

    add_output(output) {
        this.outputs.push(output);
    }

    all_high() {
        return Object.values(this.inputs).every((s) => s === 1);
    }

    receive(id, signal) {
        this.inputs[id] = signal;
        const out_signal = this.all_high() ? 0 : 1;
        return [this.id, this.outputs, out_signal];
    }
}

class Broadcaster {
    constructor(id) {
        this.id = id;
        this.outputs = [];
    }

    add_output(output) {
        this.outputs.push(output);
    }

    receive(id, signal) {
        return [this.id, this.outputs, signal];
    }
}

const nodes = new Map();
const dests = new Map();

lines.forEach((line) => {
    const [name, outputs] = line.split(" -> ");

    let id;
    let node;

    if (name.startsWith("&")) {
        id = name.substring(1);
        node = new Conjunction(id);
    } else if (name.startsWith("%")) {
        id = name.substring(1);
        node = new FlipFlop(id);
    } else {
        id = name;
        node = new Broadcaster(id);
    }

    nodes.set(id, node);
    dests.set(id, outputs.split(", "));
});

for (const [id, outs] of dests.entries()) {
    outs.forEach((out) => {
        const node = nodes.get(id);
        node.add_output(out);

        if (nodes.has(out)) {
            const other = nodes.get(out);
            if ("add_input" in other) other.add_input(node.id);
        }
    });
}

let high = 0;
let low = 0;

range(1000).forEach(() => {
    const signals = [() => nodes.get("broadcaster").receive("button", 0)];
    low += 1;

    while (signals.length > 0) {
        const fn = signals.shift();
        const [id, destinations, signal] = fn();

        destinations.forEach((dest) => {
            if (signal === 1) high += 1;
            else low += 1;

            // console.log(`${id} sending ${signal} to ${dest}`);

            if (nodes.has(dest)) {
                signals.push(() => nodes.get(dest).receive(id, signal));
            }
        });
    }
});

console.log(low * high);
