text = document.querySelector('pre').textContent

unique_characters = s => [...s].length == new Set(s).size

unique_after = (text, num) => {
  for (i = 0; i < text.length; i++) {
    part = text.slice(i, i + num)
    if (unique_characters(part)) return i + num
  }
  return -1
}

console.log(unique_after(text, 4))
console.log(unique_after(text, 14))
