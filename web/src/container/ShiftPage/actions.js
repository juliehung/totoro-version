import { CHANGE_DATE, GET_SHIFT_START, GET_SHIFT_SUCCESS } from './constant';

export const changeDate = range => {
  return { type: CHANGE_DATE, range };
};

export const getShift = range => {
  return { type: GET_SHIFT_START, range };
};

export const getShiftSuccess = shift => {
  return { type: GET_SHIFT_SUCCESS, shift };
};
