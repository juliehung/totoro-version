import { take, call, put, fork, delay } from 'redux-saga/effects';
import { INIT_PATIENT_DETAIL } from '../constant';
import NhiExtendPaitent from '../../../models/nhiExtendPaitent';
import TreatmentProcedure from '../../../models/treatmentProcedure';
import NhiAccumulatedMedicalRecords from '../../../models/nhiAccumulatedMedicalRecords';
import Patient from '../../../models/patient';
import Appointment from '../../../models/appointment';
import {
  patientNotFound,
  getPatientSuccess,
  getRecentTreatmentProcedure,
  getRecentTreatmentProcedureSuccess,
  getAccumulatedMedicalRecords,
  getAccumulatedMedicalRecordSuccess,
  getNhiExtendPatient,
  getNhiExtendPatientSuccess,
  getAppointment,
  getAppointmentSuccess,
} from '../actions';

export function* initPatientDetail() {
  while (true) {
    try {
      const { id } = yield take(INIT_PATIENT_DETAIL);
      const patient = yield call(Patient.getById, id);
      yield delay(300);
      yield put(getPatientSuccess(patient));
      yield fork(getTreatmentProcedureSaga, id);
      yield fork(getAccumulatedMedicalRecordSage, id);
      yield fork(getNhiExtendPatientSaga, id);
      yield fork(getAppointmentSaga, id);
    } catch (error) {
      if (error.response.status === 404) {
        yield put(patientNotFound());
      }
    }
  }
}

function* getTreatmentProcedureSaga(id) {
  yield put(getRecentTreatmentProcedure());
  const treatmentProcedure = yield call(TreatmentProcedure.getRecently, id);
  yield put(getRecentTreatmentProcedureSuccess(treatmentProcedure));
}

function* getAccumulatedMedicalRecordSage(id) {
  yield put(getAccumulatedMedicalRecords());
  const nhiAccumulatedMedicalRecords = yield call(NhiAccumulatedMedicalRecords.getByPatientId, id);
  yield put(getAccumulatedMedicalRecordSuccess(nhiAccumulatedMedicalRecords));
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
