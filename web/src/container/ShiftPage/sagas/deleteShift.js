import { call, put, take } from 'redux-saga/effects';
import { DELETE_SHIFT_START } from '../constant';
import { deleteShiftSuccess } from '../actions';
import Shift from '../../../models/shift';

export function* deleteShift() {
  while (true) {
    try {
      const { id } = yield take(DELETE_SHIFT_START);
      yield call(Shift.delete, id);
      yield put(deleteShiftSuccess(id));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
