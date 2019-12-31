export function toggleArrayItem(array, value) {
  const cloneArray = JSON.parse(JSON.stringify(array));
  const index = array.indexOf(value);
  if (index === -1) {
    cloneArray.push(value);
  } else {
    cloneArray.splice(index, 1);
  }
  return cloneArray;
}
