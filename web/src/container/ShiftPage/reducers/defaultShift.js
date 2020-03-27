import produce from 'immer';
import {
  GET_DEFAULT_SHIFT_START,
  GET_DEFAULT_SHIFT_SUCCESS,
  CREATE_DEFAULT_SHIFT_TEMPLATE,
  CHANGE_DEFAULT_SHIFT_NAME,
  CHANGE_DEFAULT_SHIFT_RANGE,
} from '../constant';

const defaultShiftTemplate = {
  origin: { id: 0, name: '早班', range: { start: '9:00', end: '12:00' } },
  isNew: true,
  new: {},
};

const initialState = {
  shift: [
    { origin: { id: 1, name: '早班', range: { start: '9:00', end: '12:00' } }, new: {}, isEditing: false },
    { origin: { id: 2, name: '午班', range: { start: '13:00', end: '16:00' } }, new: {}, isEditing: true },
    { origin: { id: 3, name: '晚班', range: { start: '17:00', end: '19:00' } }, new: {}, isEditing: false },
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
        // draft.shift = action.shift;
        break;
      case CREATE_DEFAULT_SHIFT_TEMPLATE:
        draft.shift = [defaultShiftTemplate, ...state.shift];
        break;
      case CHANGE_DEFAULT_SHIFT_NAME:
        draft.shift = state.shift.map(s => {
          if (s.id === action.id) {
            return { ...s, new: { name: action.name } };
          }
          return s;
        });
        break;
      case CHANGE_DEFAULT_SHIFT_RANGE:
        draft.shift = state.shift.map(s => {
          if (s.id === action.id) {
            return {
              ...s,
              new: { range: { start: action.range[0].format('HH:mm'), end: action.range[1].format('HH:mm') } },
            };
          }
          return s;
        });
        break;
      default:
        break;
    }
  });

export default defaultShift;
