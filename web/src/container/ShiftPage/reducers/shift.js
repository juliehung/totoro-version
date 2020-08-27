import produce from 'immer';
import {
  CHANGE_DATE,
  GET_SHIFT_SUCCESS,
  CREATE_SHIFT_START,
  CREATE_SHIFT_SUCCESS,
  DELETE_SHIFT_START,
  DELETE_SHIFT_SUCCESS,
  EDIT_SHIFT_START,
  EDIT_SHIFT_SUCCESS,
  SHIFT_DROP_START,
  SHIFT_DROP_SUCCESS,
  LEAVE_PAGE,
} from '../constant';
import moment from 'moment';

const initialState = {
  range: { start: undefined, end: undefined },
  shift: [],
  createShiftSuccess: false,
  editShiftSuccess: false,
  deleteSuccess: false,
};

/* eslint-disable default-case, no-param-reassign */
const shift = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_DATE:
        draft.range.start = moment(action.range.start);
        draft.range.end = moment(action.range.end);
        break;
      case GET_SHIFT_SUCCESS:
        draft.shift = action.shift;
        break;
      case CREATE_SHIFT_START:
      case SHIFT_DROP_START:
        draft.createShiftSuccess = initialState.createShiftSuccess;
        break;
      case DELETE_SHIFT_START:
        draft.deleteSuccess = initialState.deleteSuccess;
        break;
      case DELETE_SHIFT_SUCCESS:
        const { event } = action;
        draft.shift = state.shift.filter(
          s => s.userId !== event.userId || s.fromDate !== event.fromDate || s.toDate !== event.toDate,
        );
        draft.deleteSuccess = true;
        break;
      case CREATE_SHIFT_SUCCESS:
      case SHIFT_DROP_SUCCESS:
        draft.createShiftSuccess = true;
        draft.shift = [...state.shift, ...action.shifts];
        break;
      case EDIT_SHIFT_START:
        draft.editShiftSuccess = initialState.editShiftSuccess;
        break;
      case EDIT_SHIFT_SUCCESS:
        draft.shift = [...state.shift.filter(s => s.id !== action.shift.id), action.shift];
        draft.editShiftSuccess = true;
        break;
      case LEAVE_PAGE:
        draft.createShiftSuccess = initialState.createShiftSuccess;
        draft.editShiftSuccess = initialState.editShiftSuccess;
        draft.deleteSuccess = initialState.deleteSuccess;
        break;
      default:
        break;
    }
  });

export default shift;
