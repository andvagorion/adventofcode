text = document.querySelector('pre').textContent.split('\n').filter(s => s != '')

rx = 1
cycle = 0
queue = []

wait = null

cycles = [20, 60, 100, 140, 180, 220]
sum = 0

WIDTH = 40
HEIGHT = 6

range = len => [...Array(len).keys()];
print = px => console.log(px.map(row => row.join('')).join('\n'))

pixels = range(HEIGHT).map(_ => range(WIDTH).map(_ => '.'))

while (text.length > 0) {
  cycle++

  if (wait == null) {
    op = text.shift()
    if (op.startsWith('addx')) {
      val = parseInt(op.split(' ')[1])
      wait = [1, val]
    }
  }
  
  y = Math.floor((cycle - 1) / WIDTH)
  x = cycle - 1 - (y * WIDTH)
  
  if (rx == x || rx - 1 == x || rx + 1 == x) pixels[y][x] = '#'
  
  if (cycles.includes(cycle)) {
    sum += cycle * rx
  }

  if (wait != null) {
    if (wait[0] > 0) {
      wait[0] -= 1
    } else {
      rx += wait[1]
      wait = null
    }
  }
}

console.log(sum)
print(pixels)
