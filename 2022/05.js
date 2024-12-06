lines = document.querySelector('pre').textContent.split('\n').slice(10).filter(s => s != '')

initial_stacks = ['RNPG', 'TJBLCSVH', 'TDBMNL', 'RVPSB', 'GCQSWMVH', 'WQSCDBJ', 'FQL', 'WMHTDLFV', 'LPBVMJF']

stacks = initial_stacks.map(s => [...s])

lines.forEach(line => {
  parts = line.split(' ')
  amount = parts[1]
  from = parts[3] - 1
  to = parts[5] - 1

  for (i = 0; i < amount; i++) {
    crate = stacks[from].pop()
    stacks[to].push(crate)
  }
})

part1 = stacks.map(s => s.pop()).join('')
console.log(part1)

stacks = initial_stacks.map(s => [...s])

lines.forEach(line => {
  parts = line.split(' ')
  amount = parts[1]
  from = parts[3] - 1
  to = parts[5] - 1

  crate = stacks[from].splice(-amount, amount)
  stacks[to].push(...crate)
})

part2 = stacks.map(s => s.pop()).join('')
console.log(part2)
