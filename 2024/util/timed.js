const timed = (fn) => {
  const start = Date.now();

  fn();

  const end = Date.now();
  console.log(end - start, "ms");
};

module.exports = {
  timed,
};
