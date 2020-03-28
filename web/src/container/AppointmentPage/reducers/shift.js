import produce from 'immer';
import { GET_SHIFT_SUCCESS } from '../constant';

const initialState = {
  shift: [],
};

/* eslint-disable default-case, no-param-reassign */
const shift = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_SHIFT_SUCCESS:
        draft.shift = action.shift;
        break;
      default:
        break;
    }
  });

export default shift;
