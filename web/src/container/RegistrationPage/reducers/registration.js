import { GET_REGISTRATIONS_START, GET_REGISTRATIONS_SUCCESS, SET_SELECTED_DATE_SUCCESS } from '../constant';
import produce from 'immer';
import moment from 'moment';

let sessionSelectedDate = null;
try {
  sessionSelectedDate = JSON.parse(sessionStorage.getItem('selectedDate'));
} catch (e) {}

const initState = {
  registrations: [],
  range: { start: undefined, end: undefined },
  loading: false,
  selectedDate: sessionSelectedDate ? moment(sessionSelectedDate) : moment(),
};

const initialState = { ...initState };

const registration = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_REGISTRATIONS_START:
        draft.range = action.range;
        draft.loading = true;
        break;
      case GET_REGISTRATIONS_SUCCESS:
        console.log(action.appData);
        draft.registrations = action.appData;
        draft.loading = false;
        break;
      case SET_SELECTED_DATE_SUCCESS:
        draft.selectedDate = action.date;
        sessionStorage.setItem('selectedDate', JSON.stringify(action.date));
        break;
      default:
        break;
    }
  });

export default registration;
