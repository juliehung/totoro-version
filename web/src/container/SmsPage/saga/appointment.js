import { GET_APPOINTMENTS } from '../constant';
import { call, take, put } from 'redux-saga/effects';
import { getAppointmentsSuccess } from '../action';
import Appointment from '../../../models/appointment';

export function* getAppointments() {
  while (true) {
    try {
      const data = yield take(GET_APPOINTMENTS);
      const result = yield call(Appointment.getBetween, data.range);
      yield put(getAppointmentsSuccess(result));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
