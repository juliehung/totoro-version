export default function appendAmountOnTitle(title, amount) {
  return `${title}${amount && amount !== 0 ? ` (${amount})` : ''}`;
}
