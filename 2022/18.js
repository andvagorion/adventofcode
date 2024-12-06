lava = document.querySelector('pre').textContent.split('\n').filter(s => s != '').map(line => line.split(',').map(s => parseInt(s)))

range = len => [...Array(len).keys()]
equals = (arr, other) => range(arr.length).every(i => arr[i] == other[i])
contains = (arr, val) => arr.some(el => equals(el, val))

manhattan_3d = ([x0, y0, z0], [x1, y1, z1]) => Math.abs(x0 - x1) + Math.abs(y0 - y1) + Math.abs(z0 - z1)

get_neighbors = ([x, y, z]) => {
  neighbors = []
  range(3).forEach(x1 => range(3).forEach(y1 => range(3).forEach(z1 => {
    point = [x, y, z]
    other = [x + x1 - 1, y + y1 - 1, z + z1 - 1]
    if (manhattan_3d(point, other) == 1) neighbors.push(other)
  })))
  return neighbors
}

open = 0
lava.forEach(drop => {
  neighbors = get_neighbors(drop)
  neighbors.forEach(neighbor => {
    if (!contains(lava, neighbor)) open += 1
  })
})

console.log(open)

X = 0; Y = 1; Z = 2
min = [ lava[0][X], lava[0][Y], lava[0][Z] ]
max = [ lava[0][X], lava[0][Y], lava[0][Z] ]

lava.forEach(drop => {
  if (drop[X] < min[X]) min[X] = drop[X]
  if (drop[X] > max[X]) max[X] = drop[X]
  if (drop[Y] < min[Y]) min[Y] = drop[Y]
  if (drop[Y] > max[Y]) max[Y] = drop[Y]
  if (drop[Z] < min[Z]) min[Z] = drop[Z]
  if (drop[Z] > max[Z]) max[Z] = drop[Z]
})

check = (drop, dir, max) => {
  next = [...drop]
  while (!equals(next, max)) {
    next = [next[X] + dir[X], next[Y] + dir[Y], next[Z] + dir[Z]]
    if (contains(lava, next)) return false
  }
  return true
}

inner = 0
lava.forEach(drop => {
  neighbors = get_neighbors(drop)
  neighbors.forEach(neighbor => {
    top = check(neighbor, [0, 0, 1], [neighbor[X], neighbor[Y], max[Z]])
    bottom = check(neighbor, [0, 0, -1], [neighbor[X], neighbor[Y], min[Z]])
    left = check(neighbor, [-1, 0, 0], [min[X], neighbor[Y], neighbor[Z]])
    right = check(neighbor, [1, 0, 0], [max[X], neighbor[Y], neighbor[Z]])
    front = check(neighbor, [0, -1, 0], [neighbor[X], min[Y], neighbor[Z]])
    back = check(neighbor, [0, 1, 0], [neighbor[X], max[Y], neighbor[Z]])
    if (top && bottom && left && right && front && back) inner += 1
  })
})

console.log(inner)
