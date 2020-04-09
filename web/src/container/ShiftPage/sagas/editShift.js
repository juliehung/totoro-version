import { take, call, put } from 'redux-saga/effects';
import { EDIT_SHIFT_START } from '../constant';
import { editShiftSuccess } from '../actions';
import Shift from '../../../models/shift';

export function* editShift() {
  while (true) {
    try {
      const action = yield take(EDIT_SHIFT_START);
      const { shift } = action;
      const result = yield call(Shift.put, shift);
      yield put(editShiftSuccess(result));
    } catch (err) {
      console.log(err);
    }
  }
}
