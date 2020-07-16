import {
  GET_REGISTRATIONS_START,
  GET_REGISTRATIONS_SUCCESS,
  SET_SELECTED_DATE_SUCCESS,
  CHANGE_DRAWER_VISIBLE,
  ON_SELECT_PATIENT,
  GET_DOC_START,
  GET_DOC_SUCCESS,
  ON_LEAVE_PAGE,
} from './constant';

export function getRegistrations(start, end) {
  return { type: GET_REGISTRATIONS_START, range: { start, end } };
}

export function getRegistrationsSuccess(appData) {
  return { type: GET_REGISTRATIONS_SUCCESS, appData };
}

export function updateSelectedDate(date) {
  return { type: SET_SELECTED_DATE_SUCCESS, date };
}

export function changeDrawerVisible(visible) {
  return { type: CHANGE_DRAWER_VISIBLE, visible };
}

export function onSelectPatient(patient) {
  return { type: ON_SELECT_PATIENT, patient };
}

export function getDoc(pid) {
  return { type: GET_DOC_START, pid };
}

export function getDocSuccess(docs) {
  return { type: GET_DOC_SUCCESS, docs };
}

export function onLeavePage() {
  return { type: ON_LEAVE_PAGE };
}
