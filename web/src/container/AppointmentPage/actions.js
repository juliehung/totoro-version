import {
  CHANGE_CAL_DATE,
  GET_APPOINTMENTS_START,
  GET_APPOINTMENTS_SUCCESS,
  CHANGE_PRINT_MODAL_VISIBLE,
  CHANGE_PRINT_DATE,
  GET_PRINT_APP_LIST_SUCCESS,
  CHANGE_CAL_FIRST_DAY,
  GET_USERS_START,
  GET_DOCTORS_SUCCESS,
  CHANGE_SELECTED_DOCTORS,
  CHANGE_CONFIRM_MODAL_VISIBLE,
  INSERT_PENDING_INFO,
  EDIT_APPOINTMENT_SUCCESS,
  EDIT_APPOINTMENT_START,
  CREATE_APPOINTMENT,
  CREATE_APPOINTMENT_SUCCESS,
  CHANGE_CREATE_APPOINTMENT_VISIBLE,
  SEARCH_PATIENTS_START,
  SEARCH_PATIENTS_SUCCESS,
  GET_PATIENT_START_CREATE_APP,
  GET_PATIENT_SUCCESS_CREATE_APP,
  CHANGE_PATIENT_SELECTED,
  CHANGE_CREATE_APP_EXPECTED_ARRIVAL_DATE,
  CHANGE_CREATE_APP_EXPECTED_ARRIVAL_TIME,
  CHANGE_CREATE_APP_DOCTOR,
  CHANGE_CREATE_APP_DURATION,
  CHANGE_CREATE_APP_NOTE,
  CHANGE_CREATE_APP_SPECIAL_NOTE,
  CHECK_CONFIRM_BUTTON_DISABLE,
  CHANGE_CREATE_APP_PATIENT_NAME,
  CHANGE_CREATE_APP_PATIENT_PHONE,
  CHANGE_CREATE_APP_PATIENT_NATIONAL_ID,
  CHANGE_CREATE_APP_PATIENT_BIRTH,
  CREATE_PATIENT,
  CREATE_PATIENT_SUCCESS,
  CHANGE_EDIT_APP_MODAL_VISIBLE,
  INSERT_APP_TO_EDIT_APP_MODAL,
  GET_PATIENT_SUCCESS_EDIT_APP,
  DELETE_APPOINTMENT_START,
  DELETE_APPOINTMENT_SUCCESS,
  CHANGE_EDIT_APP_CONFIRM_DELETE,
  CHANGE_EDIT_APP_EXPECTED_ARRIVAL_DATE,
  CHANGE_EDIT_APP_EXPECTED_ARRIVAL_TIME,
  CHANGE_EDIT_APP_DOCTOR,
  CHANGE_EDIT_APP_DURATION,
  CHANGE_EDIT_APP_NOTE,
  CHANGE_EDIT_APP_SPECIAL_NOTE,
  CHECK_EDIT_APP_CONFIRM_MODAL_BUTTON_DISABLE,
  GET_CALENDAR_EVENT_START,
  GET_CALENDAR_EVENT_SUCCESS,
  CHANGE_CREATE_CAL_EVT_START_DATE,
  CAHNGE_CREATE_CAL_EVT_START_TIME,
  CAHNGE_CREATE_CAL_EVT_END_DATE,
  CAHNGE_CREATE_CAL_EVT_END_TIME,
  CHANGE_CREATE_CAL_EVT_DOCTOR,
  CHANGE_CREATE_CAL_EVT_REPEAT,
  CHANGE_CREATE_CAL_EVT_REAPEAT_END_DATE,
  CHANGE_CREATE_CAL_EVT_NOTE,
  CHECK_CREATE_CAL_CONFIRM_BUTTON_DISABLE,
  CHANGE_CREATE_CAL_MODAL_VISIBLE,
  CREATE_CAL_EVT_START,
  CREATE_CAL_EVT_SUCCESS,
  GET_ALL_EVENTS,
  INSERT_CAL_EVT_TO_EDIT_CAL_EVT_MODEL,
  CHANGE_EDIT_CAL_MODAL_VISIBLE,
  CHANGE_EDIT_CAL_EVT_START_DATE,
  CAHNGE_EDIT_CAL_EVT_START_TIME,
  CAHNGE_EDIT_CAL_EVT_END_DATE,
  CAHNGE_EDIT_CAL_EVT_END_TIME,
  CHANGE_EDIT_CAL_EVT_DOCTOR,
  CHANGE_EDIT_CAL_EVT_REPEAT,
  CHANGE_EDIT_CAL_EVT_REPEAT_END_DATE,
  CHANGE_EDIT_CAL_EVT_NOTE,
  CHANGE_EDIT_CAL_EVT_CONFIRM_DELETE,
  CHECK_EDIT_CAL_CONFIRM_BUTTON_DISABLE,
  EDIT_CAL_EVT_START,
  EDIT_CAL_EVT_SUCCESS,
  DELETE_CAL_EVT_START,
  DELETE_CAL_EVT_SUCCESS,
  CHANGE_CAL_SLOT_DURATION,
  GET_TODOS_START,
  GET_TODOS_SUCCESS,
  GET_SETTINGS_START,
  GET_SETTINGS_SUCCESS,
  POPOVER_CANCEL_APP_START,
  POPOVER_CANCEL_APP_SUCCESS,
} from './constant';

export function changeCalDate(date) {
  return { type: CHANGE_CAL_DATE, date };
}

export function changeCalFirstDay(firstDay) {
  return { type: CHANGE_CAL_FIRST_DAY, firstDay };
}

export function getAppointments(start, end) {
  return { type: GET_APPOINTMENTS_START, range: { start, end } };
}

export function getAppointmentsSuccess(appData) {
  return { type: GET_APPOINTMENTS_SUCCESS, appData };
}

export function changePrintModalVisible() {
  return { type: CHANGE_PRINT_MODAL_VISIBLE };
}

export function changePrintDate(date) {
  return { type: CHANGE_PRINT_DATE, date };
}

export function getPrintAppointmentsSuccess(appData) {
  return { type: GET_PRINT_APP_LIST_SUCCESS, appData };
}

export function getUsersStart() {
  return { type: GET_USERS_START };
}

export function getDoctorsSuccess(doctors) {
  return { type: GET_DOCTORS_SUCCESS, doctors };
}

export function changeSelectedDoctors(selectedDoctors) {
  return { type: CHANGE_SELECTED_DOCTORS, selectedDoctors };
}

export function changeConfirmModalVisible(visible) {
  return { type: CHANGE_CONFIRM_MODAL_VISIBLE, visible };
}

export function insertPendingInfo(info) {
  return { type: INSERT_PENDING_INFO, info };
}

export function editAppointment(app) {
  return { type: EDIT_APPOINTMENT_START, app };
}

export function editAppointmentSuccess(appointment) {
  return { type: EDIT_APPOINTMENT_SUCCESS, appointment };
}

export function createAppointment(app) {
  return { type: CREATE_APPOINTMENT, app };
}

export function createAppointmentSuccess() {
  return { type: CREATE_APPOINTMENT_SUCCESS };
}

export function changeCreateAppModalVisible(visible) {
  return { type: CHANGE_CREATE_APPOINTMENT_VISIBLE, visible };
}

export function searchPatients(searchText) {
  return { type: SEARCH_PATIENTS_START, searchText };
}

export function searchPatientsSuccess(patients) {
  return { type: SEARCH_PATIENTS_SUCCESS, patients };
}

export function getPatient(id) {
  return { type: GET_PATIENT_START_CREATE_APP, id };
}

export function getPatientSuccessCreateApp(patient) {
  return { type: GET_PATIENT_SUCCESS_CREATE_APP, patient };
}

export function changePatientSelected(selected) {
  return { type: CHANGE_PATIENT_SELECTED, selected };
}

export function changeCreateAppExpectedArrivalDate(date) {
  return { type: CHANGE_CREATE_APP_EXPECTED_ARRIVAL_DATE, date };
}

export function changeCreateAppExpectedArrivalTime(time) {
  return { type: CHANGE_CREATE_APP_EXPECTED_ARRIVAL_TIME, time };
}

export function changeCreateAppDoctor(doctorId) {
  return { type: CHANGE_CREATE_APP_DOCTOR, doctorId };
}

export function changeCreateAppDuration(duration) {
  return { type: CHANGE_CREATE_APP_DURATION, duration };
}

export function changeCreateAppNote(e) {
  return { type: CHANGE_CREATE_APP_NOTE, e };
}

export function changeCreateAppSpecialNote(value) {
  return { type: CHANGE_CREATE_APP_SPECIAL_NOTE, value };
}

export function checkConfirmButtonDisable() {
  return { type: CHECK_CONFIRM_BUTTON_DISABLE };
}

export function changeCreateAppPatientName(name) {
  return { type: CHANGE_CREATE_APP_PATIENT_NAME, name };
}

export function changeCreateAppPatientPhone(phone) {
  return { type: CHANGE_CREATE_APP_PATIENT_PHONE, phone };
}

export function changeCreateAppPatientNationalId(nationalId) {
  return { type: CHANGE_CREATE_APP_PATIENT_NATIONAL_ID, nationalId };
}

export function changeCreateAppPatientBirth(birth) {
  return { type: CHANGE_CREATE_APP_PATIENT_BIRTH, birth };
}

export function createPatient(patient) {
  return { type: CREATE_PATIENT, patient };
}

export function createPatientSuccess(id) {
  return { type: CREATE_PATIENT_SUCCESS, id };
}

//edit appointment Modal
export function changeEditAppModalVisible(visible) {
  return { type: CHANGE_EDIT_APP_MODAL_VISIBLE, visible };
}

export function insertAppToEditAppModal(app) {
  return { type: INSERT_APP_TO_EDIT_APP_MODAL, app };
}

export function getPatientSuccessEditApp(patient) {
  return { type: GET_PATIENT_SUCCESS_EDIT_APP, patient };
}

export function deleteAppointment(id) {
  return { type: DELETE_APPOINTMENT_START, id };
}

export function deleteAppointmentSuccess() {
  return { type: DELETE_APPOINTMENT_SUCCESS };
}

export function changeEditAppointmentConformDelete(confirm) {
  return { type: CHANGE_EDIT_APP_CONFIRM_DELETE, confirm };
}

export function changeEditAppExpectedArrivalDate(date) {
  return { type: CHANGE_EDIT_APP_EXPECTED_ARRIVAL_DATE, date };
}

export function changeEditAppExpectedArrivalTime(time) {
  return { type: CHANGE_EDIT_APP_EXPECTED_ARRIVAL_TIME, time };
}

export function changeEditAppDoctor(doctorId) {
  return { type: CHANGE_EDIT_APP_DOCTOR, doctorId };
}

export function changeEditAppDuration(duration) {
  return { type: CHANGE_EDIT_APP_DURATION, duration };
}

export function changeEditAppNote(e) {
  return { type: CHANGE_EDIT_APP_NOTE, e };
}

export function changeEditAppSpecialNote(value) {
  return { type: CHANGE_EDIT_APP_SPECIAL_NOTE, value };
}

export function checkEditAppConfirmButtonDisable() {
  return { type: CHECK_EDIT_APP_CONFIRM_MODAL_BUTTON_DISABLE };
}

export function getCalendarEvent(start, end) {
  return { type: GET_CALENDAR_EVENT_START, range: { start, end } };
}

export function getCalendarEventSuccess(calendarEvents) {
  return { type: GET_CALENDAR_EVENT_SUCCESS, calendarEvents };
}

export function changeCreateCalEvtStartDate(date) {
  return { type: CHANGE_CREATE_CAL_EVT_START_DATE, date };
}

export function changeCreateCalEvtStartTime(time) {
  return { type: CAHNGE_CREATE_CAL_EVT_START_TIME, time };
}

export function changeCreateCalEvtEndDate(date) {
  return { type: CAHNGE_CREATE_CAL_EVT_END_DATE, date };
}

export function changeCreateCalEvtEndTime(time) {
  return { type: CAHNGE_CREATE_CAL_EVT_END_TIME, time };
}

export function changeCreateCalEvtDoctor(doctorId) {
  return { type: CHANGE_CREATE_CAL_EVT_DOCTOR, doctorId };
}

export function changeCreateCalEvtRepeat(repeat) {
  return { type: CHANGE_CREATE_CAL_EVT_REPEAT, repeat };
}

export function changeCreateCalEvtRepeatEndDate(date) {
  return { type: CHANGE_CREATE_CAL_EVT_REAPEAT_END_DATE, date };
}

export function changeCreateCalEvtNote(e) {
  return { type: CHANGE_CREATE_CAL_EVT_NOTE, e };
}

export function checkCreateCalEvtConfirmButtonDisable() {
  return { type: CHECK_CREATE_CAL_CONFIRM_BUTTON_DISABLE };
}

export function changeCreateCalModalVisible(visible) {
  return { type: CHANGE_CREATE_CAL_MODAL_VISIBLE, visible };
}

export function createCalEvt() {
  return { type: CREATE_CAL_EVT_START };
}

export function createCalEvtSuccess() {
  return { type: CREATE_CAL_EVT_SUCCESS };
}

export function getAllEvents() {
  return { type: GET_ALL_EVENTS };
}

export function insertCalEvtToEditCalEvtModal(calEvt) {
  return { type: INSERT_CAL_EVT_TO_EDIT_CAL_EVT_MODEL, calEvt };
}

export function changeEditCalModalVisible(visible) {
  return { type: CHANGE_EDIT_CAL_MODAL_VISIBLE, visible };
}

export function changeEditCalEvtStartDate(date) {
  return { type: CHANGE_EDIT_CAL_EVT_START_DATE, date };
}

export function changeEditCalEvtStartTime(time) {
  return { type: CAHNGE_EDIT_CAL_EVT_START_TIME, time };
}

export function changeEditCalEvtEndDate(date) {
  return { type: CAHNGE_EDIT_CAL_EVT_END_DATE, date };
}

export function changeEditCalEvtEndTime(time) {
  return { type: CAHNGE_EDIT_CAL_EVT_END_TIME, time };
}

export function changeEditCalEvtDoctor(doctorId) {
  return { type: CHANGE_EDIT_CAL_EVT_DOCTOR, doctorId };
}

export function changeEditCalEvtRepeat(repeat) {
  return { type: CHANGE_EDIT_CAL_EVT_REPEAT, repeat };
}

export function changeEditCalEvtRepeatEndDate(date) {
  return { type: CHANGE_EDIT_CAL_EVT_REPEAT_END_DATE, date };
}

export function changeEditCalEvtNote(e) {
  return { type: CHANGE_EDIT_CAL_EVT_NOTE, e };
}

export function changeEditCalEvtConfirmDelete(confirm) {
  return { type: CHANGE_EDIT_CAL_EVT_CONFIRM_DELETE, confirm };
}

export function checkEditCalEvtConfirmButtonDisable() {
  return { type: CHECK_EDIT_CAL_CONFIRM_BUTTON_DISABLE };
}

export function editCalendarEvt() {
  return { type: EDIT_CAL_EVT_START };
}

export function editCalendarEvtSuccess() {
  return { type: EDIT_CAL_EVT_SUCCESS };
}

export function deleteCalEvt(id) {
  return { type: DELETE_CAL_EVT_START, id };
}

export function deleteCalEvtSuccess() {
  return { type: DELETE_CAL_EVT_SUCCESS };
}

export function changeCalSlotDuration(duration) {
  return { type: CHANGE_CAL_SLOT_DURATION, duration };
}

export function getTodos() {
  return { type: GET_TODOS_START };
}

export function getTodosSuccess(todos) {
  return { type: GET_TODOS_SUCCESS, todos };
}

export function getSettings() {
  return { type: GET_SETTINGS_START };
}

export function getSettingsSuccess(settings) {
  return { type: GET_SETTINGS_SUCCESS, settings };
}

export function popoverCancelApp(appData) {
  return { type: POPOVER_CANCEL_APP_START, appData };
}

export function popoverCancelAppSuccess(appointment) {
  return { type: POPOVER_CANCEL_APP_SUCCESS, appointment };
}
