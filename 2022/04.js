lines = document.querySelector('pre').textContent.split('\n').filter(s => s != '')

parse_int = s => parseInt(s)
to_range = s => s.split('-').map(parse_int)
to_ranges = line => line.split(',').map(to_range)

contains = (r1, r2) => r2[0] <= r1[0] && r2[1] >= r1[1]
ranges_contains_other = pair => contains(pair[1], pair[0]) || contains(pair[0], pair[1])

overlap = (r1, r2) => (r2[0] <= r1[1] && r2[0] >= r1[0]) || (r2[1] <= r1[1] && r2[1] >= r1[0])
ranges_overlap = pair => overlap(pair[1], pair[0]) || overlap(pair[0], pair[1])

ranges = lines.map(to_ranges)

console.log(ranges.filter(ranges_contains_other).length)
console.log(ranges.filter(ranges_overlap).length)
