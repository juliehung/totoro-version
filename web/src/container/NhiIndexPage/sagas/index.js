import { fork } from 'redux-saga/effects';
import {
  initNhiSalary,
  getNhiSalary,
  getDoctorNhiSalary,
  getValidNhiByYearMonth,
  getNhiOneByDisposalId,
} from './nhiIndex';

export default function* nhiIndexPage() {
  yield fork(initNhiSalary);
  yield fork(getNhiSalary);
  yield fork(getDoctorNhiSalary);
  yield fork(getValidNhiByYearMonth);
  yield fork(getNhiOneByDisposalId);
}
