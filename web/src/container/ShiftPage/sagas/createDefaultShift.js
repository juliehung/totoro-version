import { take, select, call, put } from 'redux-saga/effects';
import { CREATE_DEFAULT_SHIFT_START } from '../constant';
import { createDefaultShiftSuccess } from '../actions';
import Configuration, { defaultShiftConfigPrefix } from '../../../models/configuration';
import { handleShiftForApi } from '../utils/handleShiftForApi';

const selectNewDefaultShift = state => state.shiftPageReducer.defaultShift.newShift;

export function* createDefaultShift() {
  while (true) {
    try {
      yield take(CREATE_DEFAULT_SHIFT_START);
      const newDefaultShift = yield select(selectNewDefaultShift);
      const shiftForApi = handleShiftForApi(newDefaultShift);

      yield call(Configuration.createMultiple, {
        configurations: [
          { configKey: `${defaultShiftConfigPrefix}.${shiftForApi.id}.name`, configValue: shiftForApi.name },
          { configKey: `${defaultShiftConfigPrefix}.${shiftForApi.id}.time`, configValue: shiftForApi.time },
        ],
      });

      yield put(createDefaultShiftSuccess());
    } catch (err) {
      console.log(err);
    }
  }
}
