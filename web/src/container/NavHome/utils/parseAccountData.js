export function parseAccountData(accountData) {
  if (accountData) {
    const name = accountData.firstName;
    const role = ['ROLE_ADMIN', 'ROLE_MANAGER'].includes(accountData.authorities[0])
      ? '管理者'
      : accountData.authorities[0] === 'ROLE_DOCTOR'
      ? '醫師'
      : accountData.authorities[0] === 'ROLE_ASSISTANT'
      ? '助理'
      : '';
    const avatar = accountData.extendUser.avatar;
    return { name, role, avatar };
  }
  return { name: '' };
}
