export default function renderYYYMMDDHHMMSS(YYYMMDDHHMMSS) {
  const year = YYYMMDDHHMMSS.substring(0, 3);
  const month = YYYMMDDHHMMSS.substring(3, 5);
  const date = YYYMMDDHHMMSS.substring(5, 7);
  return `${year}/${month}/${date}`;
}
