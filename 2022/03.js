lines = document.querySelector('pre').textContent.split('\n').filter(line => line != '')

split_half = arr => {
  half = arr.length / 2
  return [arr.slice(0, half), arr.slice(half, half * 2)]
}

to_priority = arr => {
  return arr.map(c => {
    code = c.charCodeAt(0)
    if (code < 97) return code - 38
    else return code - 96
  })
}

sum = (a, b) => a + b
parse_int = s => parseInt(s)
to_chars = s => [...s]
range = len => [...Array(len).keys()];
chunked = (arr, num) => range(arr.length).filter(i => i % num == 0).map(i => arr.slice(i, i + num))
all_include = arr => range(53).filter(i => arr.every(sub => sub.includes(i)))

part1 = lines
  .map(to_chars)
  .map(split_half).map(arr => arr.map(to_priority))
  .map(all_include)
  .flat()
  .map(parse_int)
  .reduce(sum)

console.log(part1)

part2 = chunked(lines, 3)
  .map(arr => arr.map(to_chars).map(to_priority))
  .map(all_include)
  .flat()
  .map(parse_int)
  .reduce(sum)

console.log(part2)
