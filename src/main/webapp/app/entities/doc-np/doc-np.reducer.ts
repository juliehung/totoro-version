import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDocNp, defaultValue } from 'app/shared/model/doc-np.model';

export const ACTION_TYPES = {
  FETCH_DOCNP_LIST: 'docNp/FETCH_DOCNP_LIST',
  FETCH_DOCNP: 'docNp/FETCH_DOCNP',
  CREATE_DOCNP: 'docNp/CREATE_DOCNP',
  UPDATE_DOCNP: 'docNp/UPDATE_DOCNP',
  DELETE_DOCNP: 'docNp/DELETE_DOCNP',
  RESET: 'docNp/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDocNp>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DocNpState = Readonly<typeof initialState>;

// Reducer

export default (state: DocNpState = initialState, action): DocNpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DOCNP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DOCNP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DOCNP):
    case REQUEST(ACTION_TYPES.UPDATE_DOCNP):
    case REQUEST(ACTION_TYPES.DELETE_DOCNP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DOCNP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DOCNP):
    case FAILURE(ACTION_TYPES.CREATE_DOCNP):
    case FAILURE(ACTION_TYPES.UPDATE_DOCNP):
    case FAILURE(ACTION_TYPES.DELETE_DOCNP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DOCNP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DOCNP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DOCNP):
    case SUCCESS(ACTION_TYPES.UPDATE_DOCNP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DOCNP):
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

const apiUrl = 'api/doc-nps';

// Actions

export const getEntities: ICrudGetAllAction<IDocNp> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DOCNP_LIST,
  payload: axios.get<IDocNp>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDocNp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DOCNP,
    payload: axios.get<IDocNp>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDocNp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DOCNP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDocNp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DOCNP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDocNp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DOCNP,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
