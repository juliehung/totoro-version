import { call, put, take } from 'redux-saga/effects';
import Appointment from '../../../models/appointment';
import Patient from '../../../models/patient';
import { editAppointmentSuccess, getPatientSuccessEditApp } from '../actions';
import { EDIT_APPOINTMENT_START, INSERT_APP_TO_EDIT_APP_MODAL } from '../constant';
import convertPatientToPatientDetail from '../utils/convertPatientToPatientDetail';

export function* editAppointment() {
  while (true) {
    try {
      const data = yield take(EDIT_APPOINTMENT_START);
      const appointment = yield call(Appointment.editAppointment, data.app);
      yield put(editAppointmentSuccess(appointment));
    } catch (error) {
      console.log(error);

      // ignore
    }
  }
}

export function* getPatient() {
  while (true) {
    try {
      const data = yield take(INSERT_APP_TO_EDIT_APP_MODAL);
      const patient = yield call(Patient.getById, data.app.patientId);
      const appointments = yield call(Appointment.getAppointmentsByPatientId, data.app.patientId);
      yield put(getPatientSuccessEditApp(convertPatientToPatientDetail(patient, appointments)));
    } catch (error) {
      // ignore
    }
  }
}
