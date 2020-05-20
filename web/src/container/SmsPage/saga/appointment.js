import { GET_APPOINTMENTS } from '../constant';
import { call, take, put, all } from 'redux-saga/effects';
import { getAppointmentsSuccess } from '../action';
import Appointment from '../../../models/appointment';
import SmsView from '../../../models/smsView';

export function* getAppointments() {
  while (true) {
    try {
      const data = yield take(GET_APPOINTMENTS);
      const result = yield call(Appointment.getBetween, data.range);
      const lastSents = yield all(result.map(a => call(SmsView.getById, a.id)));
      const processed = result.map((a, i) => {
        return { ...a, lastSent: lastSents[i] ? lastSents[i].lastTime : '' };
      });
      yield put(getAppointmentsSuccess(processed));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
