import { SEND_SMS } from '../constant';
import { call, put, take, select } from 'redux-saga/effects';
import Sms from '../../../models/sms';
import { sendSms } from '../actions';

export function* sendSmsAsync() {
  while (true) {
    try {
      var data = yield take(SEND_SMS);
      const appointment = data.appointment;
      yield call(Sms.send, appointment);

      // TODO: show success?
    } catch (error) {
      // ignore
    }
  }
}
