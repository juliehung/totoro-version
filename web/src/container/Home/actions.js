import { GET_ACCOUNT_START, GET_ACCOUNT_SUCCESS } from './constant';

export function getAccount() {
  return { type: GET_ACCOUNT_START };
}

export function getAccountSuccess(account) {
  return { type: GET_ACCOUNT_SUCCESS, account };
}
