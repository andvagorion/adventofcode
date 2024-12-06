const { range, parse_int, sum, as_lines } = require("./util/util");

const lines = as_lines("07");

const counts = (arr) => {
    return arr.reduce((obj, a) => ({ ...obj, [a]: (obj[a] || 0) + 1 }), {});
};

const strength = [
    "A",
    "K",
    "Q",
    "J",
    "T",
    "9",
    "8",
    "7",
    "6",
    "5",
    "4",
    "3",
    "2",
].reverse();

const of_a_kind = (cards, num) =>
    cards.some((card) => cards.filter((other) => card == other).length == num);

const five_of_a_kind = (cards) => of_a_kind(cards, 5);
const four_of_a_kind = (cards) => of_a_kind(cards, 4);
const full_house = (cards) => {
    const card_counts = counts(cards);
    return (
        Object.keys(card_counts).length == 2 &&
        Object.values(card_counts).reduce(sum, 0) == 5
    );
};
const three_of_a_kind = (cards) => of_a_kind(cards, 3);
const two_pair = (cards) => {
    const card_counts = counts(cards);
    return (
        Object.keys(card_counts).filter(
            (card) => cards.filter((other) => card == other).length == 2
        ).length == 2
    );
};
const one_pair = (cards) => of_a_kind(cards, 2);

const to_check = [
    five_of_a_kind,
    four_of_a_kind,
    full_house,
    three_of_a_kind,
    two_pair,
    one_pair,
];

const power = (hand) => {
    let max = 0;
    to_check.forEach((fn, i) => {
        const curr = fn(hand);
        if (curr > max) {
            max = to_check.length - i;
        }
    });
    return max;
};

const hands = lines.map((line) => line.split(" "));

hands.forEach((hand) => {
    const cards = hand[0].split("");
    hand[2] = power(cards);
});

const compare_by_card = ([...hand1], [...hand2]) => {
    for (let i = 0; i < hand1.length; i++) {
        const c1 = hand1[i];
        const c2 = hand2[i];

        if (c1 != c2) return strength.indexOf(c1) - strength.indexOf(c2);
    }
    return 0;
};

hands.sort((hand1, hand2) => {
    if (hand1[2] < hand2[2]) return -1;
    else if (hand2[2] < hand1[2]) return 1;
    else return compare_by_card(hand1[0], hand2[0]);
});


const part1 = hands.map((hand, idx) => {
    const bid = hand[1]
    return bid * (idx + 1)
}).reduce(sum, 0)

console.log(part1)
