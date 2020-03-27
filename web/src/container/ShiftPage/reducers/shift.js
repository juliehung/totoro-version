import produce from 'immer';
import {
  CHANGE_DATE,
  GET_SHIFT_SUCCESS,
  CREATE_SHIFT_START,
  CREATE_SHIFT_SUCCESS,
  EDIT_SHIFT_START,
  EDIT_SHIFT_SUCCESS,
} from '../constant';
import moment from 'moment';

const initialState = {
  range: { start: undefined, end: undefined },
  shift: [],
  createShiftSuccess: false,
  editShiftSuccess: false,
};

/* eslint-disable default-case, no-param-reassign */
const shift = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_DATE:
        draft.range.start = moment(action.range.start).startOf('d');
        draft.range.end = moment(action.range.end).endOf('d');
        break;
      case GET_SHIFT_SUCCESS:
        draft.shift = action.shift;
        break;
      case CREATE_SHIFT_START:
        draft.createShiftSuccess = initialState.createShiftSuccess;
        break;
      case CREATE_SHIFT_SUCCESS:
        draft.createShiftSuccess = true;
        draft.shift = [...state.shift, ...action.shifts];
        break;
      case EDIT_SHIFT_START:
        draft.editShiftSuccess = initialState.editShiftSuccess;
        break;
      case EDIT_SHIFT_SUCCESS:
        draft.editShiftSuccess = true;
        break;
      default:
        break;
    }
  });

export default shift;
