export function determineRouteShow(showWhenLocalVersion) {
  const dev = process.env.NODE_ENV === 'development';
  return dev ? true : showWhenLocalVersion || window.location.origin.includes('dentall');
}
