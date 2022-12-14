// beware: this runs for a long time and is very unoptimized...

lines = document.querySelector('pre').textContent.split('\n').filter(s => s != '')

range = len => [...Array(len).keys()]
parse_int = s => parseInt(s)

d1 = (a, b) => {
  if (a < b) return a + 1
  else if (a > b) return a - 1
  return a
}

function Point(x, y) {
  this.x = x
  this.y = y

  this.equals = other => this.x == other.x && this.y == other.y
  this.move = (dx, dy) => new Point(this.x + dx, this.y + dy)
  this.move_towards = other => new Point(d1(this.x, other.x), d1(this.y, other.y))
  this.to_string = () => `${this.x}, ${this.y}`

  this.at = other => new Point(other.x, other.y)
  this.update_to = other => {
    this.x = other.x
    this.y = other.y
  }
}

to_point = s => {
  arr = s.split(',').map(parse_int)
  return new Point(arr[0], arr[1])
}

rocks = []
static_sand = []

lines.forEach(line => {
  path_points = line.split(' -> ').map(to_point)
  curr = path_points.shift()
  while (path_points.length > 0) {
    rocks.push(curr)
    if (curr.equals(path_points[0])) curr = path_points.shift()
    if (path_points.length > 0) curr = curr.move_towards(path_points[0])
  }
})

bottom = rocks.map(r => r.y).reduce((a, b) => a > b ? a : b, 0) + 2
curr = new Point(0, bottom)
target = new Point(1000, bottom)

rocks.push(curr, target)
while (!curr.equals(target)) {
  next = curr.move_towards(target)
  rocks.push(next)
  curr = next
}

i = 0

is_rock = p => rocks.some(rock => rock.equals(p))
is_sand = p => static_sand.some(sand => sand.equals(p))
blocked = p => is_rock(p) || is_sand(p)

start = new Point(500, 0)
static_sand.length = 0

spawn = () => grain = new Point(start.x, start.y)
grain = null

done = false
part1 = -1
part2 = -1

spawn()
while (!done && i < 1000000000) {
  next = grain.move(0, 1)

  if (blocked(next)) {

    // try moving left
    left = grain.move(-1, 1)
    right = grain.move(1, 1)

    if (!blocked(left)) {
      grain.update_to(left)
    } else if (!blocked(right)) {
      grain.update_to(right)
    } else {
      // both are blocked => sand is now static
      static_sand.push(grain)
      spawn()
    }

  } else {
    grain.update_to(next)
  }

  if (static_sand.some(s => s.equals(start))) done = true

  i += 1
}

console.log(static_sand.length)
