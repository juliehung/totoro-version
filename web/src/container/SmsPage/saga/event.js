import {
  GET_EVENTS,
  SAVE_EVENT,
  GET_CLINIC_REMAINING,
  EXECUTE_EVENT,
  DELETE_EVENT,
} from '../constant';
import { call, take, put, all } from 'redux-saga/effects';
import {
  getEventsSuccess,
  getClinicRemainingSuccess,
  executeEventSuccess,
  executeEventFailed,
  saveEventSuccess,
  deleteEventSuccess,
} from '../action';
import SmsEvent from '../../../models/smsEvent';
import SmsView from '../../../models/smsView';

export function* getEvents() {
  while (true) {
    try {
      const action = yield take(GET_EVENTS);
      const result = yield call(SmsEvent.get, action.params.page, action.params.size);
      yield put(getEventsSuccess(result));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}

export function* saveEvent() {
  while (true) {
    try {
      // 1. create
      const action = yield take(SAVE_EVENT);
      const identity = action.event.id !== null ? action.event.id : action.event.tempId;
      const result = yield call(SmsEvent.post, action.event);
      yield put(saveEventSuccess(result, identity));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
export function* executeEvent() {
  while (true) {
    try {
      // 1. send
      const action = yield take(EXECUTE_EVENT);
      const appIds = action.event.sms.map(m => m.metadata.appointmentId)
      const executedResult = yield call(SmsEvent.execute, action.event.id);
      if (executedResult.status === 200) {
        // remeber lastSent
        yield all(appIds.map(a => call(SmsView.post, a)));
        yield put(executeEventSuccess());

        const remaining = yield call(SmsEvent.getRemaining);
        yield put(getClinicRemainingSuccess(remaining));
        const events = yield call(SmsEvent.get, 0, 10);
        yield put(getEventsSuccess(events));
      } else {
        throw new Error();
      }
    } catch {
      yield put(executeEventFailed());
    }
  }
}

export function* deleteEvent() {
  while (true) {
    try {
      const action = yield take(DELETE_EVENT);
      const isSucess = yield call(SmsEvent.delete, action.id);
      // 2. reload
      if (isSucess) yield put(deleteEventSuccess(action.id));
    } catch (err) {
      console.log(err);
    }
  }
}

export function* getClinicRemaining() {
  while (true) {
    try {
      yield take(GET_CLINIC_REMAINING);
      const remaining = yield call(SmsEvent.getRemaining);
      yield put(getClinicRemainingSuccess(remaining));
    } catch (err) {
      //  ignore
      console.log(err);
    }
  }
}
