import produce from 'immer';
import { toggleArrayItem } from '../utils/toggleArrayItem';
import { parseTextToOption } from '../utils/parseTextToOption';
import { parseTagToOption } from '../utils/parseTagToOption';
import {
  GenderOption,
  BloodTypeOption,
  CareerOption,
  MarriageOption,
  RelationshipOption,
  tags,
} from '../constant_options';
import {
  INIT_QUESTIONNAIRE,
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
  GET_PATIENT_SUCCESS,
  INIT_PAGE,
} from '../constant';

const initState = {
  patientEntity: undefined,
  patient: {
    id: undefined,
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
  },
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const data = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case INIT_PAGE:
        draft.patient = initState.patient;
        draft.patientEntity = initState.patientEntity;
        break;
      case GET_PATIENT_SUCCESS:
        draft.patientEntity = action.patient;
        draft.patient.id = action.patient.id;
        draft.patient.name = action.patient.name;
        draft.patient.phone = action.patient.phone;
        draft.patient.gender = parseTextToOption(action.patient.gender, GenderOption);
        draft.patient.birth = action.patient.birth;
        draft.patient.emergencyContact.name = action.patient.emergencyName;
        draft.patient.emergencyContact.phone = action.patient.emergencyPhone;
        draft.patient.emergencyContact.relationship = parseTextToOption(
          action.patient.emergencyRelationship,
          RelationshipOption,
        );
        draft.patient.nationalId = action.patient.nationalId;
        draft.patient.address = action.patient.address;
        draft.patient.bloodType = parseTextToOption(action.patient.blood, BloodTypeOption);
        draft.patient.career = parseTextToOption(action.patient.career, CareerOption);
        draft.patient.marriage = parseTextToOption(action.patient.marriage, MarriageOption);
        draft.patient.disease = parseTagToOption(action.patient.tags, 'DISEASE', tags);
        draft.patient.allergy = parseTagToOption(action.patient.tags, 'ALLERGY', tags);
        draft.patient.other = parseTagToOption(action.patient.tags, 'OTHER', tags);

        draft.patient.doDrug = action.patient.questionnaire ? (action.patient.questionnaire.drug ? 'A' : 'B') : 'B';
        draft.patient.drug = action.patient.questionnaire ? action.patient.questionnaire.drugName : undefined;

        draft.patient.pregnant = action.patient.tags.find(tag => tag.id === 25) ? 'A' : 'B';
        draft.patient.smoking = action.patient.tags.find(tag => tag.id === 26) ? 'A' : 'B';
        draft.patient.smokingAmount = action.patient.questionnaire ? action.patient.questionnaire.smokeNumberADay : 0;

        break;
      case INIT_QUESTIONNAIRE:
        if (action.patient.name) {
          draft.patient.name = action.patient.name;
        }
        if (action.patient.id) {
          draft.patient.id = action.patient.id;
        }
        break;
      case CHANGE_GENDER:
        draft.patient.gender = action.gender;
        break;
      case CHANGE_BLOOD_TYPE:
        draft.patient.bloodType = action.bloodType;
        break;
      case CHANGE_CAREER:
        draft.patient.career = action.career;
        break;
      case CHANGE_MARRIAGE:
        draft.patient.marriage = action.marriage;
        break;
      case CHANGE_EMERGENCY_RELATIONSHIP:
        draft.patient.emergencyContact.relationship = action.relationship;
        break;
      case CHANGE_DISEASE:
        draft.patient.disease = toggleArrayItem(state.patient.disease, action.disease);
        break;
      case CHANGE_ALLERGY:
        draft.patient.allergy = toggleArrayItem(state.patient.allergy, action.allergy);
        break;
      case CHANGE_DO_DRUG:
        draft.patient.doDrug = action.doDrug;
        break;
      case CHANGE_PREGANT:
        draft.patient.pregnant = action.pregnant;
        break;
      case CHANGE_SMOKING:
        draft.patient.smoking = action.smoking;
        break;
      case CHANGE_OTHER:
        draft.patient.other = toggleArrayItem(state.patient.other, action.other);
        break;
      case CHANGE_NAME:
        draft.patient.name = action.name;
        break;
      case CHANGE_BIRTH:
        draft.patient.birth = action.birth;
        break;
      case CHANGE_NATIONAL_ID:
        draft.patient.nationalId = action.id;
        break;
      case CHANGE_PHONE:
        draft.patient.phone = action.phone;
        break;
      case CHANGE_ADDRESS:
        draft.patient.address = action.address;
        break;
      case CHANGE_INTRODUCER:
        draft.patient.introducer = action.name;
        break;
      case CHANGE_EMERGENCY_NAME:
        draft.patient.emergencyContact.name = action.name;
        break;
      case CHANGE_EMERGENCY_PHONE:
        draft.patient.emergencyContact.phone = action.phone;
        break;
      case CHANGE_DRUG_A:
        draft.patient.drug = action.drug;
        break;
      case CHANGE_PREGANT_A:
        draft.patient.pregnantDate = action.date;
        break;
      case CHANGE_SMOKING_A:
        draft.patient.smokingAmount = action.amount;
        break;
      default:
        break;
    }
  });

export default data;
