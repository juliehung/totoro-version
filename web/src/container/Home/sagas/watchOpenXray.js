import { take, call, put, select } from 'redux-saga/effects';
import Xray from '../../../models/xray';
import { OPEN_XRAY, XRAY_VENDORS } from '../constant';
import { xrayGreeting, xrayGreetingSuccess, xrayGreetingFailure } from '../actions';
import moment from 'moment';

const getVixwinPath = state => state.settingPageReducer.configurations.config.vixwinPath;
const pathValidator = path => path && path.length;

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
      data: { vendor, appointment },
    } = action;

    const patient_name = appointment.patientName;
    const birthday = appointment.birth ? moment(appointment.birth).format('DD/MM/YYYY') : null;
    const gender = appointment.gender ? appointment.gender[0] : null;

    if (vendor === XRAY_VENDORS.vision) {
      const patient_id = appointment.medicalId;
      yield call(Xray.get, { vendor, patient_id, patient_name, birthday, gender });
    } else if (vendor === XRAY_VENDORS.vixwin) {
      const vixwinPath = yield select(getVixwinPath);
      const vixwin_path = vixwinPath.application;
      const image_path = vixwinPath.image;
      const patient_id = appointment.nationalId;
      yield call(Xray.get, {
        vendor,
        patient_id,
        patient_name,
        ...(pathValidator(vixwin_path) && { vixwin_path }),
        ...(pathValidator(image_path) && { image_path }),
      });
    }
  } catch (err) {
    console.log(err);
  }
}
