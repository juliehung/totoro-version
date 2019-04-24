import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INHIProcedureType, defaultValue } from 'app/shared/model/nhi-procedure-type.model';

export const ACTION_TYPES = {
  FETCH_NHIPROCEDURETYPE_LIST: 'nHIProcedureType/FETCH_NHIPROCEDURETYPE_LIST',
  FETCH_NHIPROCEDURETYPE: 'nHIProcedureType/FETCH_NHIPROCEDURETYPE',
  CREATE_NHIPROCEDURETYPE: 'nHIProcedureType/CREATE_NHIPROCEDURETYPE',
  UPDATE_NHIPROCEDURETYPE: 'nHIProcedureType/UPDATE_NHIPROCEDURETYPE',
  DELETE_NHIPROCEDURETYPE: 'nHIProcedureType/DELETE_NHIPROCEDURETYPE',
  RESET: 'nHIProcedureType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INHIProcedureType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NHIProcedureTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: NHIProcedureTypeState = initialState, action): NHIProcedureTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIPROCEDURETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIPROCEDURETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_NHIPROCEDURETYPE):
    case REQUEST(ACTION_TYPES.DELETE_NHIPROCEDURETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIPROCEDURETYPE):
    case FAILURE(ACTION_TYPES.CREATE_NHIPROCEDURETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_NHIPROCEDURETYPE):
    case FAILURE(ACTION_TYPES.DELETE_NHIPROCEDURETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIPROCEDURETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIPROCEDURETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIPROCEDURETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIPROCEDURETYPE):
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

const apiUrl = 'api/nhi-procedure-types';

// Actions

export const getEntities: ICrudGetAllAction<INHIProcedureType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST,
  payload: axios.get<INHIProcedureType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INHIProcedureType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIPROCEDURETYPE,
    payload: axios.get<INHIProcedureType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INHIProcedureType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIPROCEDURETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INHIProcedureType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIPROCEDURETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INHIProcedureType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIPROCEDURETYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
