export function parseAccountData(accountData) {
  if (accountData) {
    const name = accountData.firstName;
    const role =
      accountData.authorities[0] === 'ROLE_ADMIN'
        ? '管理者'
        : accountData.authorities[0] === 'ROLE_DOCTOR'
        ? '醫師'
        : '助理';
    const avatar = accountData.extendUser.avatar;
    return { name, role, avatar };
  }
  return { name: '' };
}
