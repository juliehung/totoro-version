import { SEND_SMS } from '../constant';
import { call, take } from 'redux-saga/effects';
import Sms from '../../../models/sms';

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
