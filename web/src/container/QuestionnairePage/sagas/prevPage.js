import { PREV_PAGE, PREV_PAGE_DELAY } from '../constant';
import { put, take, delay } from 'redux-saga/effects';

export function* prevPage() {
  while (true) {
    try {
      yield take(PREV_PAGE);
      yield delay(0);
      yield put({ type: PREV_PAGE_DELAY });
    } catch (error) {}
  }
}
