import { CREATE_APPOINTMENT, SEARCH_PATIENTS_START, GET_PATIENT_START_CREATE_APP, CREATE_PATIENT } from '../constant';
import { call, put, take, select } from 'redux-saga/effects';
import Appointment from '../../../models/appointment';
import Patient from '../../../models/patient';
import {
  createAppointmentSuccess,
  searchPatientsSuccess,
  getPatientSuccessCreateApp,
  createPatientSuccess,
  createAppointment as createAppointmentAction,
} from '../actions';
import convertPatientToOption from '../utils/convertPatientToOption';
import convertPatientToPatientDetail from '../utils/convertPatientToPatientDetail';
import { handleAppointmentForApi } from '../utils/handleAppointmentForApi';

export function* createAppointment() {
  while (true) {
    try {
      yield take(CREATE_APPOINTMENT);
      const getAppointment = state => state.appointmentPageReducer.createApp.appointment;
      const appointment = yield select(getAppointment);
      const data = handleAppointmentForApi(appointment);
      yield call(Appointment.create, data);
      yield put(createAppointmentSuccess());
    } catch (error) {
      // ignore
    }
  }
}

export function* searchPatients() {
  while (true) {
    try {
      const data = yield take(SEARCH_PATIENTS_START);
      const result = yield call(Patient.search, data.searchText);
      yield put(searchPatientsSuccess(convertPatientToOption(result)));
    } catch (error) {
      // ignore
    }
  }
}

export function* getPatient() {
  while (true) {
    try {
      const data = yield take(GET_PATIENT_START_CREATE_APP);
      const patient = yield call(Patient.getById, data.id);
      const appointments = yield call(Appointment.getAppointmentsByPatientId, data.id);
      yield put(getPatientSuccessCreateApp(convertPatientToPatientDetail(patient, appointments)));
    } catch (error) {
      // ignore
    }
  }
}

export function* createPatient() {
  while (true) {
    try {
      yield take(CREATE_PATIENT);
      const getPatient = state => state.appointmentPageReducer.createApp.patient;
      const patient = yield select(getPatient);
      const createdPatient = yield call(Patient.create, patient);
      yield put(createPatientSuccess(createdPatient.id));
      yield put(createAppointmentAction());
    } catch (error) {
      // ignore
    }
  }
}
