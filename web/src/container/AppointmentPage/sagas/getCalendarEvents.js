import { call, put, takeLatest } from 'redux-saga/effects';
import { getCalendarEventSuccess } from '../actions';
import { GET_CALENDAR_EVENT_START } from '../constant';
import Calendar from '../../../models/calendar';
import convertCalendarToEvent from '../utils/convertCalendarToEvent';

export function* watchGetCalendarEvents() {
  yield takeLatest(GET_CALENDAR_EVENT_START, getCalendarEvent);
}

function* getCalendarEvent({ range }) {
  try {
    const result = yield call(Calendar.getBetween, range);
    yield put(getCalendarEventSuccess(convertCalendarToEvent(result, range)));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}
