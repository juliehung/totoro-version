import { take, call, put } from 'redux-saga/effects';
import Xray from '../../../models/xray';
import { OPEN_XRAY } from '../constant';
import { xrayGreeting, xrayGreetingSuccess, xrayGreetingFailure } from '../actions';
import moment from 'moment';
import { XRAY_VENDORS } from '../../AppointmentPage/constant';

export function* watchOpenXray() {
  while (true) {
    try {
      const action = yield take(OPEN_XRAY);
      yield put(xrayGreeting());
      yield call(Xray.greeting);
      yield call(openXray, action);
      yield put(xrayGreetingSuccess());
    } catch (error) {
      yield put(xrayGreetingFailure());
    }
  }
}

function* openXray(action) {
  try {
    const {
      data: { vendor, patient },
    } = action;
    console.log(vendor, patient);

    const patient_name = patient.name;
    const birthday = patient.birth ? moment(patient.birth).format('DD/MM/YYYY') : null;
    const gender = patient.gender ? patient.gender[0] : null;
    console.log(vendor);

    if (vendor === XRAY_VENDORS.vision) {
      const patient_id = patient.medicalId;
      yield call(Xray.get, { vendor, patient_id, patient_name, birthday, gender });
    } else if (vendor === XRAY_VENDORS.vixwin) {
      const patient_id = patient.nationalId;
      yield call(Xray.get, { vendor, patient_id, patient_name });
    }
  } catch (err) {
    console.log(err);
  }
}
