import {
  GET_REGISTRATIONS_START,
  GET_REGISTRATIONS_SUCCESS,
  SET_SELECTED_DATE_SUCCESS,
  CHANGE_DRAWER_VISIBLE,
  ON_SELECT_PATIENT,
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
