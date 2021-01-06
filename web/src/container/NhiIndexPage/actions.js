import {
  GET_OD_INDEXES,
  GET_OD_INDEXES_FAIL,
  GET_OD_INDEXES_SUCCESS,
  GET_TOOTH_CLEAN,
  GET_TOOTH_CLEAN_FAIL,
  GET_TOOTH_CLEAN_SUCCESS,
  INIT_NHI_SALARY,
  GET_NHI_SALARY_SUCCESS,
  NHI_SALARY_NOT_FOUND,
  GET_DOCTOR_NHI_SALARY,
  GET_DOCTOR_NHI_SALARY_SUCCESS,
  GET_TOTAL_POINT_BY_DISPOSAL_DATE,
  GET_TOTAL_POINT_BY_DISPOSAL_DATE_SUCCESS,
  GET_VALID_NHI_YEAR_MONTH_SUCCESS,
  GET_VALID_NHI_YEAR_MONTH,
  VALID_NHI_YEAR_MONTH_NOT_FOUND,
  GET_VALID_NHI_BY_YEAR_MONTH,
  GET_VALID_NHI_BY_YEAR_MONTH_SUCCESS,
  VALID_NHI_BY_YEAR_MONTH_NOT_FOUND,
  GET_NHI_ONE_BY_DISPOSAL_ID,
  GET_NHI_ONE_BY_DISPOSAL_ID_SUCCESS,
  NHI_ONE_BY_DISPOSAL_ID_NOT_FOUND,
  GET_NHI_SALARY,
  GET_ENDO_INDEXES_FAIL,
  GET_ENDO_INDEXES_SUCCESS,
  GET_ENDO_INDEXES,
} from './constant';

export function initNhiSalary(begin, end) {
  return { type: INIT_NHI_SALARY, begin, end };
}

export function getNhiSalary(begin, end, checkedModalData) {
  return { type: GET_NHI_SALARY, begin, end, checkedModalData };
}

export function getNhiSalarySuccess(nhiSalary) {
  return { type: GET_NHI_SALARY_SUCCESS, nhiSalary };
}

export function nhiSalaryNotFound() {
  return { type: NHI_SALARY_NOT_FOUND };
}

export function getDoctorNhiSalary(doctorId, begin, end) {
  return { type: GET_DOCTOR_NHI_SALARY, doctorId, begin, end };
}

export function getDoctorNhiSalarySuccess(doctorOneSalary) {
  return { type: GET_DOCTOR_NHI_SALARY_SUCCESS, doctorOneSalary };
}

export function getTotalPointByDisposalDate() {
  return { type: GET_TOTAL_POINT_BY_DISPOSAL_DATE };
}
export function getTotalPointByDisposalDateSuccess(totalPointByDisposalDate) {
  return { type: GET_TOTAL_POINT_BY_DISPOSAL_DATE_SUCCESS, totalPointByDisposalDate };
}

export function getOdIndexes(begin, end) {
  return { type: GET_OD_INDEXES, begin, end };
}

export function getOdIndexesSuccess(odIndexes) {
  return { type: GET_OD_INDEXES_SUCCESS, odIndexes };
}

export function getOdIndexesFail(odIndexes) {
  return { type: GET_OD_INDEXES_FAIL, odIndexes };
}

export function getToothClean(begin, end) {
  return { type: GET_TOOTH_CLEAN, begin, end };
}

export function getToothCleanSuccess(toothClean) {
  return { type: GET_TOOTH_CLEAN_SUCCESS, toothClean };
}

export function getToothCleanFail(toothClean) {
  return { type: GET_TOOTH_CLEAN_FAIL, toothClean };
}

export function getEndoIndexes(begin, end) {
  return { type: GET_ENDO_INDEXES, begin, end };
}

export function getEndoIndexesSuccess(endoIndexes) {
  return { type: GET_ENDO_INDEXES_SUCCESS, endoIndexes };
}

export function getEndoIndexesFail(endoIndexes) {
  return { type: GET_ENDO_INDEXES_FAIL, endoIndexes };
}

export function getValidNhiYearMonth(yearMonth) {
  return { type: GET_VALID_NHI_YEAR_MONTH, yearMonth };
}

export function getValidNhiYearMonthSuccess(validNhiYearMonths) {
  return { type: GET_VALID_NHI_YEAR_MONTH_SUCCESS, validNhiYearMonths };
}

export function validNhiYearMonthNotFound() {
  return { type: VALID_NHI_YEAR_MONTH_NOT_FOUND };
}

export function getValidNhiByYearMonth(yearMonth) {
  return { type: GET_VALID_NHI_BY_YEAR_MONTH, yearMonth };
}
export function getValidNhiByYearMonthSuccess(validNhiData) {
  return { type: GET_VALID_NHI_BY_YEAR_MONTH_SUCCESS, validNhiData };
}
export function validNhiByYearMonthNotFound() {
  return { type: VALID_NHI_BY_YEAR_MONTH_NOT_FOUND };
}

export function getNhiOneByDisposalId(disposalId) {
  return { type: GET_NHI_ONE_BY_DISPOSAL_ID, disposalId };
}
export function getNhiOneByDisposalIdSuccess(nhiOne) {
  return { type: GET_NHI_ONE_BY_DISPOSAL_ID_SUCCESS, nhiOne };
}
export function nhiOneByDisposalIdNotFound() {
  return { type: NHI_ONE_BY_DISPOSAL_ID_NOT_FOUND };
}
