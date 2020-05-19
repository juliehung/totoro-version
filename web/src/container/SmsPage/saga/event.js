import {
  GET_EVENTS,
  SAVE_EVENT,
  SAVE_EVENT_AND_SEND_IMMEDIATELY,
  GET_CLINIC_REMAINING,
  EXECUTE_EVENT,
  DELETE_EVENT,
} from '../constant';
import { call, take, put } from 'redux-saga/effects';
import {
  getEventsSuccess,
  getClinicRemainingSuccess,
  executeEventSuccess,
  executeEventFailed,
  saveEventSuccess,
  deleteEventSuccess,
} from '../action';
import SmsEvent from '../../../models/smsEvent';

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

// auto save usage
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

// future support
export function* executeEvent() {
  while (true) {
    try {
      // 1. send
      const action = yield take(EXECUTE_EVENT);
      const executedResult = yield call(SmsEvent.execute, action.event.id);
      if (executedResult.status === 200) {
        // 3-1. reload:
        const remaining = yield call(SmsEvent.getRemaining);
        yield put(getClinicRemainingSuccess(remaining));
        const events = yield call(SmsEvent.get);
        yield put(getEventsSuccess(events));
      }
    } catch {
      yield put(executeEventFailed());
    }
  }
}

// future support
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

// current support
export function* saveEventAndSendImmediately() {
  while (true) {
    try {
      // 1. create
      const action = yield take(SAVE_EVENT_AND_SEND_IMMEDIATELY);
      const createdResult = yield call(SmsEvent.post, action.event);

      // 2. send
      const executedResult = yield call(SmsEvent.execute, createdResult.id);

      // success
      if (executedResult.status === 200) {
        yield put(executeEventSuccess());

        // and reload
        const remaining = yield call(SmsEvent.getRemaining);
        yield put(getClinicRemainingSuccess(remaining));
        const events = yield call(SmsEvent.get);
        yield put(getEventsSuccess(events));
        // or failed
      } else {
        throw new Error();
      }
    } catch {
      yield put(executeEventFailed());
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
