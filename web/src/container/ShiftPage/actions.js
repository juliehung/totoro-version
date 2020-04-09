import {
  CHANGE_DATE,
  GET_SHIFT_START,
  GET_SHIFT_SUCCESS,
  CREATE_SHIFT_START,
  CREATE_SHIFT_SUCCESS,
  DELETE_SHIFT_START,
  DELETE_SHIFT_SUCCESS,
  GET_DEFAULT_SHIFT_START,
  GET_DEFAULT_SHIFT_SUCCESS,
  EDIT_SHIFT_START,
  EDIT_SHIFT_SUCCESS,
  CREATE_DEFAULT_SHIFT_START,
  CREATE_DEFAULT_SHIFT_SUCCESS,
  CHANGE_DEFAULT_SHIFT_NAME,
  CHANGE_DEFAULT_SHIFT_RANGE,
  SHIFT_DROP_START,
  SHIFT_DROP_SUCCESS,
  DELETE_DEFAULT_SHIFT_START,
  DELETE_DEFAULT_SHIFT_SUCCESS,
  GET_RESOURCE_COLOR_START,
  GET_RESOURCE_COLOR_SUCCESS,
  CHANGE_RESOURCE_COLOR_START,
  CHANGE_RESOURCE_COLOR_SUCCESS,
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

export const deleteShift = id => {
  return { type: DELETE_SHIFT_START, id };
};

export const deleteShiftSuccess = id => {
  return { type: DELETE_SHIFT_SUCCESS, id };
};

export const getDefaultShift = () => {
  return { type: GET_DEFAULT_SHIFT_START };
};

export const getDefaultShiftSuccess = shift => {
  return { type: GET_DEFAULT_SHIFT_SUCCESS, shift };
};

export const editShift = shift => {
  return { type: EDIT_SHIFT_START, shift };
};

export const editShiftSuccess = shift => {
  return { type: EDIT_SHIFT_SUCCESS, shift };
};

export const createDefaultShift = () => {
  return { type: CREATE_DEFAULT_SHIFT_START };
};

export const createDefaultShiftSuccess = () => {
  return { type: CREATE_DEFAULT_SHIFT_SUCCESS };
};

export const deleteDefaultShift = id => {
  return { type: DELETE_DEFAULT_SHIFT_START, id };
};

export const deleteDefaultShiftSuccess = id => {
  return { type: DELETE_DEFAULT_SHIFT_SUCCESS, id };
};

export const changeDefaultShiftName = name => {
  return { type: CHANGE_DEFAULT_SHIFT_NAME, name };
};

export const changeDefaultShiftRange = range => {
  return { type: CHANGE_DEFAULT_SHIFT_RANGE, range };
};

export const shiftDrop = shift => {
  return { type: SHIFT_DROP_START, shift };
};

export const shiftDropSuccess = shifts => {
  return { type: SHIFT_DROP_SUCCESS, shifts };
};

export const getResourceColor = () => {
  return { type: GET_RESOURCE_COLOR_START };
};

export const getResourceColorSuccess = color => {
  return { type: GET_RESOURCE_COLOR_SUCCESS, color };
};

export const changeResourceColor = (id, color) => {
  return { type: CHANGE_RESOURCE_COLOR_START, id, color };
};

export const changeResourceColorSuccess = (id, color) => {
  return { type: CHANGE_RESOURCE_COLOR_SUCCESS, id, color };
};
