import { fork } from 'redux-saga/effects';
import { getOdIndexes, getDoctorNhiExam, getDoctorNhiTx } from './nhiIndex';

export default function* nhiIndexPage() {
  yield fork(getOdIndexes);
  yield fork(getDoctorNhiExam);
  yield fork(getDoctorNhiTx);
}
