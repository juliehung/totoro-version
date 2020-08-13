import { fork } from 'redux-saga/effects';
import nhiIndex from './nhiIndex';

export default function* nhiIndexPage() {
  yield fork(nhiIndex);
}
