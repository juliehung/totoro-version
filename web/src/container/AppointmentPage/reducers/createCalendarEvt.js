import produce from 'immer';
import {
  CHANGE_CREATE_CAL_EVT_START_DATE,
  CAHNGE_CREATE_CAL_EVT_START_TIME,
  CAHNGE_CREATE_CAL_EVT_END_DATE,
  CAHNGE_CREATE_CAL_EVT_END_TIME,
  CHANGE_CREATE_CAL_EVT_DOCTOR,
  CHANGE_CREATE_CAL_EVT_REPEAT,
  CHANGE_CREATE_CAL_EVT_REAPEAT_END_DATE,
  CHANGE_CREATE_CAL_EVT_NOTE,
  CHECK_CREATE_CAL_CONFIRM_BUTTON_DISABLE,
  CHANGE_CREATE_CAL_MODAL_VISIBLE,
  CREATE_CAL_EVT_START,
  CREATE_CAL_EVT_SUCCESS,
} from '../constant';

const initState = {
  loading: false,
  visible: false,
  disabled: true,
  createCalEvtSuccess: false,
  event: {
    startDate: undefined,
    startTime: undefined,
    endDate: undefined,
    endTime: undefined,
    repeatEndDate: undefined,
    doctorId: 'none',
    repeat: 'none',
    note: undefined,
  },
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const createCalendarEvt = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_CREATE_CAL_MODAL_VISIBLE:
        draft.visible = action.visible;
        if (!action.visible) {
          draft.event = initState.event;
          draft.loading = initState.loading;
          draft.disabled = initState.disabled;
          draft.createCalEvtSuccess = initState.createCalEvtSuccess;
        }
        break;
      case CHANGE_CREATE_CAL_EVT_START_DATE:
        draft.event.startDate = action.date;
        break;
      case CAHNGE_CREATE_CAL_EVT_START_TIME:
        draft.event.startTime = action.time;
        break;
      case CAHNGE_CREATE_CAL_EVT_END_DATE:
        draft.event.endDate = action.date;
        break;
      case CAHNGE_CREATE_CAL_EVT_END_TIME:
        draft.event.endTime = action.time;
        break;
      case CHANGE_CREATE_CAL_EVT_DOCTOR:
        draft.event.doctorId = action.doctorId;
        break;
      case CHANGE_CREATE_CAL_EVT_REPEAT:
        draft.event.repeat = action.repeat;
        break;
      case CHANGE_CREATE_CAL_EVT_REAPEAT_END_DATE:
        draft.event.repeatEndDate = action.date;
        break;
      case CHANGE_CREATE_CAL_EVT_NOTE:
        draft.event.note = action.e.target.value;
        break;
      case CHECK_CREATE_CAL_CONFIRM_BUTTON_DISABLE:
        if (state.event.startDate && state.event.startTime && state.event.endDate && state.event.endTime) {
          if (state.event.repeat !== 'none') {
            if (state.event.repeatEndDate) {
              draft.disabled = false;
            } else {
              draft.disabled = true;
            }
          } else {
            draft.disabled = false;
          }
        } else {
          draft.disabled = true;
        }
        break;
      case CREATE_CAL_EVT_START:
        draft.loading = true;
        break;
      case CREATE_CAL_EVT_SUCCESS:
        draft.createCalEvtSuccess = true;
        break;
      default:
        break;
    }
  });

export default createCalendarEvt;