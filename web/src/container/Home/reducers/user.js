import produce from 'immer';
import { GET_USER_SUCCESS } from '../constant';

const initialState = { users: [] };

/* eslint-disable default-case, no-param-reassign */
const user = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_USER_SUCCESS:
        draft.users = action.user;
        break;
    }
  });

export default user;
