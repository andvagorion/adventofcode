lines = document.querySelector('pre').textContent.split('\n').filter(s => s != '')

X = 0
Y = 1
DIST = 3
range = len => [...Array(len).keys()];
parse_int = s => parseInt(s)
parse_only_int = s => parseInt(s.replace(/[^\d-]/g, ''))
manhattan = ([x0, y0], [x1, y1]) => Math.abs(x0 - x1) + Math.abs(y0 - y1)

sensors = {}
beacons = {}

lines.forEach(line => {
  text = line.split(' ')

  sensor_id = Object.keys(sensors).length;
  sensor_x = parse_only_int(text[2])
  sensor_y = parse_only_int(text[3])

  beacon_id = Object.keys(beacons).length;
  beacon_x = parse_only_int(text[8])
  beacon_y = parse_only_int(text[9])

  matching = Object.entries(beacons).find(([key, val]) => val[X] == beacon_x && val[Y] == beacon_y)
  if (matching != null) beacon_id = parseInt(matching[0])

  dist = manhattan([sensor_x, sensor_y], [beacon_x, beacon_y])
  
  sensors[sensor_id] = [sensor_x, sensor_y, beacon_id, dist]
  beacons[beacon_id] = [beacon_x, beacon_y]
})

max_dist = Object.values(sensors).reduce((acc, [x, y, beacon_id, dist]) => dist > acc ? dist : acc, 0)
most_left = Object.values(sensors).map(sensor => sensor[X]).reduce((a, b) => a < b ? a : b, sensors[0][X])
most_right = Object.values(sensors).map(sensor => sensor[X]).reduce((a, b) => a > b ? a : b, sensors[0][X])

console.log(sensors)
console.log(beacons)
console.log('max dist', max_dist)
console.log('most left', most_left)
console.log('most right', most_right)

y = 2000000
matches = range(most_right - most_left + 2 * max_dist).map(dx => {
  x = most_left - max_dist + dx
  return Object.entries(sensors).some(([id, sensor]) => {
    dist = manhattan([sensor[X], sensor[Y]], [x, y])
    return dist <= sensor[DIST]
  })
})

beacons_in_row = Object.values(beacons).filter(beacon => beacon[Y] == y).length
positions = matches.reduce((a, b) => a + (b ? 1 : 0), 0)
console.log(positions - beacons_in_row)
