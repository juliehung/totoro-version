import produce from 'immer';
import moment from 'moment';
import {
  CHANGE_CAL_DATE,
  GET_APPOINTMENTS_START,
  GET_APPOINTMENTS_SUCCESS,
  CHANGE_CAL_FIRST_DAY,
  GET_DOCTORS_SUCCESS,
  CHANGE_SELECTED_DOCTORS,
  GET_CALENDAR_EVENT_SUCCESS,
  CHANGE_CAL_SLOT_DURATION,
  POPOVER_CANCEL_APP_SUCCESS,
} from '../constant';

const doctors = JSON.parse(localStorage.getItem('selectedDoctors'));

const initState = {
  calendarDate: moment(),
  appointments: [],
  calendarEvents: [],
  doctors: [],
  selectedDoctors: doctors || ['all'],
  doctorAppCount: {},
  selectedAllDoctors: doctors ? doctors.includes('all') : true,
  showCalEvt: doctors ? doctors.includes('dayOff') : false,
  calendarFirstDay: 0,
  slotDuration: 10,
  range: { start: undefined, end: undefined },
  cancelApp: false,
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
        draft.range = action.range;
        break;
      case GET_APPOINTMENTS_SUCCESS:
        draft.appointments = action.appData.appointment;
        draft.doctorAppCount = action.appData.doctorAppCount;
        break;
      case CHANGE_CAL_FIRST_DAY:
        draft.calendarFirstDay = action.firstDay % 7;
        break;
      case GET_DOCTORS_SUCCESS:
        draft.doctors = action.doctors.sort((a, b) => a.id - b.id);
        break;
      case CHANGE_SELECTED_DOCTORS:
        draft.selectedDoctors = action.selectedDoctors;
        if (draft.selectedDoctors.includes('all')) {
          draft.selectedAllDoctors = true;
        } else {
          draft.selectedAllDoctors = false;
        }
        if (draft.selectedDoctors.includes('dayOff')) {
          draft.showCalEvt = true;
        } else {
          draft.showCalEvt = false;
        }
        localStorage.setItem('selectedDoctors', JSON.stringify(draft.selectedDoctors));
        break;
      case GET_CALENDAR_EVENT_SUCCESS:
        draft.calendarEvents = action.calendarEvents;
        break;
      case CHANGE_CAL_SLOT_DURATION:
        draft.slotDuration = action.duration;
        break;
      case POPOVER_CANCEL_APP_SUCCESS:
        draft.cancelApp = !state.cancelApp;
        break;
      default:
        break;
    }
  });

export default calendar;
