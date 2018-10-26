import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRegistration, defaultValue } from 'app/shared/model/registration.model';

export const ACTION_TYPES = {
  FETCH_REGISTRATION_LIST: 'registration/FETCH_REGISTRATION_LIST',
  FETCH_REGISTRATION: 'registration/FETCH_REGISTRATION',
  CREATE_REGISTRATION: 'registration/CREATE_REGISTRATION',
  UPDATE_REGISTRATION: 'registration/UPDATE_REGISTRATION',
  DELETE_REGISTRATION: 'registration/DELETE_REGISTRATION',
  RESET: 'registration/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRegistration>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RegistrationState = Readonly<typeof initialState>;

// Reducer

export default (state: RegistrationState = initialState, action): RegistrationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REGISTRATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REGISTRATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_REGISTRATION):
    case REQUEST(ACTION_TYPES.UPDATE_REGISTRATION):
    case REQUEST(ACTION_TYPES.DELETE_REGISTRATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_REGISTRATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REGISTRATION):
    case FAILURE(ACTION_TYPES.CREATE_REGISTRATION):
    case FAILURE(ACTION_TYPES.UPDATE_REGISTRATION):
    case FAILURE(ACTION_TYPES.DELETE_REGISTRATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGISTRATION_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGISTRATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_REGISTRATION):
    case SUCCESS(ACTION_TYPES.UPDATE_REGISTRATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_REGISTRATION):
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

const apiUrl = 'api/registrations';

// Actions

export const getEntities: ICrudGetAllAction<IRegistration> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_REGISTRATION_LIST,
    payload: axios.get<IRegistration>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRegistration> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REGISTRATION,
    payload: axios.get<IRegistration>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRegistration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REGISTRATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRegistration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REGISTRATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRegistration> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REGISTRATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
