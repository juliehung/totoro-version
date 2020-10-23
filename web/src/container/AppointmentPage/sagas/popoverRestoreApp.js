import { call, put, take } from 'redux-saga/effects';
import Appointment from '../../../models/appointment';
import { POPOVER_RESTORE_APP_START } from '../constant';
import { popoverRestoreAppSuccess } from '../actions';

export function* popoverRestoreApp() {
  while (true) {
    try {
      const data = yield take(POPOVER_RESTORE_APP_START);
      const appointment = yield call(Appointment.editAppointment, data.appData);
      yield put(popoverRestoreAppSuccess(appointment));
    } catch (error) {
      // ignore
    }
  }
}
