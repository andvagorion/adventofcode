numbers = document.querySelector('pre').textContent.split('\n').filter(s => s != '').map(s => parseInt(s))

range = len => [...Array(len).keys()]
print = obj => console.log(order.map(o => `{ orig: ${o.orig}, pos: ${o.pos}, num: ${o.num} }`).join('\n'))
print_values = () => console.log(order.map(o => o.num).join(', '))

order = range(numbers.length).map(i => ({ orig: i, num: numbers[i] }))

mix = (numbers, order, times) => {
  MAX_IDX = numbers.length - 1

  range(times).forEach(_ => {
    range(numbers.length).forEach(i => {
      val = order.find(o => o.orig == i)
      idx = order.indexOf(val)

      order.splice(idx, 1)
      idx_next = (idx + val.num) % MAX_IDX
      while (idx_next > MAX_IDX) idx_next -= MAX_IDX
      while (idx_next < 0) idx_next += MAX_IDX

      if (idx_next == 0) order.push(val)
      else order.splice(idx_next, 0, val);
    })
  })
}

get_idx = idx => {
  val_zero = order.find(o => o.num == 0)
  idx_zero = order.indexOf(val_zero)
  val_idx = (idx + idx_zero) % numbers.length
  return order[val_idx].num
}

// part 1

order = range(numbers.length).map(i => ({ orig: i, num: numbers[i] }))

mix(numbers, order, 1)

console.log(get_idx(1000) + get_idx(2000) + get_idx(3000))

// part 2

key = 811589153
numbers = numbers.map(num => num * key)

order = range(numbers.length).map(i => ({ orig: i, num: numbers[i] }))

mix(numbers, order, 10)

console.log(get_idx(1000) + get_idx(2000) + get_idx(3000))