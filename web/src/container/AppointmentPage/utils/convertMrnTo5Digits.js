export default function convertMrnTo5Digits(mrn) {
  return `00000${mrn}`.slice(-5);
}
