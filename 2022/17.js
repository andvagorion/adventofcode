range = len => [...Array(len).keys()]

TYPE = 0
POS = 1
X = 0
Y = 1

print = (cavern, rock) => {
  out = [...cavern].map(row => row.map(col => col ? '#' : '.'))
  if (rock != null) rock_at(rock[POS][X], rock[POS][Y], rock[TYPE]).forEach(pos => out[pos[Y]][pos[X]] = '@')
  console.log(out.reverse().map(row => '|' + row.join('') + '|').join('\n') + '\n+-------+')
}

jets = document.querySelector('pre').textContent.split('').filter(s => s != '\n')

highest_point = cavern => {
  return range(cavern.length).reduce((acc, y) => {
    if (cavern[y].some(x => x)) return y
    return acc
  }, -1)
}

HOR_LINE = 0
PLUS = 1
L_SHAPE = 2
VER_LINE = 3
SQUARE = 4

rock_at = (x, y, type) => {
  if (type == HOR_LINE) return range(4).map(dx => [x + dx, y])
  if (type == PLUS) return [ [x + 1, y], ...range(3).map(dx => [x + dx, y + 1]), [x + 1, y + 2] ]
  if (type == L_SHAPE) return [ ...range(3).map(dx => [x + dx, y]), [x + 2, y + 1], [x + 2, y + 2] ]
  if (type == VER_LINE) return range(4).map(dy => [x, y + dy])
  if (type == SQUARE) return range(2).map(dy => range(2).map(dx => [x + dx, y + dy])).flat()
  else return []
}

check_collision = (cavern, rock) => {
  cells = rock_at(rock[POS][X], rock[POS][Y], rock[TYPE])
  if (cells.some(cell => cell[Y] < 0)) return true
  if (cells.some(cell => cell[X] < 0)) return true
  if (cells.some(cell => cell[X] >= cavern[0].length)) return true
  return cells.some(cell => cavern[cell[Y]][cell[X]])
}

copy_of = rock => [rock[TYPE], [rock[POS][X], rock[POS][Y]]]

cavern = range(1).map(y => range(7).map(x => false))
expand_cavern = () => cavern.push(range(7).map(x => false))

jet_idx = -1
rock_idx = -1
current_rock = null

spawn_rock = () => {
  rock_idx = (rock_idx + 1) % 5
  x = 2
  y = highest_point(cavern) + 4
  new_rock = [rock_idx, [x, y]]
  return new_rock
}

fall = (rock) => {
  next_pos = copy_of(rock)
  next_pos[POS][Y] -= 1

  if (check_collision(cavern, next_pos)) {
    rock_at(rock[POS][X], rock[POS][Y], rock[TYPE]).forEach(pos => cavern[pos[Y]][pos[X]] = true)
    return null
  } else {
    return next_pos
  }
}

jet_move = (rock) => {
  next_pos = copy_of(rock)
  
  jet_idx = (jet_idx + 1) % jets.length
  if (jets[jet_idx] == '>') next_pos[POS][X] += 1
  else next_pos[POS][X] -= 1
  
  if (!check_collision(cavern, next_pos)) return next_pos
  return rock
}

num_rocks = 0
while (num_rocks < 2023) {
  if (current_rock != null) current_rock = fall(current_rock)
  
  if (current_rock == null) {
    current_rock = spawn_rock()
    num_rocks += 1
  }
  
  while (current_rock[POS][Y] + 4 > cavern.length) expand_cavern()
  
  current_rock = jet_move(current_rock)
}

// print(cavern, current_rock)
console.log(highest_point(cavern) + 1)
