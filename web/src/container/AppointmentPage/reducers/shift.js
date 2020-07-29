import produce from 'immer';
import { GET_SHIFT_START, GET_SHIFT_SUCCESS, CHANGE_SHIFT_OPEN } from '../constant';

const initialState = {
  shift: [],
  getShiftSuccess: false,
  shiftOpen: true,
};

/* eslint-disable default-case, no-param-reassign */
const shift = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_SHIFT_START:
        draft.getShiftSuccess = initialState.getShiftSuccess;
        break;
      case GET_SHIFT_SUCCESS:
        draft.shift = action.shift;
        draft.getShiftSuccess = true;
        break;
      case CHANGE_SHIFT_OPEN:
        draft.shiftOpen = action.shiftOpen;
        break;
      default:
        break;
    }
  });

export default shift;
