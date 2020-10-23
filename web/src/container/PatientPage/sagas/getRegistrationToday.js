import { call, take, put, delay } from 'redux-saga/effects';
import moment from 'moment';
import { GET_REGISTRATION_TODAY } from '../constant';
import Registration from '../../../models/registration';
import { getRegistrationTodaySuccess } from '../actions';

export function* getRegistrationToday() {
  while (true) {
    try {
      yield take(GET_REGISTRATION_TODAY);
      yield delay(500);
      const registrations = yield call(Registration.getBetween, {
        start: moment().startOf('d'),
        end: moment().endOf('d'),
      });
      yield put(getRegistrationTodaySuccess(registrations));
    } catch (error) {
      // ignore
      console.log('error', error);
    }
  }
}
