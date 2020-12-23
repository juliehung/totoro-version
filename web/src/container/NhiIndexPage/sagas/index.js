import { fork } from 'redux-saga/effects';
import {
  getNhiSalary,
  getDoctorNhiSalary,
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
  getToothClean,
  getIndexTreatmentProcedure,
} from './nhiIndex';

export default function* nhiIndexPage() {
  yield fork(getNhiSalary);
  yield fork(getDoctorNhiSalary);
  yield fork(getOdIndexes);
  yield fork(getDoctorNhiExam);
  yield fork(getDoctorNhiTx);
  yield fork(getToothClean);
  yield fork(getIndexTreatmentProcedure);
}
