import { NEXT_PAGE, NEXT_PAGE_DELAY } from '../constant';
import { put, take, delay } from 'redux-saga/effects';

export function* nextPage() {
  while (true) {
    try {
      yield take(NEXT_PAGE);
      yield put({ type: NEXT_PAGE_DELAY });
      yield delay(400);
    } catch (error) {}
  }
}
