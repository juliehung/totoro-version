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
import registration from '../../../models/registration';
import moment from 'moment';

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
      let result = [];
      if (!data.searchText || data.searchText.length === 0) {
        const registrations = yield call(registration.getBetween, {
          start: moment().startOf('d'),
          end: moment().endOf('d'),
        });

        const patients = registrations
          .filter(r => r.status !== 'CANCEL' && r.registration && r.registration.id)
          .sort((a, b) => moment(b.registration.arrivalTime).unix() - moment(a.registration.arrivalTime).unix())
          .map(r => r.patient);

        const filteredPatients = patients.filter(
          (patient, index, self) => index === self.findIndex(p => p.id === patient.id),
        );
        result = convertPatientToOption(filteredPatients);
      } else {
        const patient = yield call(Patient.search, data.searchText);
        result = convertPatientToOption(patient);
      }
      yield put(searchPatientsSuccess(result));
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
