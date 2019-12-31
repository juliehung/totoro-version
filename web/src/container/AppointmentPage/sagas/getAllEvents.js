import { put, takeLatest, select } from 'redux-saga/effects';
import { GET_ALL_EVENTS } from '../constant';
import { getAppointments, getCalendarEvent } from '../actions';

export function* getAllEvents() {
  try {
    const getCalendarRange = state => state.appointmentPageReducer.calendar.range;
    const { start, end } = yield select(getCalendarRange);
    yield put(getAppointments(start, end));
    yield put(getCalendarEvent(start, end));
  } catch (err) {
    console.log(err);
  }
}

export function* watchGetAllEvents() {
  yield takeLatest(GET_ALL_EVENTS, getAllEvents);
}
