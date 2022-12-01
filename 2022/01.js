text = document.querySelector('pre').textContent.split('\n')
elves = text.join(',').replace(/,,/g,'\n').split('\n').map(sub => sub.split(','))
calories = elves.map(sub => sub.filter(s => s != '').map(s => parseInt(s)).reduce((a,b) => a + b, 0))

part1 = calories.reduce((a, b) => a > b ? a : b, 0)
console.log(part1)

calories.sort((a, b) => a > b);
part2 = calories.slice(-3).reduce((a,b) => a+b, 0);
console.log(part2)