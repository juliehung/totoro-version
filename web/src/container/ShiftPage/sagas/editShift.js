import { take, call, put, all } from 'redux-saga/effects';
import { EDIT_SHIFT_START } from '../constant';
import { editShiftSuccess } from '../actions';
import Shift from '../../../models/shift';

export function* editShift() {
  while (true) {
    try {
      const action = yield take(EDIT_SHIFT_START);
      const { oldShift, newShift } = action;
      const { fromDate, toDate, userId } = oldShift;
      const deleteConfig = {
        'toDate.greaterOrEqualThan': toDate,
        'fromDate.lessOrEqualThan': fromDate,
        'userId.equals': userId,
      };
      const ids = yield deleteShifts(deleteConfig);
      const shift = yield call(Shift.post, newShift);
      const result = { ids, shift };
      yield put(editShiftSuccess(result));
    } catch (err) {
      console.log(err);
    }
  }
}

function* deleteShifts(rule) {
  const currentShift = yield call(Shift.get, rule);
  yield all(currentShift.map(c => call(Shift.delete, c.id)));
  return currentShift.map(c => c.id);
}
