import { take, call, put } from 'redux-saga/effects';
import { DELETE_DEFAULT_SHIFT_START } from '../constant';
import { deleteDefaultShiftSuccess } from '../actions';
import Configuration, { defaultShiftConfigPrefix } from '../../../models/configuration';

export function* deleteDefaultShift() {
  while (true) {
    try {
      const { id } = yield take(DELETE_DEFAULT_SHIFT_START);
      yield call(Configuration.deleteMultiple, {
        configurations: [
          { configKey: `${defaultShiftConfigPrefix}.${id}.name` },
          { configKey: `${defaultShiftConfigPrefix}.${id}.time` },
        ],
      });
      yield put(deleteDefaultShiftSuccess(id));
    } catch (err) {
      console.log(err);
    }
  }
}
