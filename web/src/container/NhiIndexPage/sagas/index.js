import { fork } from 'redux-saga/effects';
import {
  initNhiSalary,
  getNhiSalary,
  getDoctorNhiSalary,
  getOdIndexes,
  getToothClean,
  getValidNhiYearMonth,
  getValidNhiByYearMonth,
  getNhiOneByDisposalId,
} from './nhiIndex';

export default function* nhiIndexPage() {
  yield fork(initNhiSalary);
  yield fork(getNhiSalary);
  yield fork(getDoctorNhiSalary);
  yield fork(getOdIndexes);
  yield fork(getToothClean);
  yield fork(getValidNhiYearMonth);
  yield fork(getValidNhiByYearMonth);
  yield fork(getNhiOneByDisposalId);
}
