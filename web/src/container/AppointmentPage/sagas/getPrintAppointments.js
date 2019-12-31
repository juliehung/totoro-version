import { call, put, takeLatest } from 'redux-saga/effects';
import { getPrintAppointmentsSuccess } from '../actions';
import { CHANGE_PRINT_DATE } from '../constant';
import convertAppToPrintItem from '../utils/convertAppToPrintItem';
import appointment from '../../../models/appointment';

export function* getPrintAppointments({ date }) {
  try {
    const range = {
      start: date.clone().startOf('day'),
      end: date.clone().endOf('day'),
    };
    const result = yield call(appointment.getBetween, range);
    yield put(getPrintAppointmentsSuccess(convertAppToPrintItem(result)));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetPrintAppointments() {
  yield takeLatest(CHANGE_PRINT_DATE, getPrintAppointments);
}
