import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiIcd9Cm, defaultValue } from 'app/shared/model/nhi-icd-9-cm.model';

export const ACTION_TYPES = {
  FETCH_NHIICD9CM_LIST: 'nhiIcd9Cm/FETCH_NHIICD9CM_LIST',
  FETCH_NHIICD9CM: 'nhiIcd9Cm/FETCH_NHIICD9CM',
  CREATE_NHIICD9CM: 'nhiIcd9Cm/CREATE_NHIICD9CM',
  UPDATE_NHIICD9CM: 'nhiIcd9Cm/UPDATE_NHIICD9CM',
  DELETE_NHIICD9CM: 'nhiIcd9Cm/DELETE_NHIICD9CM',
  RESET: 'nhiIcd9Cm/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiIcd9Cm>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiIcd9CmState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiIcd9CmState = initialState, action): NhiIcd9CmState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIICD9CM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIICD9CM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIICD9CM):
    case REQUEST(ACTION_TYPES.UPDATE_NHIICD9CM):
    case REQUEST(ACTION_TYPES.DELETE_NHIICD9CM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIICD9CM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIICD9CM):
    case FAILURE(ACTION_TYPES.CREATE_NHIICD9CM):
    case FAILURE(ACTION_TYPES.UPDATE_NHIICD9CM):
    case FAILURE(ACTION_TYPES.DELETE_NHIICD9CM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIICD9CM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIICD9CM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIICD9CM):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIICD9CM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIICD9CM):
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

const apiUrl = 'api/nhi-icd-9-cms';

// Actions

export const getEntities: ICrudGetAllAction<INhiIcd9Cm> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIICD9CM_LIST,
  payload: axios.get<INhiIcd9Cm>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiIcd9Cm> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIICD9CM,
    payload: axios.get<INhiIcd9Cm>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiIcd9Cm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIICD9CM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiIcd9Cm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIICD9CM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiIcd9Cm> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIICD9CM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
