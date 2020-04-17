import {
  GET_ACCOUNT_START,
  GET_ACCOUNT_SUCCESS,
  GET_USER_START,
  GET_USER_SUCCESS,
  GET_SETTINGS_START,
  GET_SETTINGS_SUCCESS,
} from './constant';

export function getAccount() {
  return { type: GET_ACCOUNT_START };
}

export function getAccountSuccess(account) {
  return { type: GET_ACCOUNT_SUCCESS, account };
}

export function getUserStart() {
  return { type: GET_USER_START };
}

export function getUserSuccess(user) {
  return { type: GET_USER_SUCCESS, user };
}

export function getSettings() {
  return { type: GET_SETTINGS_START };
}

export function getSettingsSuccess(settings) {
  return { type: GET_SETTINGS_SUCCESS, settings };
}
