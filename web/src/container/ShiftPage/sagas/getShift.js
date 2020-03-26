import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_SHIFT_START } from '../constant';
import shift from '../../../models/shift';
import { getShiftSuccess } from '../actions';

export function* getShift({ range }) {
  try {
    const result = yield call(shift.get, {
      'toDate.greaterOrEqualThan': range.start.toISOString(),
      'fromDate.lessOrEqualThan': range.end.toISOString(),
    });

    yield put(getShiftSuccess(result));
  } catch (err) {
    console.log(err);
  }
}

export function* watchGetShift() {
  yield takeLatest(GET_SHIFT_START, getShift);
}
