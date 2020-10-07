import { call, put, take, select } from 'redux-saga/effects';
import { CREATE_APPOINTMENT } from '../constant';
import Appointment from '../../../models/appointment';
import { handleAppointmentForApi } from '../../AppointmentPage/utils/handleAppointmentForApi';
import { createAppointmentSuccess } from '../actions';

const appointmentSelector = state => state.patientPageReducer.createAppointment.appointment;
const patientSelector = state => state.patientPageReducer.patient.patient;

export function* createAppointment() {
  while (true) {
    try {
      yield take(CREATE_APPOINTMENT);
      const patient = yield select(patientSelector);
      const appointment = yield select(appointmentSelector);
      const { id } = patient;
      const data = handleAppointmentForApi({ patientId: id, ...appointment });
      yield call(Appointment.create, data);
      yield put(createAppointmentSuccess());
    } catch (error) {
      console.log(error);
    }
  }
}
