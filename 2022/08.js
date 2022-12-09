text = document.querySelector('pre').textContent
// text = '30373\n25512\n65332\n33549\n35390'

input = text.split('\n').filter(s => s != '')

product = (a, b) => a * b
range = len => [...Array(len).keys()];
parse_int = s => parseInt(s)
grid = input.map(s => [...s].map(parse_int))

width = grid[0].length
height = grid.length

total = 0
visible = []
max = -1

check_tree = (x, y) => {
  tree = grid[y][x]
  hash = `${x}-${y}`
  if (tree > max) {
    if (!(visible.includes(hash))) {
      total++
      visible.push(hash)
    }
    max = tree
  }
}

range(width).map(x => {
  max = -1
  range(height).map(y => check_tree(x, y))
  max = -1
  range(height).reverse().map(y => check_tree(x, y))
})

range(height).map(y => {
  max = -1
  range(width).map(x => check_tree(x, y))
  max = -1
  range(width).reverse().map(x => check_tree(x, y))
})

console.log(total)

total = 0

viewing_distances = (x, y) => {
  tree = grid[y][x]
  count_up = -1
  y1 = y
  while (y1 >= 0 && grid[y1][x] <= tree) {
    count_up++
    if (y1 != y && grid[y1][x] == tree) break
    y1--
  }
  count_down = -1
  y1 = y
  while (y1 < height && grid[y1][x] <= tree) {
    count_down++
    if (y1 != y && grid[y1][x] == tree) break
    y1++
  }
  count_right = -1
  x1 = x
  while (x1 < width && grid[y][x1] <= tree) {
    count_right++
    if (x1 != x && grid[y][x1] == tree) break
    x1++
  }
  count_left = -1
  x1 = x
  while (x1 >= 0 && grid[y][x1] <= tree) {
    count_left++
    if (x1 != x && grid[y][x1] == tree) break
    x1--
  }
  score = [count_up, count_down, count_right, count_left].reduce(product, 1)
  if (score >= 1000000) {
    console.log('score for', x, y, 'is', score)
    console.log([y, 99-y-1, 99-x-1, x])
    console.log([count_up, count_down, count_right, count_left])
  }
  return score
}

best_score = 0

range(height).forEach(y => {
  range(width).forEach(x => {
    score = viewing_distances(x, y)
    if (score > best_score) best_score = score
  })
})

console.log(best_score)
