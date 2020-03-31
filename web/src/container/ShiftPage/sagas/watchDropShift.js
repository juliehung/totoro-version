import { take, call, put, fork } from 'redux-saga/effects';
import { SHIFT_DROP_START } from '../constant';
import { shiftDropSuccess } from '../actions';
import { generateApiObj } from '../utils/handleRepeatShift';
import Shift from '../../../models/shift';

function* dropShift(shiftObj) {
  try {
    const shifts = generateApiObj(shiftObj.resourceId, shiftObj.date, shiftObj.timeRange);
    let createdShifts = [];
    for (let shift of shifts) {
      const createdShift = yield call(Shift.post, shift);
      createdShifts.push(createdShift);
    }
    yield put(shiftDropSuccess(createdShifts));
  } catch (err) {
    console.log(err);
  }
}

export function* watchDropShift() {
  while (true) {
    const action = yield take(SHIFT_DROP_START);
    const shiftObj = action.shift;
    yield fork(dropShift, shiftObj);
  }
}
