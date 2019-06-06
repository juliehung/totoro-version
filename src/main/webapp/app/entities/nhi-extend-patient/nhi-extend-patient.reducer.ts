import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiExtendPatient, defaultValue } from 'app/shared/model/nhi-extend-patient.model';

export const ACTION_TYPES = {
  FETCH_NHIEXTENDPATIENT_LIST: 'nhiExtendPatient/FETCH_NHIEXTENDPATIENT_LIST',
  FETCH_NHIEXTENDPATIENT: 'nhiExtendPatient/FETCH_NHIEXTENDPATIENT',
  CREATE_NHIEXTENDPATIENT: 'nhiExtendPatient/CREATE_NHIEXTENDPATIENT',
  UPDATE_NHIEXTENDPATIENT: 'nhiExtendPatient/UPDATE_NHIEXTENDPATIENT',
  DELETE_NHIEXTENDPATIENT: 'nhiExtendPatient/DELETE_NHIEXTENDPATIENT',
  RESET: 'nhiExtendPatient/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiExtendPatient>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiExtendPatientState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiExtendPatientState = initialState, action): NhiExtendPatientState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIEXTENDPATIENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIEXTENDPATIENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIEXTENDPATIENT):
    case REQUEST(ACTION_TYPES.UPDATE_NHIEXTENDPATIENT):
    case REQUEST(ACTION_TYPES.DELETE_NHIEXTENDPATIENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIEXTENDPATIENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIEXTENDPATIENT):
    case FAILURE(ACTION_TYPES.CREATE_NHIEXTENDPATIENT):
    case FAILURE(ACTION_TYPES.UPDATE_NHIEXTENDPATIENT):
    case FAILURE(ACTION_TYPES.DELETE_NHIEXTENDPATIENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIEXTENDPATIENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIEXTENDPATIENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIEXTENDPATIENT):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIEXTENDPATIENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIEXTENDPATIENT):
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

const apiUrl = 'api/nhi-extend-patients';

// Actions

export const getEntities: ICrudGetAllAction<INhiExtendPatient> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIEXTENDPATIENT_LIST,
  payload: axios.get<INhiExtendPatient>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiExtendPatient> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIEXTENDPATIENT,
    payload: axios.get<INhiExtendPatient>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiExtendPatient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIEXTENDPATIENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiExtendPatient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIEXTENDPATIENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiExtendPatient> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIEXTENDPATIENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
