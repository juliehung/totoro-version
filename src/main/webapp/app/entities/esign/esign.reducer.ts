import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEsign, defaultValue } from 'app/shared/model/esign.model';

export const ACTION_TYPES = {
  FETCH_ESIGN_LIST: 'esign/FETCH_ESIGN_LIST',
  FETCH_ESIGN: 'esign/FETCH_ESIGN',
  CREATE_ESIGN: 'esign/CREATE_ESIGN',
  UPDATE_ESIGN: 'esign/UPDATE_ESIGN',
  DELETE_ESIGN: 'esign/DELETE_ESIGN',
  SET_BLOB: 'esign/SET_BLOB',
  RESET: 'esign/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEsign>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EsignState = Readonly<typeof initialState>;

// Reducer

export default (state: EsignState = initialState, action): EsignState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ESIGN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ESIGN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ESIGN):
    case REQUEST(ACTION_TYPES.UPDATE_ESIGN):
    case REQUEST(ACTION_TYPES.DELETE_ESIGN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ESIGN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ESIGN):
    case FAILURE(ACTION_TYPES.CREATE_ESIGN):
    case FAILURE(ACTION_TYPES.UPDATE_ESIGN):
    case FAILURE(ACTION_TYPES.DELETE_ESIGN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESIGN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESIGN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ESIGN):
    case SUCCESS(ACTION_TYPES.UPDATE_ESIGN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ESIGN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/esigns';

// Actions

export const getEntities: ICrudGetAllAction<IEsign> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ESIGN_LIST,
  payload: axios.get<IEsign>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEsign> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ESIGN,
    payload: axios.get<IEsign>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEsign> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ESIGN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEsign> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ESIGN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEsign> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ESIGN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
