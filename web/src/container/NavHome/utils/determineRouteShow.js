export function determineRouteOrLinkShow(route) {
  const isDev = process.env.NODE_ENV === 'development';
  const showWhenLocalVersion = route.localVersion;
  const specificClinic = route.clinic;
  const isLocal = isLocalChecker();

  return isDev ? true : isLocal ? showWhenLocalVersion : clinicChecker(specificClinic);
}

function isLocalChecker() {
  return !window.location.href.includes('his.dentall.io') && !window.location.href.includes('dentall.pw');
}

function clinicChecker(clinics) {
  if (!clinics) {
    return true;
  }
  return clinics.some(p => window.location.pathname.includes(p));
}
