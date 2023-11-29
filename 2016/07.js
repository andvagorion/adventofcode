const { sliding_window } = require("./util/array");
const { as_lines } = require("./util/load");
const {
    is_palindrome,
    contains_palindrome,
    sliding_window_str,
} = require("./util/text");

const lines = as_lines("07");

const parse = (addr) => {
    const parts = { supernet: [], hypernet: [] };

    let buffer = [];

    addr.split("").forEach((c) => {
        if (c == "[") {
            parts.supernet.push(buffer.join(""));
            buffer = [];
        } else if (c == "]") {
            parts.hypernet.push(buffer.join(""));
            buffer = [];
        } else {
            buffer.push(c);
        }
    });

    if (buffer.length > 0) parts.supernet.push(buffer.join(""));

    return parts;
};

const is_tls = (addr) => {
    const parts = parse(addr);

    const supernet_contains_palindrome = parts.supernet.some((s) =>
        contains_palindrome(s, 4)
    );
    const hypernet_contains_palindrome = parts.hypernet.some((s) =>
        contains_palindrome(s, 4)
    );

    return supernet_contains_palindrome && !hypernet_contains_palindrome;
};

const tls = lines.filter(is_tls).length;
console.log(tls);

const is_aba = (str) => {
    return str[0] == str[2] && str[0] != str[1];
};

const find_aba = (str) => {
    return sliding_window_str(str, 3).filter(is_aba);
};

const is_ssl = (addr) => {
    const parts = parse(addr);

    const ABAs = parts.supernet.map(find_aba).flat();

    if (ABAs.length == 0) return false;

    const BABs = parts.hypernet.map(find_aba).flat();

    return ABAs.some((aba) => {
        const bab = aba[1] + aba[0] + aba[1];
        return BABs.includes(bab);
    });
};

const ssl = lines.filter(is_ssl).length;
console.log(ssl);
