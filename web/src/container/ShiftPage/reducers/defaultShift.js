import produce from 'immer';
import {
  GET_DEFAULT_SHIFT_START,
  GET_DEFAULT_SHIFT_SUCCESS,
  CREATE_DEFAULT_SHIFT_TEMPLATE,
  CHANGE_DEFAULT_SHIFT_NAME,
  CHANGE_DEFAULT_SHIFT_RANGE,
  CREATE_DEFAULT_SHIFT_START,
  CREATE_DEFAULT_SHIFT_SUCCESS,
} from '../constant';
import { parseShiftConfigToShift } from '../utils/parseShiftConfigToShift';

const defaultShiftTemplate = {
  origin: { id: 0, name: '早班', range: { start: '9:00', end: '12:00' } },
  new: {},
  isNew: true,
};

const initialState = {
  shift: [],
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
      case CREATE_DEFAULT_SHIFT_TEMPLATE:
        if (!state.shift.find(s => s.isNew)) {
          draft.shift = [defaultShiftTemplate, ...state.shift];
        }
        break;
      case CHANGE_DEFAULT_SHIFT_NAME:
        draft.shift = state.shift.map(s =>
          s.origin.id === action.id ? { ...s, new: { ...s.new, name: action.name } } : s,
        );
        break;
      case CHANGE_DEFAULT_SHIFT_RANGE:
        draft.shift = state.shift.map(s =>
          s.origin.id === action.id
            ? {
                ...s,
                new: {
                  ...s.new,
                  range: { start: action.range[0].format('HH:mm'), end: action.range[1].format('HH:mm') },
                },
              }
            : s,
        );
        break;
      case CREATE_DEFAULT_SHIFT_START:
        draft.createSuccess = initialState.createSuccess;
        break;
      case CREATE_DEFAULT_SHIFT_SUCCESS:
        draft.createSuccess = true;
        break;
      default:
        break;
    }
  });

export default defaultShift;
