export function calculatePreviousRange(range) {
  if (!range || !range.start || !range.end) return undefined;
  const start = range.start.clone().add(-1, 'w');
  const end = range.end.clone().add(-1, 'w');
  return { start, end };
}
