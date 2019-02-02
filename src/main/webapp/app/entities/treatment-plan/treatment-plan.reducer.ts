import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITreatmentPlan, defaultValue } from 'app/shared/model/treatment-plan.model';

export const ACTION_TYPES = {
  FETCH_TREATMENTPLAN_LIST: 'treatmentPlan/FETCH_TREATMENTPLAN_LIST',
  FETCH_TREATMENTPLAN: 'treatmentPlan/FETCH_TREATMENTPLAN',
  CREATE_TREATMENTPLAN: 'treatmentPlan/CREATE_TREATMENTPLAN',
  UPDATE_TREATMENTPLAN: 'treatmentPlan/UPDATE_TREATMENTPLAN',
  DELETE_TREATMENTPLAN: 'treatmentPlan/DELETE_TREATMENTPLAN',
  RESET: 'treatmentPlan/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITreatmentPlan>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TreatmentPlanState = Readonly<typeof initialState>;

// Reducer

export default (state: TreatmentPlanState = initialState, action): TreatmentPlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTPLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TREATMENTPLAN):
    case REQUEST(ACTION_TYPES.UPDATE_TREATMENTPLAN):
    case REQUEST(ACTION_TYPES.DELETE_TREATMENTPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTPLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTPLAN):
    case FAILURE(ACTION_TYPES.CREATE_TREATMENTPLAN):
    case FAILURE(ACTION_TYPES.UPDATE_TREATMENTPLAN):
    case FAILURE(ACTION_TYPES.DELETE_TREATMENTPLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTPLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTPLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TREATMENTPLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_TREATMENTPLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TREATMENTPLAN):
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

const apiUrl = 'api/treatment-plans';

// Actions

export const getEntities: ICrudGetAllAction<ITreatmentPlan> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TREATMENTPLAN_LIST,
  payload: axios.get<ITreatmentPlan>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITreatmentPlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTPLAN,
    payload: axios.get<ITreatmentPlan>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITreatmentPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TREATMENTPLAN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITreatmentPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TREATMENTPLAN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITreatmentPlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TREATMENTPLAN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
