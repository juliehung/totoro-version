import { call, put, take, select } from 'redux-saga/effects';
import Calendar from '../../../models/calendar';
import { editCalendarEvtSuccess } from '../actions';
import { EDIT_CAL_EVT_START } from '../constant';
import { handleCalendarEventForApi } from '../utils/handleCalendarEventForApi';

export function* editCalendarEvent() {
  while (true) {
    try {
      yield take(EDIT_CAL_EVT_START);
      const getCalendarRange = state => state.appointmentPageReducer.editCalendarEvt.event;
      const calEvt = yield select(getCalendarRange);
      const calEvtForApi = handleCalendarEventForApi(calEvt);
      yield call(Calendar.edit, calEvtForApi);
      yield put(editCalendarEvtSuccess());
    } catch (error) {
      // ignore
    }
  }
}
