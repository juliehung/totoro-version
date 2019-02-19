import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITreatmentDrug, defaultValue } from 'app/shared/model/treatment-drug.model';

export const ACTION_TYPES = {
  FETCH_TREATMENTDRUG_LIST: 'treatmentDrug/FETCH_TREATMENTDRUG_LIST',
  FETCH_TREATMENTDRUG: 'treatmentDrug/FETCH_TREATMENTDRUG',
  CREATE_TREATMENTDRUG: 'treatmentDrug/CREATE_TREATMENTDRUG',
  UPDATE_TREATMENTDRUG: 'treatmentDrug/UPDATE_TREATMENTDRUG',
  DELETE_TREATMENTDRUG: 'treatmentDrug/DELETE_TREATMENTDRUG',
  RESET: 'treatmentDrug/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITreatmentDrug>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TreatmentDrugState = Readonly<typeof initialState>;

// Reducer

export default (state: TreatmentDrugState = initialState, action): TreatmentDrugState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTDRUG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTDRUG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TREATMENTDRUG):
    case REQUEST(ACTION_TYPES.UPDATE_TREATMENTDRUG):
    case REQUEST(ACTION_TYPES.DELETE_TREATMENTDRUG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTDRUG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTDRUG):
    case FAILURE(ACTION_TYPES.CREATE_TREATMENTDRUG):
    case FAILURE(ACTION_TYPES.UPDATE_TREATMENTDRUG):
    case FAILURE(ACTION_TYPES.DELETE_TREATMENTDRUG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTDRUG_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTDRUG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TREATMENTDRUG):
    case SUCCESS(ACTION_TYPES.UPDATE_TREATMENTDRUG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TREATMENTDRUG):
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

const apiUrl = 'api/treatment-drugs';

// Actions

export const getEntities: ICrudGetAllAction<ITreatmentDrug> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTDRUG_LIST,
    payload: axios.get<ITreatmentDrug>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITreatmentDrug> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTDRUG,
    payload: axios.get<ITreatmentDrug>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITreatmentDrug> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TREATMENTDRUG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITreatmentDrug> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TREATMENTDRUG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITreatmentDrug> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TREATMENTDRUG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
