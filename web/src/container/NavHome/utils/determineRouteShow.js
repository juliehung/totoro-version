export function determineRouteOrLinkShow(route) {
  const isDev = process.env.NODE_ENV === 'development';
  const showWhenLocalVersion = route.localVersion;
  const specificClinic = route.clinic;
  const isLocal = !window.location.origin.includes('dentall');

  return isDev ? true : isLocal ? showWhenLocalVersion : clinicChecker(specificClinic);
}

function clinicChecker(clinics) {
  if (!clinics) {
    return true;
  }
  return clinics.some(p => window.location.pathname.includes(p));
}
