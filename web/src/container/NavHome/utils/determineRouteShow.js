export function determineRouteOrLinkShow(route, account) {
  const isDev = process.env.NODE_ENV === 'development';
  if (route.path === 'nhi' && (!window.location.href.includes('dev.dentall.site') || !isDev)) {
    return false;
  }
  const adminRole = account && account?.role && account?.role.indexOf('管理者') !== -1;
  const isRequireAuth = !!route?.requireAuth;
  const showWhenLocalVersion = route.localVersion;
  const specificClinic = route.clinic;
  const isLocal = isLocalChecker();
  const routeCheck = isDev ? true : isLocal ? showWhenLocalVersion : clinicChecker(specificClinic);

  return isRequireAuth ? (adminRole ? routeCheck : false) : routeCheck;
}

function isLocalChecker() {
  return (
    !window.location.href.includes('his.dentall.io') &&
    !window.location.href.includes('dentall.pw') &&
    !window.location.href.includes('dev.dentall.site')
  );
}

function clinicChecker(clinics) {
  if (!clinics) {
    return true;
  }
  return clinics.some(p => window.location.pathname.includes(p));
}
