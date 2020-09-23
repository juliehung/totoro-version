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
  LEAVE_PAGE,
  COPY_SHIFT,
  ON_COPY_SHIFT_START,
  ON_COPY_SHIFT_SUCCESS,
  CHANGE_DELETE_CURRENT,
  CHANGE_COPY_MODAL_VISIBLE,
  CHANGE_SELECT_ALL_DOCTOR,
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

export const deleteShift = event => {
  return { type: DELETE_SHIFT_START, event };
};

export const deleteShiftSuccess = event => {
  return { type: DELETE_SHIFT_SUCCESS, event };
};

export const getDefaultShift = () => {
  return { type: GET_DEFAULT_SHIFT_START };
};

export const getDefaultShiftSuccess = shift => {
  return { type: GET_DEFAULT_SHIFT_SUCCESS, shift };
};

export const editShift = ({ oldShift, newShift }) => {
  return { type: EDIT_SHIFT_START, oldShift, newShift };
};

export const editShiftSuccess = result => {
  return { type: EDIT_SHIFT_SUCCESS, result };
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

export const leavePage = () => {
  return { type: LEAVE_PAGE };
};

export const copyShift = (doctor, range) => {
  return { type: COPY_SHIFT, doctor, range };
};

export const onCopyShift = () => {
  return { type: ON_COPY_SHIFT_START };
};

export const onCopyShiftSuccess = () => {
  return { type: ON_COPY_SHIFT_SUCCESS };
};

export const changeDeleteCurrent = value => {
  return { type: CHANGE_DELETE_CURRENT, value };
};

export const changeCopyModalVisible = visible => {
  return { type: CHANGE_COPY_MODAL_VISIBLE, visible };
};

export const changeSelectAllDoctor = selectAllDoctor => {
  return { type: CHANGE_SELECT_ALL_DOCTOR, selectAllDoctor };
};
