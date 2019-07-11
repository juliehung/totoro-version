import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiMonthDeclarationDetails, defaultValue } from 'app/shared/model/nhi-month-declaration-details.model';

export const ACTION_TYPES = {
  FETCH_NHIMONTHDECLARATIONDETAILS_LIST: 'nhiMonthDeclarationDetails/FETCH_NHIMONTHDECLARATIONDETAILS_LIST',
  FETCH_NHIMONTHDECLARATIONDETAILS: 'nhiMonthDeclarationDetails/FETCH_NHIMONTHDECLARATIONDETAILS',
  CREATE_NHIMONTHDECLARATIONDETAILS: 'nhiMonthDeclarationDetails/CREATE_NHIMONTHDECLARATIONDETAILS',
  UPDATE_NHIMONTHDECLARATIONDETAILS: 'nhiMonthDeclarationDetails/UPDATE_NHIMONTHDECLARATIONDETAILS',
  DELETE_NHIMONTHDECLARATIONDETAILS: 'nhiMonthDeclarationDetails/DELETE_NHIMONTHDECLARATIONDETAILS',
  RESET: 'nhiMonthDeclarationDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiMonthDeclarationDetails>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiMonthDeclarationDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiMonthDeclarationDetailsState = initialState, action): NhiMonthDeclarationDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIMONTHDECLARATIONDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_NHIMONTHDECLARATIONDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_NHIMONTHDECLARATIONDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_NHIMONTHDECLARATIONDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_NHIMONTHDECLARATIONDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_NHIMONTHDECLARATIONDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIMONTHDECLARATIONDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIMONTHDECLARATIONDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIMONTHDECLARATIONDETAILS):
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

const apiUrl = 'api/nhi-month-declaration-details';

// Actions

export const getEntities: ICrudGetAllAction<INhiMonthDeclarationDetails> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS_LIST,
  payload: axios.get<INhiMonthDeclarationDetails>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiMonthDeclarationDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIMONTHDECLARATIONDETAILS,
    payload: axios.get<INhiMonthDeclarationDetails>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiMonthDeclarationDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIMONTHDECLARATIONDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiMonthDeclarationDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIMONTHDECLARATIONDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiMonthDeclarationDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIMONTHDECLARATIONDETAILS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
