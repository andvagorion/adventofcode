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

lines.forEach(line => {
  path_points = line.split(' -> ').map(to_point)
  curr = path_points.shift()
  while (path_points.length > 0) {
    rocks.push(curr)
    if (curr.equals(path_points[0])) curr = path_points.shift()
    if (path_points.length > 0) curr = curr.move_towards(path_points[0])
  }
})

start = new Point(500, 0)

static_sand = []

grain = null
spawn = () => grain = new Point(start.x, start.y)

fell_into_void = false
void_start = rocks.map(r => r.y).reduce((a, b) => a > b ? a : b, 0) + 1
i = 0

is_rock = p => rocks.some(rock => rock.equals(p))
is_sand = p => static_sand.some(sand => sand.equals(p))
blocked = p => is_rock(p) || is_sand(p)

spawn()
while (!fell_into_void && i < 100000) {
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

  if (grain.y > void_start) fell_into_void = true
  i += 1
}

print = () => {
  out = range(200).map(y => {
    return range(150).map(x => {
      x += 450
      p = new Point(x, y)
      if (is_rock(p)) return '#'
      else if (is_sand(p)) return 'o'
      return '.'
    }).join('')
  }).join('\n')
  console.log(out)
}

// print()
console.log(static_sand.length)
