import produce from 'immer';
import {
  COPY_SHIFT,
  ON_COPY_SHIFT_START,
  CHANGE_DELETE_CURRENT,
  ON_COPY_SHIFT_SUCCESS,
  ON_COPY_SHIFT_FAIL,
  CHANGE_COPY_MODAL_VISIBLE,
  CHANGE_SELECT_ALL_DOCTOR,
} from '../constant';
import { calculatePreviousRange } from '../utils/calculatePreviousRange';
// import moment from 'moment';

const initialState = {
  visible: false,
  range: { start: undefined, end: undefined },
  prevRange: { start: undefined, end: undefined },
  deleteCurrent: false,
  doctor: undefined,
  loading: false,
  success: false,
  selectAllDoctor: false,
  error: false,
};

/* eslint-disable default-case, no-param-reassign */
const copy = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_COPY_MODAL_VISIBLE:
        draft.visible = action.visible;
        if (!action.visible) {
          draft.loading = initialState.loading;
          draft.success = initialState.success;
          draft.visible = initialState.visible;
          draft.selectAllDoctor = initialState.selectAllDoctor;
          draft.doctor = initialState.doctor;
          draft.range = initialState.range;
          draft.prevRange = initialState.prevRange;
          draft.error = initialState.error;
        }
        break;
      case COPY_SHIFT:
        draft.range = action.range;
        draft.prevRange = calculatePreviousRange(action.range);
        draft.doctor = action.doctor;
        break;
      case ON_COPY_SHIFT_START:
        draft.loading = true;
        break;
      case ON_COPY_SHIFT_SUCCESS:
        draft.success = true;
        break;
      case ON_COPY_SHIFT_FAIL:
        draft.error = true;
        break;
      case CHANGE_DELETE_CURRENT:
        if (action.value === 'delete') {
          draft.deleteCurrent = true;
        } else {
          draft.deleteCurrent = false;
        }
        break;
      case CHANGE_SELECT_ALL_DOCTOR:
        draft.selectAllDoctor = action.selectAllDoctor;
        break;
      default:
        break;
    }
  });

export default copy;
