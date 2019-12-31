import produce from 'immer';
import moment from 'moment';
import { parseDayOffCronToRepeat } from '../utils/parseDayOffCronToRepeat';
import {
  CHANGE_EDIT_CAL_MODAL_VISIBLE,
  INSERT_CAL_EVT_TO_EDIT_CAL_EVT_MODEL,
  CHANGE_EDIT_CAL_EVT_START_DATE,
  CAHNGE_EDIT_CAL_EVT_START_TIME,
  CAHNGE_EDIT_CAL_EVT_END_DATE,
  CAHNGE_EDIT_CAL_EVT_END_TIME,
  CHANGE_EDIT_CAL_EVT_DOCTOR,
  CHANGE_EDIT_CAL_EVT_REPEAT,
  CHANGE_EDIT_CAL_EVT_REPEAT_END_DATE,
  CHANGE_EDIT_CAL_EVT_NOTE,
  CHANGE_EDIT_CAL_EVT_CONFIRM_DELETE,
  CHECK_EDIT_CAL_CONFIRM_BUTTON_DISABLE,
  EDIT_CAL_EVT_START,
  EDIT_CAL_EVT_SUCCESS,
  DELETE_CAL_EVT_START,
  DELETE_CAL_EVT_SUCCESS,
} from '../constant';

const initialState = {
  loading: false,
  visible: false,
  disabled: true,
  deleteLoading: false,
  deleteSuccess: false,
  editCalEvtSuccess: false,
  confirmDelete: false,
  event: {
    id: undefined,
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

/* eslint-disable default-case, no-param-reassign */
const createCalendarEvt = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_EDIT_CAL_MODAL_VISIBLE:
        draft.visible = action.visible;
        if (!action.visible) {
          draft.event = initialState.event;
          draft.loading = initialState.loading;
          draft.disabled = initialState.disabled;
          draft.editCalEvtSuccess = initialState.editCalEvtSuccess;
          draft.deleteLoading = initialState.deleteLoading;
          draft.deleteSuccess = initialState.deleteSuccess;
          draft.confirmDelete = initialState.confirmDelete;
        }
        break;
      case CHANGE_EDIT_CAL_EVT_START_DATE:
        draft.event.startDate = action.date;
        break;
      case CAHNGE_EDIT_CAL_EVT_START_TIME:
        draft.event.startTime = action.time;
        break;
      case CAHNGE_EDIT_CAL_EVT_END_DATE:
        draft.event.endDate = action.date;
        break;
      case CAHNGE_EDIT_CAL_EVT_END_TIME:
        draft.event.endTime = action.time;
        break;
      case CHANGE_EDIT_CAL_EVT_DOCTOR:
        draft.event.doctorId = action.doctorId;
        break;
      case CHANGE_EDIT_CAL_EVT_REPEAT:
        draft.event.repeat = action.repeat;
        break;
      case CHANGE_EDIT_CAL_EVT_REPEAT_END_DATE:
        draft.event.repeatEndDate = action.date;
        break;
      case CHANGE_EDIT_CAL_EVT_NOTE:
        draft.event.note = action.e.target.value;
        break;
      case CHECK_EDIT_CAL_CONFIRM_BUTTON_DISABLE:
        if (state.event.startDate && state.event.startTime && state.event.endDate && state.event.endTime) {
          draft.disabled = false;
          return;
        }
        draft.disabled = true;
        break;
      case INSERT_CAL_EVT_TO_EDIT_CAL_EVT_MODEL:
        if (action.calEvt.dayOffCron && action.calEvt.dayOffCron.length > 0) {
          const end = moment(action.calEvt.start).add(action.calEvt.duration, 'm');
          draft.event.endDate = end;
          draft.event.endTime = end;
          draft.event.repeatEndDate = moment(action.calEvt.end);
          draft.event.repeat = parseDayOffCronToRepeat(action.calEvt.dayOffCron);
        } else {
          draft.event.endDate = moment(action.calEvt.end);
          draft.event.endTime = moment(action.calEvt.end);
        }

        draft.event.startDate = moment(action.calEvt.start);
        draft.event.startTime = moment(action.calEvt.start);

        if (action.calEvt.doctor) {
          draft.event.doctorId = action.calEvt.doctor.id;
        } else {
          draft.event.doctorId = 'none';
        }

        draft.event.note = action.calEvt.note;
        draft.event.id = action.calEvt.id;
        break;
      case EDIT_CAL_EVT_START:
        draft.loading = true;
        break;
      case EDIT_CAL_EVT_SUCCESS:
        draft.editCalEvtSuccess = true;
        break;
      case DELETE_CAL_EVT_START:
        draft.deleteLoading = true;
        break;
      case DELETE_CAL_EVT_SUCCESS:
        draft.deleteSuccess = true;
        break;
      case CHANGE_EDIT_CAL_EVT_CONFIRM_DELETE:
        draft.confirmDelete = action.confirm;
        break;
      default:
        break;
    }
  });

export default createCalendarEvt;
