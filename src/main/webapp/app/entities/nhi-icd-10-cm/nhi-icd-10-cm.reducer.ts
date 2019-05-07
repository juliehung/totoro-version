import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiIcd10Cm, defaultValue } from 'app/shared/model/nhi-icd-10-cm.model';

export const ACTION_TYPES = {
  FETCH_NHIICD10CM_LIST: 'nhiIcd10Cm/FETCH_NHIICD10CM_LIST',
  FETCH_NHIICD10CM: 'nhiIcd10Cm/FETCH_NHIICD10CM',
  CREATE_NHIICD10CM: 'nhiIcd10Cm/CREATE_NHIICD10CM',
  UPDATE_NHIICD10CM: 'nhiIcd10Cm/UPDATE_NHIICD10CM',
  DELETE_NHIICD10CM: 'nhiIcd10Cm/DELETE_NHIICD10CM',
  RESET: 'nhiIcd10Cm/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiIcd10Cm>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiIcd10CmState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiIcd10CmState = initialState, action): NhiIcd10CmState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIICD10CM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIICD10CM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIICD10CM):
    case REQUEST(ACTION_TYPES.UPDATE_NHIICD10CM):
    case REQUEST(ACTION_TYPES.DELETE_NHIICD10CM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIICD10CM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIICD10CM):
    case FAILURE(ACTION_TYPES.CREATE_NHIICD10CM):
    case FAILURE(ACTION_TYPES.UPDATE_NHIICD10CM):
    case FAILURE(ACTION_TYPES.DELETE_NHIICD10CM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIICD10CM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIICD10CM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIICD10CM):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIICD10CM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIICD10CM):
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

const apiUrl = 'api/nhi-icd-10-cms';

// Actions

export const getEntities: ICrudGetAllAction<INhiIcd10Cm> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIICD10CM_LIST,
  payload: axios.get<INhiIcd10Cm>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiIcd10Cm> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIICD10CM,
    payload: axios.get<INhiIcd10Cm>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiIcd10Cm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIICD10CM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiIcd10Cm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIICD10CM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiIcd10Cm> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIICD10CM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
