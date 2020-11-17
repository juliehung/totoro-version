import {
  CHANGE_DRAWER_VISIBLE,
  CHANGE_DIAGNOSIS_NOTE_FOCUSED,
  INIT_PATIENT_DETAIL,
  PATIENT_NOT_FOUND,
  GET_PATIENT_SUCCESS,
  GET_DISPOSAL,
  GET_DISPOSAL_SUCCESS,
  GET_RECENT_TREATMENT_PROCEDURE,
  GET_RECENT_TREATMENT_PROCEDURE_SUCCESS,
  GET_ACCUMULATED_MEDICAL_RECORD,
  GET_ACCUMULATED_MEDICAL_RECORD_SUCCESS,
  GET_NHI_EXTEND_PATIENT,
  GET_NHI_EXTEND_PATIENT_SUCCESS,
  GET_APPOINTMENT,
  GET_APPOINTMENT_SUCCESS,
  GET_DOC_NP_HISTORY,
  GET_DOC_NP_HISTORY_SUCCESS,
  GET_REGISTRATION_TODAY,
  GET_REGISTRATION_TODAY_SUCCESS,
  ON_LEAVE_PAGE,
  CHANGE_CLINIC_NOTE,
  ADD_DATE_TO_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE_SUCCESS,
  RESTORE_CLINIC_NOTE,
  RESTORE_CLINIC_NOTE_UPDATE_SUCCESS,
  CHANGE_APPOINTMENT_LIST_MODAL_VISIBLE,
  SEARCH_PATIENTS_START,
  SEARCH_PATIENT_SUCCESS,
  CHANGE_TREATMENT_LIST_MODAL_VISIBLE,
  GET_NHI_PATIENT_STATUS,
  GET_NHI_PATIENT_STATUS_SUCCESS,
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

export function getDisposal() {
  return { type: GET_DISPOSAL };
}

export function getDisposalSuccess(disposals) {
  return { type: GET_DISPOSAL_SUCCESS, disposals };
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

export function getNhiPatientStatus() {
  return { type: GET_NHI_PATIENT_STATUS };
}

export function getNhiPatientStatusSuccess({ nhiPatientScalingStatus, nhiPatientFluorideStatus }) {
  return { type: GET_NHI_PATIENT_STATUS_SUCCESS, nhiPatientScalingStatus, nhiPatientFluorideStatus };
}

export function getAppointment() {
  return { type: GET_APPOINTMENT };
}

export function getAppointmentSuccess(appointment) {
  return { type: GET_APPOINTMENT_SUCCESS, appointment };
}

export function getDocNpHistory() {
  return { type: GET_DOC_NP_HISTORY };
}

export function getDocNpHistorySuccess(docNps) {
  return { type: GET_DOC_NP_HISTORY_SUCCESS, docNps };
}

export function getRegistrationToday() {
  return { type: GET_REGISTRATION_TODAY };
}

export function getRegistrationTodaySuccess(registrations) {
  return { type: GET_REGISTRATION_TODAY_SUCCESS, registrations };
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

export function updateClinicNoteSuccess(patient) {
  return { type: UPDATE_CLINIC_NOTE_SUCCESS, patient };
}

export function restoreClinicNote() {
  return { type: RESTORE_CLINIC_NOTE };
}

export function restoreClinicNoteUpdateSuccess() {
  return { type: RESTORE_CLINIC_NOTE_UPDATE_SUCCESS };
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

// treatment list modal
export function changeTreatmentListModalVisible(visible) {
  return { type: CHANGE_TREATMENT_LIST_MODAL_VISIBLE, visible };
}
