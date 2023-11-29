const transpose = (o1) => {
    const o2 = {};
    Object.keys(o1).forEach((key) => (o2[o1[key]] = key));
    return o2;
};

exports.transpose = transpose;
