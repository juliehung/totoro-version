import produce from 'immer';
import { GET_USER_START, GET_USER_SUCCESS } from '../constant';

const initialState = { users: [], getSuccess: false };

/* eslint-disable default-case, no-param-reassign */
const user = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_USER_START:
        draft.getSuccess = initialState.getSuccess;
        break;
      case GET_USER_SUCCESS:
        draft.users = action.user;
        draft.getSuccess = true;
        break;
    }
  });

export default user;
