import produce from 'immer';
import { GET_SHIFT_START, GET_SHIFT_SUCCESS } from '../constant';

const initialState = {
  shift: [],
  getShiftSuccess: false,
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
      default:
        break;
    }
  });

export default shift;
