import { GET_OD_INDEXES, GET_OD_INDEXES_FAIL, GET_OD_INDEXES_SUCCESS } from './constant';

export function getOdIndexes(begin, end) {
  return { type: GET_OD_INDEXES, begin, end };
}

export function getOdIndexesSuccess(odIndexes) {
  return { type: GET_OD_INDEXES_SUCCESS, odIndexes };
}

export function getOdIndexesFail(odIndexes) {
  return { type: GET_OD_INDEXES_FAIL, odIndexes };
}
