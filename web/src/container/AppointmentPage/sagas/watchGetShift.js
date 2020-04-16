import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_SHIFT_START } from '../constant';
import Shift from '../../../models/shift';
import { getShiftSuccess } from '../actions';

function* getShift({ start, end }) {
  try {
    const result = yield call(Shift.get, {
      'toDate.greaterOrEqualThan': start.toISOString(),
      'fromDate.lessThan': end.toISOString(),
    });

    yield put(getShiftSuccess(result));
  } catch (err) {
    console.log(err);
  }
}

export function* watchGetShift() {
  yield takeLatest(GET_SHIFT_START, getShift);
}
