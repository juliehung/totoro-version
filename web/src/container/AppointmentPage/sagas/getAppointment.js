import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_APPOINTMENTS_START } from '../constant';
import { getAppointmentsSuccess } from '../actions';
import appointment from '../../../models/appointment';

export function* getAppointments({ range }) {
  try {
    const result = yield call(appointment.getBetween, range);
    yield put(getAppointmentsSuccess(result));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetAppointments() {
  yield takeLatest(GET_APPOINTMENTS_START, getAppointments);
}
