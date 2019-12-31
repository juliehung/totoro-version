import { call, put, take } from 'redux-saga/effects';
import { DELETE_CAL_EVT_START } from '../constant';
import { deleteCalEvtSuccess } from '../actions';
import Calendar from '../../../models/calendar';

export function* deleteCalendarEvent() {
  while (true) {
    try {
      const data = yield take(DELETE_CAL_EVT_START);
      yield call(Calendar.delete, data.id);
      yield put(deleteCalEvtSuccess());
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
