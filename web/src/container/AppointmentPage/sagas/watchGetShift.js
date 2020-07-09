import { call, put, takeLatest, select } from 'redux-saga/effects';
import { GET_SHIFT_START } from '../constant';
import Shift from '../../../models/shift';
import { getShiftSuccess } from '../actions';

const getCalendarRange = state => state.appointmentPageReducer.calendar.range;

function* getShift() {
  try {
    const range = yield select(getCalendarRange);
    const result = yield call(Shift.get, {
      'toDate.greaterOrEqualThan': range.start.toISOString(),
      'fromDate.lessThan': range.end.toISOString(),
    });

    yield put(getShiftSuccess(result));
  } catch (err) {
    console.log(err);
  }
}

export function* watchGetShift() {
  yield takeLatest(GET_SHIFT_START, getShift);
}
