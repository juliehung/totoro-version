export default function toSurfaceAbbrivation(surface) {
  return surface
    .split('*')
    .map(s => s.split('_')[2] ?? '')
    .join('');
}
