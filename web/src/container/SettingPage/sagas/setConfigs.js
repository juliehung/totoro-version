import { call, take, put } from 'redux-saga/effects';
import Configuration from '../../../models/configuration';
import { SET_CONFIGS } from '../constant';
import { setConfigSuccess } from '../actions';

export function* setConfigs() {
  while (true) {
    try {
      const { configs } = yield take(SET_CONFIGS);

      const updateConfigs = configs.update ?? [];
      const createConfigs = configs.create ?? [];

      if (updateConfigs.length) {
        yield call(Configuration.updateMultiple, {
          configurations: updateConfigs,
        });
      }
      if (createConfigs.length) {
        yield call(Configuration.createMultiple, {
          configurations: createConfigs,
        });
      }
      yield put(setConfigSuccess());
    } catch (err) {
      console.log(err);
    }
  }
}
