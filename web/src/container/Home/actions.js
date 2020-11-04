import {
  GET_ACCOUNT_START,
  GET_ACCOUNT_SUCCESS,
  GET_USER_START,
  GET_USER_SUCCESS,
  GET_SETTINGS_START,
  GET_SETTINGS_SUCCESS,
  PUT_SETTINGS_START,
  PUT_SETTINGS_SUCCESS,
  OPEN_XRAY,
  CHANGE_XRAY_MODAL_VISIBLE,
  XRAY_GREETING,
  XRAY_GREETING_SUCCESS,
  XRAY_GREETING_FAILURE,
  RESTORE_XRAY_STATE,
  GET_NHI_PROCEDURE,
  GET_NHI_PROCEDURE_SUCCESS,
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

export function putSettings(settings) {
  return { type: PUT_SETTINGS_START, settings };
}

export function putSettingsSuccess(settings) {
  return { type: PUT_SETTINGS_SUCCESS, settings };
}

export function openXray(data = {}) {
  return { type: OPEN_XRAY, data };
}

export function xrayGreeting() {
  return { type: XRAY_GREETING };
}

export function xrayGreetingSuccess() {
  return { type: XRAY_GREETING_SUCCESS };
}

export function xrayGreetingFailure() {
  return { type: XRAY_GREETING_FAILURE };
}

export function restoreXrayState() {
  return { type: RESTORE_XRAY_STATE };
}

export function changeXrayModalVisible(visible) {
  return { type: CHANGE_XRAY_MODAL_VISIBLE, visible };
}

export function getNhiProcedure() {
  return { type: GET_NHI_PROCEDURE };
}

export function getNhiProcedureSuccess(nhiProcedure) {
  return { type: GET_NHI_PROCEDURE_SUCCESS, nhiProcedure };
}
