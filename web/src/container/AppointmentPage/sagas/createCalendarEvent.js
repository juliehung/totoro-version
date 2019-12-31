import { CREATE_CAL_EVT_START } from '../constant';
import { call, put, take, select } from 'redux-saga/effects';
import { handleCalendarEventForApi } from '../utils/handleCalendarEventForApi';
import Calendar from '../../../models/calendar';
import { createCalEvtSuccess } from '../actions';

export function* createCalendarEvent() {
  while (true) {
    try {
      yield take(CREATE_CAL_EVT_START);
      const getCalEvt = state => state.appointmentPageReducer.createCalendarEvt.event;
      const calendarEvent = yield select(getCalEvt);
      const calendarEventForApi = handleCalendarEventForApi(calendarEvent);
      yield call(Calendar.create, calendarEventForApi);
      yield put(createCalEvtSuccess());
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
