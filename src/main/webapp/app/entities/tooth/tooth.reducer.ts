import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITooth, defaultValue } from 'app/shared/model/tooth.model';

export const ACTION_TYPES = {
  FETCH_TOOTH_LIST: 'tooth/FETCH_TOOTH_LIST',
  FETCH_TOOTH: 'tooth/FETCH_TOOTH',
  CREATE_TOOTH: 'tooth/CREATE_TOOTH',
  UPDATE_TOOTH: 'tooth/UPDATE_TOOTH',
  DELETE_TOOTH: 'tooth/DELETE_TOOTH',
  RESET: 'tooth/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITooth>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ToothState = Readonly<typeof initialState>;

// Reducer

export default (state: ToothState = initialState, action): ToothState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TOOTH_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TOOTH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TOOTH):
    case REQUEST(ACTION_TYPES.UPDATE_TOOTH):
    case REQUEST(ACTION_TYPES.DELETE_TOOTH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TOOTH_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TOOTH):
    case FAILURE(ACTION_TYPES.CREATE_TOOTH):
    case FAILURE(ACTION_TYPES.UPDATE_TOOTH):
    case FAILURE(ACTION_TYPES.DELETE_TOOTH):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOOTH_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOOTH):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TOOTH):
    case SUCCESS(ACTION_TYPES.UPDATE_TOOTH):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TOOTH):
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

const apiUrl = 'api/teeth';

// Actions

export const getEntities: ICrudGetAllAction<ITooth> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TOOTH_LIST,
    payload: axios.get<ITooth>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITooth> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TOOTH,
    payload: axios.get<ITooth>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITooth> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TOOTH,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITooth> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TOOTH,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITooth> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TOOTH,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
