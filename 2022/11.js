range = len => [...Array(len).keys()]

function Monkey(id, items, operation, testFn, testTrue, testFalse) {

  this.id = id
  this.items = items
  this.operation = operation
  this.testFn = testFn
  this.testTrue = testTrue
  this.testFalse = testFalse
  this.amountInspected = 0

  this.inspect = () => {
    let result = []
    while (this.items.length > 0) {
      item = this.items.shift()
      item = this.operation(item)
      item = Math.floor(item / 3)
      if (testFn(item)) result.push([item, testTrue])
      else result.push([item, testFalse])
      this.amountInspected++
    }
    return result
  }

  this.receive = (item) => {
    this.items.push(item)
  }

}

monkeys = [
  new Monkey(0, [62,92,50,63,62,93,73,50], old => old * 7, num => num % 2 == 0, 7, 1),
  new Monkey(1, [51,97,74,84,99], old => old + 3, num => num % 7 == 0, 2, 4),
  new Monkey(2, [98,86,62,76,51,81,95], old => old + 4, num => num % 13 == 0, 5, 4),
  new Monkey(3, [53,95,50,85,83,72], old => old + 5, num => num % 19 == 0, 6, 0),
  new Monkey(4, [59,60,63,71], old => old * 5, num => num % 11 == 0, 5, 3),
  new Monkey(5, [92,65], old => old * old, num => num % 5 == 0, 6, 3),
  new Monkey(6, [78], old => old + 8, num => num % 3 == 0, 0, 7),
  new Monkey(7, [84,93,54], old => old + 1, num => num % 17 == 0, 2, 1)
]

range(20).forEach(round => {

  for (monkey of monkeys) {
    inspected = monkey.inspect()
    inspected.forEach(item => {
      itemLvl = item[0]
      receiver = item[1]
      monkeys[receiver].receive(itemLvl)
    })
  }

})

mostItems = monkeys.map(monkey => monkey.amountInspected)
mostItems = [...mostItems].sort((a, b) => a - b)
console.log(mostItems.slice(-2).reduce((a,b) => a * b, 1))
