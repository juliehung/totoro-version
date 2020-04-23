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
  GET_APPOINTMENTS,
  GET_APPOINTMENTS_SUCCESS,
  TOGGLE_APPOINTMENT_MODAL,
  UNSELECT_APPOINTMENT,
  ADD_CONTACT_APPOINTMENTS,
  TOGGLE_PREVIEWING_MODAL,
  GET_CLINIC_SETTINGS,
  GET_CLINIC_SETTINGS_SUCCESS,
  SAVE_EVENT_AND_SEND_IMMEDIATELY,
  GET_CLINIC_REMAINING,
  GET_CLINIC_REMAINING_SUCCESS,
  EXECUTE_EVENT,
  DELETE_EVENT,
  EXECUTE_EVENT_FAILED,
} from './constant';

export function getEvents() {
  return { type: GET_EVENTS };
}

export function getEventsSuccess(events) {
  return { type: GET_EVENTS_SUCCESS, events };
}

export function setSelectedEvent(event, shouldDumpDraft) {
  return { type: SET_SELECT_EVENT, payload: {event, shouldDumpDraft} };
}

export function createEvent() {
  return { type: CREATE_EVENT };
}

export function editTitle(e) {
  return { type: EDIT_TITLE, value: e.target.value};
}

export function editTemplate(e) {
  return { type: EDIT_TEMPLATE, value: e.target.value};
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

export function executeEvent(event) {
  return { type: EXECUTE_EVENT, event };
}

export function executeEventFailed() {
  return { type: EXECUTE_EVENT_FAILED };
}

export function deleteEvent(id) {
  return { type: DELETE_EVENT, id };
}

export function saveEventAndSendImmediately(event) {
  return { type: SAVE_EVENT_AND_SEND_IMMEDIATELY, event };
}


export function getAppointments(range) {
  return { type: GET_APPOINTMENTS, range };
}

export function getAppointmentsSuccess(appointments) {
  return { type: GET_APPOINTMENTS_SUCCESS, appointments };
}

export function toggleAppointmentModal(){
  return { type: TOGGLE_APPOINTMENT_MODAL };
}

export function addContactAppointments(appointments){
  return { type: ADD_CONTACT_APPOINTMENTS, appointments };
}

export function unselectAppointment(key){
  return { type: UNSELECT_APPOINTMENT, key };
}

export function togglePreviewingModal(){
  return { type: TOGGLE_PREVIEWING_MODAL };
}

export function getClinicSettings(){
  return { type: GET_CLINIC_SETTINGS };
}

export function getClinicSettingsSuccess(settings){
  return { type: GET_CLINIC_SETTINGS_SUCCESS, settings };
}

export function getClinicRemaining(){
  return { type: GET_CLINIC_REMAINING };
}

export function getClinicRemainingSuccess(remaining){
  return { type: GET_CLINIC_REMAINING_SUCCESS, remaining };
}