export function pincuiOrRakumi() {
  const dev = process.env.NODE_ENV === 'development';
  return dev ? true : window.location.pathname.includes('pin-cui') || window.location.pathname.includes('rakumi');
}
