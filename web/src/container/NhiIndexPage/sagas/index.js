import { fork } from 'redux-saga/effects';
import {
  initNhiSalary,
  getNhiSalary,
  getDoctorNhiSalary,
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
  getToothClean,
  getIndexTreatmentProcedure,
  getValidNhiYearMonth,
  getValidNhiByYearMonth,
  getNhiOneByDisposalId,
} from './nhiIndex';

export default function* nhiIndexPage() {
  yield fork(initNhiSalary);
  yield fork(getNhiSalary);
  yield fork(getDoctorNhiSalary);
  yield fork(getOdIndexes);
  yield fork(getDoctorNhiExam);
  yield fork(getDoctorNhiTx);
  yield fork(getToothClean);
  yield fork(getIndexTreatmentProcedure);
  yield fork(getValidNhiYearMonth);
  yield fork(getValidNhiByYearMonth);
  yield fork(getNhiOneByDisposalId);
}
