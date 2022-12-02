lines = document.querySelector('pre').textContent.split('\n').filter(line => line != '')

R = 1, P = 2, S = 3
points = { 'A': R, 'B': P, 'C': S, 'X': R, 'Y': P, 'Z': S }

beats = (a, b) => (a == R && b == S) || (a == S && b == P) || (a == P && b == R)
draw = (a, b) => a == b

outcome = r => {
  if (beats(r[1], r[0])) return 6
  else if (draw(r[1], r[0])) return 3
  else return 0
}

rounds = lines.map(l => l.split(' ').map(s => points[s]))

part1 = rounds.map(r => r[1] + outcome(r)).reduce((a,b) => a + b, 0)
console.log(part1)

//// part 2

get_outcome = round => {
  player = round[1]
  opponent = points[round[0]]
  if (player == 'Z') {
    if (opponent == R) return [R, P]
    else if (opponent == P) return [P, S]
    else return [S, R]
  }
  else if (player == 'X') {
    if (opponent == R) return [R, S]
    else if (opponent == P) return [P, R]
    else return [S, P]
  }
  else {
    return [opponent, opponent]
  }
}

rounds = lines.map(l => l.split(' ')).map(s => get_outcome(s))
part2 = rounds.map(r => r[1] + outcome(r)).reduce((a,b) => a + b, 0)
console.log(part2)
