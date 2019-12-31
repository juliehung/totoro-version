import { call, put, take } from 'redux-saga/effects';
import Appointment from '../../../models/appointment';
import { POPOVER_CANCEL_APP_START } from '../constant';
import { popoverCancelAppSuccess } from '../actions';

export function* popoverCancelApp() {
  while (true) {
    try {
      const data = yield take(POPOVER_CANCEL_APP_START);
      yield call(Appointment.editAppointment, data.appData);
      yield put(popoverCancelAppSuccess());
    } catch (error) {
      // ignore
    }
  }
}
