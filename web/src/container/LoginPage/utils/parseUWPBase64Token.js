export function parseUWPBase64Token(uwpBase64) {
  const tokenFromUrl = uwpBase64.replace(/-/g, '+').replace(/_/g, '/');
  switch (tokenFromUrl.Length % 4) {
    case 2:
      uwpBase64 += '==';
      break;
    case 3:
      uwpBase64 += '=';
      break;
    default:
      break;
  }

  try {
    return atob(tokenFromUrl);
  } catch (error) {
    const url = window.location.origin + window.location.pathname;
    window.location.replace(url);
  }
}
