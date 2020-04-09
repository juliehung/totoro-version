export default function extractDoctorsFromUser(users) {
  return users
    .filter(u => u.authorities.includes('ROLE_DOCTOR'))
    .map(d => ({
      id: d.id,
      name: d.firstName,
      login: d.login,
      activated: d.activated,
      avatar: d.extendUser.avatar,
    }));
}
