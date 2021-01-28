export default function toSurfaceAbbrivation(surface) {
  if (surface && surface.length !== 0) {
    return surface
      .split('*')
      .map(s => s.split('_')[2] ?? '')
      .join('');
  } else {
    return '';
  }
}
