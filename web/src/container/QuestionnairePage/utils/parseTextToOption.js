export function parseTextToOption(text, options) {
  if (!text) {
    return undefined;
  }
  const find = options.find(o => o.code === text);
  return find ? find.key : undefined;
}