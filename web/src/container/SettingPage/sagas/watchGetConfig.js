import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_CONFIG_START } from '../constant';
import { getConfigSuccess } from '../actions';
import Configuration, { xRayVendorPrefix, linkManagmentPrefix, vixwinPathPrefix } from '../../../models/configuration';
import parseConfigToObject from '../../../utils/parseConfigToObject';

export function* getConfig() {
  try {
    const xRayVendorConfig = yield call(Configuration.get, { 'configKey.contains': xRayVendorPrefix });
    const xRayVendors = parseConfigToObject(xRayVendorConfig, xRayVendorPrefix);
    const vixwinPathConfig = yield call(Configuration.get, { 'configKey.contains': vixwinPathPrefix });
    const vixwinPath = parseConfigToObject(vixwinPathConfig, vixwinPathPrefix);
    const linkManagment = yield call(Configuration.get, { 'configKey.contains': linkManagmentPrefix });
    const config = { xRayVendors, vixwinPath, linkManagment };
    yield put(getConfigSuccess(config));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetConfig() {
  yield takeLatest(GET_CONFIG_START, getConfig);
}
