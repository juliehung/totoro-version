import { call, put, takeLatest, cancelled, select } from 'redux-saga/effects';
import { GET_APPOINTMENTS_START } from '../constant';
import { getAppointmentsSuccess } from '../actions';
import appointment from '../../../models/appointment';

const getCalendarRange = state => state.appointmentPageReducer.calendar.range;

export function* getAppointments() {
  const abortController = new AbortController();
  try {
    const range = yield select(getCalendarRange);
    const result = yield call(appointment.getBetween, range, abortController.signal);
    yield put(getAppointmentsSuccess(result));
  } catch (err) {
    console.log(err);
  } finally {
    if (yield cancelled()) {
      abortController.abort();
    }
  }
}

export function* watchGetAppointments() {
  yield takeLatest(GET_APPOINTMENTS_START, getAppointments);
}
