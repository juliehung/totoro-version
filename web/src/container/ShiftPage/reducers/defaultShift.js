import produce from 'immer';
import {
  GET_DEFAULT_SHIFT_START,
  GET_DEFAULT_SHIFT_SUCCESS,
  CHANGE_DEFAULT_SHIFT_NAME,
  CHANGE_DEFAULT_SHIFT_RANGE,
  CREATE_DEFAULT_SHIFT_START,
  CREATE_DEFAULT_SHIFT_SUCCESS,
  DELETE_DEFAULT_SHIFT_SUCCESS,
} from '../constant';
import { parseShiftConfigToShift } from '../utils/parseShiftConfigToShift';

const initialState = {
  shift: [],
  newShift: { name: undefined, range: { start: undefined, end: undefined } },
  createSuccess: false,
};

/* eslint-disable default-case, no-param-reassign */
const defaultShift = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_DEFAULT_SHIFT_START:
        draft.createSuccess = initialState.createSuccess;
        break;
      case GET_DEFAULT_SHIFT_SUCCESS:
        draft.shift = parseShiftConfigToShift(action.shift);
        break;
      case CHANGE_DEFAULT_SHIFT_NAME:
        draft.newShift.name = action.name;
        break;
      case CHANGE_DEFAULT_SHIFT_RANGE:
        draft.newShift.range = { start: action.range[0].format('HH:mm'), end: action.range[1].format('HH:mm') };
        break;
      case CREATE_DEFAULT_SHIFT_START:
        draft.createSuccess = initialState.createSuccess;
        break;
      case CREATE_DEFAULT_SHIFT_SUCCESS:
        draft.newShift = initialState.newShift;
        draft.createSuccess = true;
        break;
      case DELETE_DEFAULT_SHIFT_SUCCESS:
        draft.shift = state.shift.filter(s => s.origin.id !== action.id);
        break;
      default:
        break;
    }
  });

export default defaultShift;
