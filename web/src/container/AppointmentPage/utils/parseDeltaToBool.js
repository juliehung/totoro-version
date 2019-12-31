export function parseDeltaToBool(delta) {
  if (!delta) {
    return false;
  } else {
    return Object.keys(delta).some(k => {
      return delta[k] !== 0;
    });
  }
}
