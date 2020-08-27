import { call, put, take, all } from 'redux-saga/effects';
import { DELETE_SHIFT_START } from '../constant';
import { deleteShiftSuccess } from '../actions';
import Shift from '../../../models/shift';

export function* deleteShift() {
  while (true) {
    try {
      const { event } = yield take(DELETE_SHIFT_START);
      const { fromDate, toDate, userId } = event;

      const deleteConfig = {
        'toDate.greaterOrEqualThan': toDate,
        'fromDate.lessOrEqualThan': fromDate,
        'userId.equals': userId,
      };

      yield deleteShifts(deleteConfig);
      yield put(deleteShiftSuccess(event));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}

function* deleteShifts(rule) {
  const currentShift = yield call(Shift.get, rule);
  yield all(currentShift.map(c => call(Shift.delete, c.id)));
}
