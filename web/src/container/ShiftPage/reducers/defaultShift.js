import produce from 'immer';
import { GET_DEFAULT_SHIFT_START, GET_DEFAULT_SHIFT_SUCCESS } from '../constant';

const initialState = {
  shift: [
    { id: 1, name: '早班', range: { start: '9:00', end: '12:00' } },
    { id: 2, name: '午班', range: { start: '13:00', end: '16:00' } },
    { id: 3, name: '晚班', range: { start: '17:00', end: '19:00' } },
  ],
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
        draft.createSuccess = true;
        draft.shift = action.shift;
        break;
      default:
        break;
    }
  });

export default defaultShift;
