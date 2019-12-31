import { call, put, take } from 'redux-saga/effects';
import { DELETE_APPOINTMENT_START } from '../constant';
import { deleteAppointmentSuccess } from '../actions';
import Appointment from '../../../models/appointment';

export function* deleteAppointments() {
  while (true) {
    try {
      const data = yield take(DELETE_APPOINTMENT_START);
      yield call(Appointment.delete, data.id);
      yield put(deleteAppointmentSuccess());
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
