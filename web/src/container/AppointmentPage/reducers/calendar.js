import produce from 'immer';
import moment from 'moment';
import { handleEditAppLocally } from '../utils/handleEditAppLocally';
import { handleCountDocApp } from '../utils/handleCountDocApp';
import convertAppsToEvt from '../utils/convertAppsToEvt';

import {
  CHANGE_CAL_DATE,
  GET_APPOINTMENTS_START,
  GET_APPOINTMENTS_SUCCESS,
  CHANGE_CAL_FIRST_DAY,
  CHANGE_SELECTED_DOCTORS,
  GET_CALENDAR_EVENT_SUCCESS,
  CHANGE_CAL_SLOT_DURATION,
  CHANGE_CALENDAR_FULLSCREEN,
  POPOVER_CANCEL_APP_SUCCESS,
  POPOVER_RESTORE_APP_SUCCESS,
  EDIT_APPOINTMENT_SUCCESS,
  CHANGE_CALENADR_RANGE,
} from '../constant';

const doctors = JSON.parse(localStorage.getItem('selectedDoctors')) ?? [];

export const calFirstDay = 0;

const initState = {
  calendarDate: moment(),
  appointments: [],
  calendarEvents: [],
  selectedDoctors: doctors,
  doctorAppCount: {},
  calendarFirstDay: calFirstDay,
  slotDuration: 15,
  calendarFullScreen: false,
  range: { start: undefined, end: undefined },
  cancelApp: false,
  restoreApp: false,
  getSuccess: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const calendar = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_CAL_DATE:
        draft.calendarDate = action.date;
        break;
      case GET_APPOINTMENTS_START:
        draft.getSuccess = initState.getSuccess;
        break;
      case CHANGE_CALENADR_RANGE:
        draft.range = action.range;
        break;
      case GET_APPOINTMENTS_SUCCESS:
        draft.appointments = convertAppsToEvt(action.appData);
        draft.doctorAppCount = handleCountDocApp(draft.appointments);
        draft.getSuccess = true;
        break;
      case CHANGE_CAL_FIRST_DAY:
        draft.calendarFirstDay = action.firstDay % 7;
        break;
      case CHANGE_SELECTED_DOCTORS:
        draft.selectedDoctors = action.selectedDoctors;
        localStorage.setItem('selectedDoctors', JSON.stringify(draft.selectedDoctors));
        break;
      case GET_CALENDAR_EVENT_SUCCESS:
        draft.calendarEvents = action.calendarEvents;
        break;
      case CHANGE_CAL_SLOT_DURATION:
        draft.slotDuration = action.duration;
        break;
      case CHANGE_CALENDAR_FULLSCREEN:
        draft.calendarFullScreen = action.calendarFullScreen;
        break;
      case POPOVER_CANCEL_APP_SUCCESS:
        draft.cancelApp = !state.cancelApp;
        draft.appointments = handleEditAppLocally(state.appointments, action.appointment);
        draft.doctorAppCount = handleCountDocApp(draft.appointments);
        break;
      case POPOVER_RESTORE_APP_SUCCESS:
        draft.restoreApp = !state.restoreApp;
        draft.appointments = handleEditAppLocally(state.appointments, action.appointment);
        draft.doctorAppCount = handleCountDocApp(draft.appointments);
        break;
      case EDIT_APPOINTMENT_SUCCESS:
        draft.appointments = handleEditAppLocally(state.appointments, action.appointment);
        draft.doctorAppCount = handleCountDocApp(draft.appointments);
        break;
      default:
        break;
    }
  });

export default calendar;
