import {
  CHANGE_DRAWER_VISIBLE,
  CHANGE_DIAGNOSIS_NOTE_FOCUSED,
  INIT_PATIENT_DETAIL,
  PATIENT_NOT_FOUND,
  GET_PATIENT_SUCCESS,
  GET_RECENT_TREATMENT_PROCEDURE,
  GET_RECENT_TREATMENT_PROCEDURE_SUCCESS,
  GET_ACCUMULATED_MEDICAL_RECORD,
  GET_ACCUMULATED_MEDICAL_RECORD_SUCCESS,
  GET_NHI_EXTEND_PATIENT,
  GET_NHI_EXTEND_PATIENT_SUCCESS,
  GET_APPOINTMENT,
  GET_APPOINTMENT_SUCCESS,
  ON_LEAVE_PAGE,
  CHANGE_CLINIC_NOTE,
  ADD_DATE_TO_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE_SUCCESS,
  RESTORE_CLINIC_NOTE,
  CHANGE_APPOINTMENT_MODAL_VISIBLE,
  CHANGE_APPOINTMENT_NOTE,
  CHANGE_APPOINTMENT_SPECIAL_NOTE,
  CHANGE_APPOINTMENT_DURATION,
  CHANGE_APPOINTMENT_DOCTOR,
  CHANGE_APPOINTMENT_EXPECT_ARRIVAL_TIME,
  CHANGE_APPOINTMENT_EXPECT_ARRIVAL_DATE,
  CHANGE_APPOINTMENT_COLOR,
  CHECK_CONFIRM_BUTTON_DISABLE,
  CREATE_APPOINTMENT,
  CREATE_APPOINTMENT_SUCCESS,
  CHANGE_APPOINTMENT_LIST_MODAL_VISIBLE,
  SEARCH_PATIENTS_START,
  SEARCH_PATIENT_SUCCESS,
} from './constant';

export function changeDrawerVisible(visible) {
  return { type: CHANGE_DRAWER_VISIBLE, visible };
}

export function changeDiagnosisNoteFocused(focused) {
  return { type: CHANGE_DIAGNOSIS_NOTE_FOCUSED, focused };
}

export function initPatientDetail(id) {
  return { type: INIT_PATIENT_DETAIL, id };
}

export function patientNotFound() {
  return { type: PATIENT_NOT_FOUND };
}

export function getPatientSuccess(patient) {
  return { type: GET_PATIENT_SUCCESS, patient };
}

export function getRecentTreatmentProcedure() {
  return { type: GET_RECENT_TREATMENT_PROCEDURE };
}

export function getRecentTreatmentProcedureSuccess(treatmentProcedures) {
  return { type: GET_RECENT_TREATMENT_PROCEDURE_SUCCESS, treatmentProcedures };
}

export function getAccumulatedMedicalRecords() {
  return { type: GET_ACCUMULATED_MEDICAL_RECORD };
}

export function getAccumulatedMedicalRecordSuccess(nhiAccumulatedMedicalRecords) {
  return { type: GET_ACCUMULATED_MEDICAL_RECORD_SUCCESS, nhiAccumulatedMedicalRecords };
}

export function getNhiExtendPatient() {
  return { type: GET_NHI_EXTEND_PATIENT };
}

export function getNhiExtendPatientSuccess(nhiExtendPatient) {
  return { type: GET_NHI_EXTEND_PATIENT_SUCCESS, nhiExtendPatient };
}

export function getAppointment() {
  return { type: GET_APPOINTMENT };
}

export function getAppointmentSuccess(appointment) {
  return { type: GET_APPOINTMENT_SUCCESS, appointment };
}

export function onLeavePage() {
  return { type: ON_LEAVE_PAGE };
}

export function changeClinicNote(clinicNote) {
  return { type: CHANGE_CLINIC_NOTE, clinicNote };
}

export function addDateClinicNote() {
  return { type: ADD_DATE_TO_CLINIC_NOTE };
}

export function updateClinicNote() {
  return { type: UPDATE_CLINIC_NOTE };
}

export function updateClinicNoteSuccess() {
  return { type: UPDATE_CLINIC_NOTE_SUCCESS };
}

export function restoreClinicNote() {
  return { type: RESTORE_CLINIC_NOTE };
}

// create appointment modal
export function changeCreateAppointmentModalVisible(visible) {
  return { type: CHANGE_APPOINTMENT_MODAL_VISIBLE, visible };
}

export function changeCreateAppointmentNote(note) {
  return { type: CHANGE_APPOINTMENT_NOTE, note };
}

export function changeCreateAppointmenSpecialtNote(value) {
  return { type: CHANGE_APPOINTMENT_SPECIAL_NOTE, value };
}

export function changeCreateAppointmentDuration(duration) {
  return { type: CHANGE_APPOINTMENT_DURATION, duration };
}

export function changeCreateAppointmentDoctor(doctorId) {
  return { type: CHANGE_APPOINTMENT_DOCTOR, doctorId };
}

export function changeCreateAppointmentExpectedArrivalTime(time) {
  return { type: CHANGE_APPOINTMENT_EXPECT_ARRIVAL_TIME, time };
}

export function changeCreateAppointmentExpectedArrivalDate(date) {
  return { type: CHANGE_APPOINTMENT_EXPECT_ARRIVAL_DATE, date };
}

export function changeAppointmentColor(colorId) {
  return { type: CHANGE_APPOINTMENT_COLOR, colorId };
}

export function checkConfirmButtonDisable() {
  return { type: CHECK_CONFIRM_BUTTON_DISABLE };
}

export function createAppointment() {
  return { type: CREATE_APPOINTMENT };
}

export function createAppointmentSuccess() {
  return { type: CREATE_APPOINTMENT_SUCCESS };
}

export function changeAppointmentListModalVisible(visible) {
  return { type: CHANGE_APPOINTMENT_LIST_MODAL_VISIBLE, visible };
}

// search patient drawer
export function searchPatient(text) {
  return { type: SEARCH_PATIENTS_START, text };
}

export function searchPatientSuccess(patients, searchText) {
  return { type: SEARCH_PATIENT_SUCCESS, patients, searchText };
}
