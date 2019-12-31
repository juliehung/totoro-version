import {
  LOGIN_START,
  LOGIN_SUCCESS,
  LOGIN_FAIL,
  CHANGE_LOGIN_SUCCESS,
  CHECK_TOKEN_VALIDATION,
  CHECK_TOKEN_VALIDATION_FAIL,
} from './constant';

export function login(data) {
  return { type: LOGIN_START, data };
}

export function loginSuccess(authenticate) {
  return { type: LOGIN_SUCCESS, authenticate };
}

export function changeLoginFail(loginFail) {
  return { type: LOGIN_FAIL, loginFail };
}

export function changeLoginSuccess(loginSuccess) {
  return { type: CHANGE_LOGIN_SUCCESS, loginSuccess };
}

export function checkTokenValidate() {
  return { type: CHECK_TOKEN_VALIDATION };
}

export function checkTokenValidateFail() {
  return { type: CHECK_TOKEN_VALIDATION_FAIL };
}
