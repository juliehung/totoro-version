import { GOTO_PAGE, GOTO_PAGE_DELAY } from '../constant';
import { put, take, delay } from 'redux-saga/effects';

export function* gotoPage() {
  while (true) {
    try {
      const data = yield take(GOTO_PAGE);
      const page = data.page;
      yield delay(0);
      yield put({ type: GOTO_PAGE_DELAY, page });
    } catch (error) {
      console.log(error);
    }
  }
}
