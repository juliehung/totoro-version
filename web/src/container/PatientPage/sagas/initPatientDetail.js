import { take, call, put, fork, delay } from 'redux-saga/effects';
import moment from 'moment';
import { INIT_PATIENT_DETAIL } from '../constant';
import NhiExtendPaitent from '../../../models/nhiExtendPaitent';
import NhiPatientStatus from '../../../models/nhiPaitentStatus';
import TreatmentProcedure from '../../../models/treatmentProcedure';
import NhiAccumulatedMedicalRecords from '../../../models/nhiAccumulatedMedicalRecords';
import Patient from '../../../models/patient';
import Appointment from '../../../models/appointment';
import Disposal from '../../../models/disposal';
import DocNps from '../../../models/docNps';
import {
  patientNotFound,
  getPatientSuccess,
  getDisposal,
  getDisposalSuccess,
  getRecentTreatmentProcedure,
  getRecentTreatmentProcedureSuccess,
  getAccumulatedMedicalRecords,
  getAccumulatedMedicalRecordSuccess,
  getNhiExtendPatient,
  getNhiExtendPatientSuccess,
  getAppointment,
  getAppointmentSuccess,
  getDocNpHistory,
  getDocNpHistorySuccess,
  getNhiPatientStatus,
  getNhiPatientStatusSuccess,
  getPatientImages,
  getPatientImagesSuccess,
} from '../actions';

export function* initPatientDetail() {
  while (true) {
    try {
      const { id } = yield take(INIT_PATIENT_DETAIL);
      const patient = yield call(Patient.getById, id);
      yield delay(300);
      yield put(getPatientSuccess(patient));
      yield fork(getRecentTreatmentProcedureSaga, id);
      yield fork(getDisposalSaga, id);
      yield fork(getAccumulatedMedicalRecordSage, id);
      yield fork(getNhiPatientStatusSaga, id);
      yield fork(getNhiExtendPatientSaga, id);
      yield fork(getAppointmentSaga, id);
      yield fork(getDocNpHistorySaga, id);
      yield fork(getPatientImagesSaga, id);
    } catch (error) {
      yield put(patientNotFound());
    }
  }
}

function* getRecentTreatmentProcedureSaga(id) {
  yield put(getRecentTreatmentProcedure());
  const begin = moment().add(-18, 'M').toISOString();
  const end = moment().toISOString();
  const treatmentProcedure = yield call(TreatmentProcedure.getRecently, id, { begin, end });
  yield put(getRecentTreatmentProcedureSuccess(treatmentProcedure));
}

function* getDisposalSaga(id) {
  yield put(getDisposal());
  const disposal = yield call(Disposal.getByPatientId, id);
  yield put(getDisposalSuccess(disposal));
}

function* getAccumulatedMedicalRecordSage(id) {
  yield put(getAccumulatedMedicalRecords());
  const nhiAccumulatedMedicalRecords = yield call(NhiAccumulatedMedicalRecords.getByPatientId, id);
  yield put(getAccumulatedMedicalRecordSuccess(nhiAccumulatedMedicalRecords));
}

function* getNhiPatientStatusSaga(id) {
  yield put(getNhiPatientStatus());
  const nhiPatientScalingStatus = yield call(NhiPatientStatus.getById, id, '91004C');
  const nhiPatientFluorideStatus = yield call(NhiPatientStatus.getById, id, '81');
  yield put(getNhiPatientStatusSuccess({ nhiPatientScalingStatus, nhiPatientFluorideStatus }));
}

function* getNhiExtendPatientSaga(id) {
  yield put(getNhiExtendPatient());
  const nhiExtendPatient = yield call(NhiExtendPaitent.getById, id);
  yield put(getNhiExtendPatientSuccess(nhiExtendPatient));
}

function* getAppointmentSaga(id) {
  yield put(getAppointment());
  const appointment = yield call(Appointment.getAppointmentsByPatientId, id);
  yield put(getAppointmentSuccess(appointment));
}

function* getDocNpHistorySaga(id) {
  yield put(getDocNpHistory());
  const docNps = yield call(DocNps.getByPid, id);
  yield put(getDocNpHistorySuccess(docNps));
}

function* getPatientImagesSaga(id) {
  yield put(getPatientImages());
  const patientImages = yield call(Patient.getImagesById, id);
  yield put(getPatientImagesSuccess(patientImages));
}
