import { PREV_PAGE, PREV_PAGE_DELAY } from '../constant';
import { put, take, delay } from 'redux-saga/effects';

export function* prevPage() {
  while (true) {
    try {
      yield take(PREV_PAGE);
      yield put({ type: PREV_PAGE_DELAY });
      yield delay(400);
    } catch (error) {}
  }
}
