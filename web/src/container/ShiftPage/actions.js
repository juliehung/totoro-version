import {
  CHANGE_DATE,
  GET_SHIFT_START,
  GET_SHIFT_SUCCESS,
  CREATE_SHIFT_START,
  CREATE_SHIFT_SUCCESS,
  GET_DEFAULT_SHIFT_START,
  GET_DEFAULT_SHIFT_SUCCESS,
  EDIT_SHIFT_START,
  EDIT_SHIFT_SUCCESS,
} from './constant';

export const changeDate = range => {
  return { type: CHANGE_DATE, range };
};

export const getShift = range => {
  return { type: GET_SHIFT_START, range };
};

export const getShiftSuccess = shift => {
  return { type: GET_SHIFT_SUCCESS, shift };
};

export const createShift = data => {
  return { type: CREATE_SHIFT_START, data };
};

export const createShiftSuccess = shifts => {
  return { type: CREATE_SHIFT_SUCCESS, shifts };
};

export const getDefaultShift = () => {
  return { type: GET_DEFAULT_SHIFT_START };
};

export const getDefaultShiftSuccess = shift => {
  return { type: GET_DEFAULT_SHIFT_SUCCESS, shift };
}

export const editShift = shift => {
  return { type: EDIT_SHIFT_START, shift };
};

export const editShiftSuccess = () => {
  return { type: EDIT_SHIFT_SUCCESS };
};
