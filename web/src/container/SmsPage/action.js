import {
  GET_EVENTS,
  GET_EVENTS_SUCCESS,
  SET_SELECT_EVENT,
  CREATE_EVENT,
  EDIT_TITLE,
  EDIT_TEMPLATE,
  FILTER_EVENTS,
  ADD_TAG,
  SAVE_EVENT,
  SAVE_EVENT_SUCCESS,
  GET_APPOINTMENTS,
  GET_APPOINTMENTS_SUCCESS,
  TOGGLE_APPOINTMENT_MODAL,
  UNSELECT_APPOINTMENT,
  ADD_CONTACT_APPOINTMENTS,
  TOGGLE_PREVIEWING_MODAL,
  GET_CLINIC_SETTINGS,
  GET_CLINIC_SETTINGS_SUCCESS,
  GET_CLINIC_REMAINING,
  GET_CLINIC_REMAINING_SUCCESS,
  EXECUTE_EVENT,
  EXECUTE_EVENT_SUCCESS,
  EXECUTE_EVENT_FAILED,
  DELETE_EVENT,
  DELETE_EVENT_SUCCESS,
  GET_USERS,
  GET_USERS_SUCCESS,
  SET_CARET_POSITION,
} from './constant';

export function getEvents(page = 0, size = 10) {
  return { type: GET_EVENTS, params: { page, size } };
}

export function getEventsSuccess(result) {
  return { type: GET_EVENTS_SUCCESS, result };
}

export function setSelectedEvent(event) {
  return { type: SET_SELECT_EVENT, event };
}

export function createEvent() {
  return { type: CREATE_EVENT };
}

export function editTitle(e) {
  return { type: EDIT_TITLE, value: e.target.value };
}

export function editTemplate(e) {
  return { type: EDIT_TEMPLATE, value: e.target.value };
}

export function filterEvents(key) {
  return { type: FILTER_EVENTS, key };
}

export function addTag(value) {
  return { type: ADD_TAG, value };
}

export function saveEvent(event) {
  return { type: SAVE_EVENT, event };
}

export function saveEventSuccess(result, identity) {
  return { type: SAVE_EVENT_SUCCESS, payload: { result, identity } };
}

export function executeEvent(event) {
  return { type: EXECUTE_EVENT, event };
}

export function executeEventSuccess() {
  return { type: EXECUTE_EVENT_SUCCESS };
}

export function executeEventFailed() {
  return { type: EXECUTE_EVENT_FAILED };
}

export function deleteEvent(id) {
  return { type: DELETE_EVENT, id };
}

export function deleteEventSuccess(identity) {
  return { type: DELETE_EVENT_SUCCESS, identity };
}

export function getAppointments(range) {
  return { type: GET_APPOINTMENTS, range };
}

export function getAppointmentsSuccess(appointments) {
  return { type: GET_APPOINTMENTS_SUCCESS, appointments };
}

export function toggleAppointmentModal() {
  return { type: TOGGLE_APPOINTMENT_MODAL };
}

export function addContactAppointments(appointments) {
  return { type: ADD_CONTACT_APPOINTMENTS, appointments };
}

export function unselectAppointment(key) {
  return { type: UNSELECT_APPOINTMENT, key };
}

export function togglePreviewingModal() {
  return { type: TOGGLE_PREVIEWING_MODAL };
}

export function getClinicSettings() {
  return { type: GET_CLINIC_SETTINGS };
}

export function getClinicSettingsSuccess(settings) {
  return { type: GET_CLINIC_SETTINGS_SUCCESS, settings };
}

export function getClinicRemaining() {
  return { type: GET_CLINIC_REMAINING };
}

export function getClinicRemainingSuccess(remaining) {
  return { type: GET_CLINIC_REMAINING_SUCCESS, remaining };
}

export function getUsers() {
  return { type: GET_USERS };
}

export function getUsersSuccess(users) {
  return { type: GET_USERS_SUCCESS, users };
}

export function setCaretPosition(idx, by) {
  return { type: SET_CARET_POSITION, payload: { idx, by } };
}
