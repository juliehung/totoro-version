import { take, select, call, put } from 'redux-saga/effects';
import { CREATE_SHIFT_START } from '../constant';
import { createShiftSuccess } from '../actions';
import { handleRepeatShift } from '../utils/handleRepeatShift';
import Shift from '../../../models/shift';

export function* createShift() {
  while (true) {
    try {
      const action = yield take(CREATE_SHIFT_START);
      const { data } = action;
      const selectDefaultShift = state => state.shiftPageReducer.shift.defaultShift;
      const defaultShift = yield select(selectDefaultShift);
      const shifts = handleRepeatShift(data, defaultShift);
      let createdShifts = [];
      for (let shift of shifts) {
        const createdShift = yield call(Shift.post, shift);
        createdShifts.push(createdShift);
      }
      yield put(createShiftSuccess(createdShifts));
    } catch (err) {
      console.log(err);
    }
  }
}
