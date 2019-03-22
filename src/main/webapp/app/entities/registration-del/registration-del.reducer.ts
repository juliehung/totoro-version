import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRegistrationDel, defaultValue } from 'app/shared/model/registration-del.model';

export const ACTION_TYPES = {
  FETCH_REGISTRATIONDEL_LIST: 'registrationDel/FETCH_REGISTRATIONDEL_LIST',
  FETCH_REGISTRATIONDEL: 'registrationDel/FETCH_REGISTRATIONDEL',
  CREATE_REGISTRATIONDEL: 'registrationDel/CREATE_REGISTRATIONDEL',
  UPDATE_REGISTRATIONDEL: 'registrationDel/UPDATE_REGISTRATIONDEL',
  DELETE_REGISTRATIONDEL: 'registrationDel/DELETE_REGISTRATIONDEL',
  RESET: 'registrationDel/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRegistrationDel>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RegistrationDelState = Readonly<typeof initialState>;

// Reducer

export default (state: RegistrationDelState = initialState, action): RegistrationDelState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REGISTRATIONDEL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REGISTRATIONDEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_REGISTRATIONDEL):
    case REQUEST(ACTION_TYPES.UPDATE_REGISTRATIONDEL):
    case REQUEST(ACTION_TYPES.DELETE_REGISTRATIONDEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_REGISTRATIONDEL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REGISTRATIONDEL):
    case FAILURE(ACTION_TYPES.CREATE_REGISTRATIONDEL):
    case FAILURE(ACTION_TYPES.UPDATE_REGISTRATIONDEL):
    case FAILURE(ACTION_TYPES.DELETE_REGISTRATIONDEL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGISTRATIONDEL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGISTRATIONDEL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_REGISTRATIONDEL):
    case SUCCESS(ACTION_TYPES.UPDATE_REGISTRATIONDEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_REGISTRATIONDEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/registration-dels';

// Actions

export const getEntities: ICrudGetAllAction<IRegistrationDel> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_REGISTRATIONDEL_LIST,
    payload: axios.get<IRegistrationDel>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRegistrationDel> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REGISTRATIONDEL,
    payload: axios.get<IRegistrationDel>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRegistrationDel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REGISTRATIONDEL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRegistrationDel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REGISTRATIONDEL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRegistrationDel> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REGISTRATIONDEL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
