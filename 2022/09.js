text = document.querySelector('pre').textContent.split('\n').filter(s => s != '')

X = 0, Y = 1
range = len => [...Array(len).keys()];

to_dir = s => {
  if (s == 'R') return [1, 0]
  if (s == 'L') return [-1, 0]
  if (s == 'D') return [0, 1]
  if (s == 'U') return [0, -1]
}

add = (arr, dir) => [arr[X] + dir[X], arr[Y] + dir[Y]]

is_diag = (a1, a2) => a1[X] != a2[X] && a1[Y] != a2[Y]

dist_x = (a1, a2) => Math.abs(a1[X] - a2[X])
dist_y = (a1, a2) => Math.abs(a1[Y] - a2[Y])

dist_orth = (a1, a2) => dist_x(a1, a2) + dist_y(a1, a2)
dist_diag = (a1, a2) => dist_orth(a1, a2) - 1
dist = (a1, a2) => is_diag(a1, a2) ? dist_diag(a1, a2) : dist_orth(a1, a2)

get_move_x = (p, target) => p[X] > target[X] ? to_dir('L') : to_dir('R')
get_move_y = (p, target) => p[Y] > target[Y] ? to_dir('U') : to_dir('D')

get_tail_move = (p, target) => {
  // move diagonal
  if (p[X] != target[X] && p[Y] != target[Y]) {
    move_x = get_move_x(p, target)
    move_y = get_move_y(p, target)
    return add(move_x, move_y)
  }
  // move orthogonal
  if (dist_x(p, target) > dist_y(p, target)) {
    return get_move_x(p, target)
  } else {
    return get_move_y(p, target)
  }
}

contains = (arr, val) => arr.some(el => range(val.length).every(i => val[i] == el[i]))

head = [0, 0]
tail = [0, 0]

positions = [tail]

for (line of text) {
  dir = to_dir(line.split(' ')[0])
  num = parseInt(line.split(' ')[1])
  range(num).forEach(i => {
    head = add(head, dir)
    if (dist(tail, head) > 1) {
      tail_move = get_tail_move(tail, head)
      tail = add(tail, tail_move)
      if (!contains(positions, tail)) positions.push(tail)
    }
  })
}

console.log(positions.length)

rope = range(10).map(i => [0, 0])
positions = [[0, 0]]

print = rope => {
  out = ''
  range(50).map(y => y - 25).map(y => {
    range(50).map(x => x - 25).map(x => {
      pos = [x, y]
      if (contains(rope, pos)) out += 'x'
      else out += '.'
    })
    out += '\n'
  })
  console.log(out)
}

for (line of text) {
  dir = to_dir(line.split(' ')[0])
  num = parseInt(line.split(' ')[1])
  range(num).forEach(_ => {
    rope[0] = add(rope[0], dir)
    range(9).map(i => i + 1).forEach(i => {
      segment = rope[i]
      target = rope[i - 1]
      
      if (dist(segment, target) > 1) {
        tail_move = get_tail_move(segment, target)
        segment = add(segment, tail_move)
        rope[i] = segment
        if (i == 9 && !contains(positions, segment)) positions.push(segment)
      }
    })
  })
}

console.log(positions.length)
