import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiMonthDeclaration, defaultValue } from 'app/shared/model/nhi-month-declaration.model';

export const ACTION_TYPES = {
  FETCH_NHIMONTHDECLARATION_LIST: 'nhiMonthDeclaration/FETCH_NHIMONTHDECLARATION_LIST',
  FETCH_NHIMONTHDECLARATION: 'nhiMonthDeclaration/FETCH_NHIMONTHDECLARATION',
  CREATE_NHIMONTHDECLARATION: 'nhiMonthDeclaration/CREATE_NHIMONTHDECLARATION',
  UPDATE_NHIMONTHDECLARATION: 'nhiMonthDeclaration/UPDATE_NHIMONTHDECLARATION',
  DELETE_NHIMONTHDECLARATION: 'nhiMonthDeclaration/DELETE_NHIMONTHDECLARATION',
  RESET: 'nhiMonthDeclaration/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiMonthDeclaration>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiMonthDeclarationState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiMonthDeclarationState = initialState, action): NhiMonthDeclarationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIMONTHDECLARATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIMONTHDECLARATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIMONTHDECLARATION):
    case REQUEST(ACTION_TYPES.UPDATE_NHIMONTHDECLARATION):
    case REQUEST(ACTION_TYPES.DELETE_NHIMONTHDECLARATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIMONTHDECLARATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIMONTHDECLARATION):
    case FAILURE(ACTION_TYPES.CREATE_NHIMONTHDECLARATION):
    case FAILURE(ACTION_TYPES.UPDATE_NHIMONTHDECLARATION):
    case FAILURE(ACTION_TYPES.DELETE_NHIMONTHDECLARATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIMONTHDECLARATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIMONTHDECLARATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIMONTHDECLARATION):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIMONTHDECLARATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIMONTHDECLARATION):
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

const apiUrl = 'api/nhi-month-declarations';

// Actions

export const getEntities: ICrudGetAllAction<INhiMonthDeclaration> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIMONTHDECLARATION_LIST,
  payload: axios.get<INhiMonthDeclaration>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiMonthDeclaration> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIMONTHDECLARATION,
    payload: axios.get<INhiMonthDeclaration>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiMonthDeclaration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIMONTHDECLARATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiMonthDeclaration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIMONTHDECLARATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiMonthDeclaration> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIMONTHDECLARATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
