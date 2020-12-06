import produce from 'immer';
import {
  CHANGE_PAGE_SIZE_TO_GET_PATIENT_IMAGES,
  CHANGE_PAGE_SIZE_TO_GET_PATIENT_IMAGES_SUCCESS,
  GET_PATIENT_IMAGES,
  GET_PATIENT_IMAGES_SUCCESS,
  UPDATE_PATIENT_IMAGES_INDEX,
} from '../constant';

const initState = {
  loading: false,
  patientImages: undefined,
  page: 0,
  size: 20,
  currentIndex: 0,
  isFetchEmpty: false,
};

/* eslint-disable default-case, no-param-reassign */
const patientImages = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_PATIENT_IMAGES:
        draft.loading = true;
        break;
      case GET_PATIENT_IMAGES_SUCCESS:
        draft.loading = false;
        draft.patientImages = action.patientImages;
        break;
      case UPDATE_PATIENT_IMAGES_INDEX:
        draft.currentIndex = action.currentIndex;
        break;
      case CHANGE_PAGE_SIZE_TO_GET_PATIENT_IMAGES:
        draft.page = action.page;
        draft.size = action.size;
        draft.currentIndex = action.currentIndex;
        break;
      case CHANGE_PAGE_SIZE_TO_GET_PATIENT_IMAGES_SUCCESS:
        if (action.patientImages.length === 0) {
          draft.page = state.page - 1;
          draft.currentIndex = state.patientImages.length - 1;
          draft.isFetchEmpty = true;
        } else {
          draft.currentIndex = state.patientImages.length;
          draft.patientImages = [].concat(state.patientImages, action.patientImages);
        }
        break;
      default:
        break;
    }
  });

export default patientImages;
