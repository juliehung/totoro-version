import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_CONFIG_START } from '../constant';
import { getConfigSuccess } from '../actions';
import Configuration, { xRayVendorPrefix, linkManagmentPrefix } from '../../../models/configuration';
import { parseXrayVendorConfigtoObject } from '../utils/parseXrayVendorConfigtoObject';

export function* getConfig() {
  try {
    const xRayVendorConfig = yield call(Configuration.get, { 'configKey.contains': xRayVendorPrefix });
    const xRayVendors = parseXrayVendorConfigtoObject(xRayVendorConfig);
    const linkManagment = yield call(Configuration.get, { 'configKey.contains': linkManagmentPrefix });
    const config = { xRayVendors, linkManagment };
    yield put(getConfigSuccess(config));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetConfig() {
  yield takeLatest(GET_CONFIG_START, getConfig);
}
