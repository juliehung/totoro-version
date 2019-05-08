import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiIcd10Pcs, defaultValue } from 'app/shared/model/nhi-icd-10-pcs.model';

export const ACTION_TYPES = {
  FETCH_NHIICD10PCS_LIST: 'nhiIcd10Pcs/FETCH_NHIICD10PCS_LIST',
  FETCH_NHIICD10PCS: 'nhiIcd10Pcs/FETCH_NHIICD10PCS',
  CREATE_NHIICD10PCS: 'nhiIcd10Pcs/CREATE_NHIICD10PCS',
  UPDATE_NHIICD10PCS: 'nhiIcd10Pcs/UPDATE_NHIICD10PCS',
  DELETE_NHIICD10PCS: 'nhiIcd10Pcs/DELETE_NHIICD10PCS',
  RESET: 'nhiIcd10Pcs/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiIcd10Pcs>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiIcd10PcsState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiIcd10PcsState = initialState, action): NhiIcd10PcsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIICD10PCS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIICD10PCS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIICD10PCS):
    case REQUEST(ACTION_TYPES.UPDATE_NHIICD10PCS):
    case REQUEST(ACTION_TYPES.DELETE_NHIICD10PCS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIICD10PCS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIICD10PCS):
    case FAILURE(ACTION_TYPES.CREATE_NHIICD10PCS):
    case FAILURE(ACTION_TYPES.UPDATE_NHIICD10PCS):
    case FAILURE(ACTION_TYPES.DELETE_NHIICD10PCS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIICD10PCS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIICD10PCS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIICD10PCS):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIICD10PCS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIICD10PCS):
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

const apiUrl = 'api/nhi-icd-10-pcs';

// Actions

export const getEntities: ICrudGetAllAction<INhiIcd10Pcs> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIICD10PCS_LIST,
  payload: axios.get<INhiIcd10Pcs>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiIcd10Pcs> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIICD10PCS,
    payload: axios.get<INhiIcd10Pcs>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiIcd10Pcs> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIICD10PCS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiIcd10Pcs> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIICD10PCS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiIcd10Pcs> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIICD10PCS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
