import produce from 'immer';
import { toggleArrayItem } from '../utils/toggleArrayItem';
import {
  CHANGE_GENDER,
  CHANGE_BLOOD_TYPE,
  CHANGE_CAREER,
  CHANGE_MARRIAGE,
  CHANGE_EMERGENCY_RELATIONSHIP,
  CHANGE_DISEASE,
  CHANGE_ALLERGY,
  CHANGE_DO_DRUG,
  CHANGE_PREGANT,
  CHANGE_SMOKING,
  CHANGE_OTHER,
  CHANGE_NAME,
  CHANGE_BIRTH,
  CHANGE_NATIONAL_ID,
  CHANGE_PHONE,
  CHANGE_ADDRESS,
  CHANGE_INTRODUCER,
  CHANGE_EMERGENCY_NAME,
  CHANGE_EMERGENCY_PHONE,
  CHANGE_DRUG_A,
  CHANGE_PREGANT_A,
  CHANGE_SMOKING_A,
  CHANGE_IS_SIG_EMPTY,
} from '../constant';

const initState = {
  name: '',
  birth: undefined,
  gender: undefined,
  nationalId: undefined,
  bloodType: undefined,
  phone: undefined,
  address: undefined,
  career: undefined,
  marriage: undefined,
  introducer: undefined,
  disease: [],
  allergy: [],
  doDrug: undefined,
  drug: undefined,
  pregnant: undefined,
  pregnantDate: undefined,
  smoking: undefined,
  smokingAmount: undefined,
  other: [],
  emergencyContact: { name: undefined, phone: undefined, relationship: undefined },
  isSigEmpty: true,
  signature: undefined,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const data = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_GENDER:
        draft.gender = action.gender;
        break;
      case CHANGE_BLOOD_TYPE:
        draft.bloodType = action.bloodType;
        break;
      case CHANGE_CAREER:
        draft.career = action.career;
        break;
      case CHANGE_MARRIAGE:
        draft.marriage = action.marriage;
        break;
      case CHANGE_EMERGENCY_RELATIONSHIP:
        draft.emergencyContact.relationship = action.relationship;
        break;
      case CHANGE_DISEASE:
        draft.disease = toggleArrayItem(state.disease, action.disease);
        break;
      case CHANGE_ALLERGY:
        draft.allergy = toggleArrayItem(state.allergy, action.allergy);
        break;
      case CHANGE_DO_DRUG:
        draft.doDrug = action.doDrug;
        break;
      case CHANGE_PREGANT:
        draft.pregnant = action.pregnant;
        break;
      case CHANGE_SMOKING:
        draft.smoking = action.smoking;
        break;
      case CHANGE_OTHER:
        draft.other = toggleArrayItem(state.other, action.other);
        break;
      case CHANGE_NAME:
        draft.name = action.name;
        break;
      case CHANGE_BIRTH:
        draft.birth = action.birth;
        break;
      case CHANGE_NATIONAL_ID:
        draft.nationalId = action.id;
        break;
      case CHANGE_PHONE:
        draft.phone = action.phone;
        break;
      case CHANGE_ADDRESS:
        draft.address = action.address;
        break;
      case CHANGE_INTRODUCER:
        draft.introducer = action.name;
        break;
      case CHANGE_EMERGENCY_NAME:
        draft.emergencyContact.name = action.name;
        break;
      case CHANGE_EMERGENCY_PHONE:
        draft.emergencyContact.phone = action.phone;
        break;
      case CHANGE_DRUG_A:
        draft.drug = action.drug;
        break;
      case CHANGE_PREGANT_A:
        draft.pregnantDate = action.date;
        break;
      case CHANGE_SMOKING_A:
        draft.smokingAmount = action.amount;
        break;
      case CHANGE_IS_SIG_EMPTY:
        draft.isSigEmpty = action.isEmpty;
        break;
      default:
        break;
    }
  });

export default data;
