width = 50
height = 6

range = (end) => [...Array(end).keys()];

print = (scr) => console.log(scr.map((sub) => sub.join('')).join('\n'))
copy_of = (arr) => range(height).map((y) => range(width).map((x) => arr[y][x]))

rect = (src, w, h) => {
  copy = copy_of(src)
  range(h).map(y => range(w).map(x => copy[y][x] = '#'))
  return copy
}

rotate_row = (src, y, num) => {
  copy = copy_of(src)
  cols = src[y].length 
  range(cols).map(x => {
    x1 = x - num
    
    while (x1 < 0 || x1 >= cols) {
      if (x1 < 0) x1 = cols + x1
      else if (x1 >= cols) x1 = x1 - cols
    }
    
    val = src[y][x1]
    copy[y][x] = val
  })
  return copy
}

rotate_col = (src, x, num) => {
  copy = copy_of(src)
  rows = src.length
  range(rows).map(y => {
    y1 = y - num
    
    while (y1 < 0 || y1 >= rows) {
      if (y1 < 0) y1 = rows + y1
      else if (y1 >= rows) y1 = y1 - rows
    }
    
    val = src[y1][x]
    copy[y][x] = val
  })
  return copy
}

execute = (arr, line) => {
  if (line.startsWith('rect')) {
    vals = line.split(' ')[1].split('x')
    w = parseInt(vals[0])
    h = parseInt(vals[1])
    return rect(arr, w, h)
  } else if (line.startsWith('rotate row')) {
    vals = line.split(' ')
    y = parseInt(vals[2].split('=')[1])
    num = parseInt(vals[4])
    return rotate_row(arr, y, num)
  } else if (line.startsWith('rotate column')) {
    vals = line.split(' ')
    x = parseInt(vals[2].split('=')[1])
    num = parseInt(vals[4])
    return rotate_col(arr, x, num)
  }
}

lines = document.querySelector('pre').textContent.split('\n').filter(line => line != '')

// example
// width = 7, height = 3, text = 'rect 3x2\nrotate column x=1 by 1\nrotate row y=0 by 4\nrotate column x=1 by 1'

screen = range(height).map(() => range(width).map(() => '.'))

lines.forEach(line => {
  screen = execute(screen, line)
})

part1 = screen.flat().map(val => val == '#' ? 1 : 0).reduce((a,b) => a + b, 0)
console.log(part1)

// part 2: 
print(screen)