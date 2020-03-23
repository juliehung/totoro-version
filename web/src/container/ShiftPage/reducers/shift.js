import produce from 'immer';
import { CHANGE_DATE, GET_SHIFT_SUCCESS } from '../constant';
import moment from 'moment';

const initialState = { range: { start: undefined, end: undefined }, shift: [] };

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
