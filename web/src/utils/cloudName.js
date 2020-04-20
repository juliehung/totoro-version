export const cloudName = (() => {
  return window.location.pathname.split('/').filter(s => s)[0];
})();
