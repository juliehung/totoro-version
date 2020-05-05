import { GET_USERS_SUCCESS } from '../constant';
import produce from 'immer';

const initState = {
  users: [],
};

const initialState = {
  ...initState,
};

const user = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_USERS_SUCCESS:
        draft.users = action.users;
        break;
      default:
        break;
    }
  });

export default user;
