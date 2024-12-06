input = document.querySelector('pre').textContent.split('\n').filter(s => s != '')

cd = ['/']
fs = {}

for (line of input) {
  if (line.startsWith('$')) {
    parts = line.split(' ')
    if (parts[1] == 'cd') {
      if (parts[2] == '/') {
        cd = ['/']
      } else if (parts[2] == '..') {
        cd.pop()
      } else {
        name = parts[2]
        cd.push(name)
      }
    }
    if (parts[1] == 'ls') {
      // ignore for now
    }
  } else {
    parts = line.split(' ')
    name = parts[1]
    size = parts[0]

    curr = fs
    for (dir of cd) {
      if (!(dir in curr)) {
        curr[dir] = {}
      }
      curr = curr[dir]
    }

    if (size == 'dir') {
      curr[name] = {}
    } else {
      curr[name] = parseInt(size)
    }
  }
}

range = len => [...Array(len).keys()]

walk = (obj, path, sizes) => {
  for (var name in obj) {
    file = obj[name]
    if (typeof file == "object") {
      sub_path = [...path]
      sub_path.push(name)
      walk(file, sub_path, sizes);
    } else {
      for (i = 1; i < path.length + 1; i++) {
        full_path = range(i).map(k => path[k]).join('/')
        if (!(full_path in sizes)) sizes[full_path] = 0
        sizes[full_path] += file
      }
    }
  }
  return sizes
}

sizes = {}
walk(fs, [], sizes)

sum = (a, b) => a + b
dir_sum = Object.entries(sizes).filter(arr => arr[1] <= 100000).map(arr => arr[1]).reduce(sum, 0)
console.log(dir_sum)

total_used = sizes['/']
max = 70000000 - 30000000

to_free = total_used - max

min = (a, b) => a[1] < b[1] ? a : b
to_delete = Object.entries(sizes).filter(arr => arr[1] >= to_free).reduce(min, 0)
console.log(to_delete[1])
