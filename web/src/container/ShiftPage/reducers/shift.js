import produce from 'immer';
import { CHANGE_DATE, GET_SHIFT_SUCCESS } from '../constant';
import moment from 'moment';

const initialState = {
  range: { start: undefined, end: undefined },
  shift: [],
  defaultShift: [
    { name: '早班', range: { start: '9:00', end: '12:00' } },
    { name: '午班', range: { start: '13:00', end: '16:00' } },
  ],
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
      default:
        break;
    }
  });

export default shift;
