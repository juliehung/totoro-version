import produce from 'immer';
import moment from 'moment';
import { GET_OD_INDEXES, GET_OD_INDEXES_SUCCESS, GET_OD_INDEXES_FAIL } from '../constant';

const initState = {
  begin: moment().startOf('month').format('YYYY-MM-DD'),
  end: moment().format('YYYY-MM-DD'),
  odIndexes: [
    // Od object
    // {
    //   did: '',
    //   totalPat: '',
    //   distinctTotalPat: '',
    //   totalTooth: '',
    //   totalSurface: '',
    //   surfaceToothRate: '',
    //   toothPeopleRate: '',
    //   surfacePeopleRate: ''
    // }
  ],
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
export default (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_OD_INDEXES:
        draft.begin = action.begin;
        draft.end = action.end;
        break;
      case GET_OD_INDEXES_SUCCESS:
        draft.odIndexes = action.odIndexes;
        break;
      case GET_OD_INDEXES_FAIL:
        break;
      default:
        break;
    }
  });
