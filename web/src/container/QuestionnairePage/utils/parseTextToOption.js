export function parseTextToOption(text, options) {
  console.log(text, options);

  if (!text) {
    return undefined;
  }
  const find = options.find(o => o.code === text);
  return find ? find.key : undefined;
}
