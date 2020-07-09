import { call, put, takeLatest, cancelled, select } from 'redux-saga/effects';
import { getCalendarEventSuccess } from '../actions';
import { GET_CALENDAR_EVENT_START } from '../constant';
import Calendar from '../../../models/calendar';
import convertCalendarToEvent from '../utils/convertCalendarToEvent';

const getCalendarRange = state => state.appointmentPageReducer.calendar.range;

export function* watchGetCalendarEvents() {
  yield takeLatest(GET_CALENDAR_EVENT_START, getCalendarEvent);
}

function* getCalendarEvent() {
  const abortController = new AbortController();
  try {
    const range = yield select(getCalendarRange);
    const result = yield call(Calendar.getBetween, range, abortController.signal);
    yield put(getCalendarEventSuccess(convertCalendarToEvent(result, range)));
  } catch (err) {
    //  ignore
    console.log(err);
  } finally {
    if (yield cancelled()) {
      abortController.abort();
    }
  }
}
