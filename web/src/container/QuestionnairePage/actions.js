import {
  NEXT_PAGE,
  PREV_PAGE,
  GOTO_PAGE,
  PRE_CHANGE_GENDER,
  CHANGE_GENDER,
  PRE_CHANGE_BLOOD_TYPE,
  CHANGE_BLOOD_TYPE,
  PRE_CHANGE_CAREER,
  CHANGE_CAREER,
  PRE_CHANGE_MARRIAGE,
  CHANGE_MARRIAGE,
  PRE_CHANGE_EMERGENCY_RELATIONSHIP,
  CHANGE_EMERGENCY_RELATIONSHIP,
  CHANGE_DISEASE,
  CHANGE_ALLERGY,
  PRE_CHANGE_DO_DRUG,
  CHANGE_DO_DRUG,
  PRE_CHANGE_PREGANT,
  CHANGE_PREGANT,
  PRE_CHANGE_SMOKING,
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
  INIT_QUESTIONNAIRE,
  CREATE_Q_WITH_SIGN,
  CREATE_Q_WITHOUT_SIGN,
  GET_PATIENT_START,
  GET_PATIENT_SUCCESS,
  CREATE_Q_SUCCESS,
  CREATE_Q_FAILURE,
  INIT_PAGE,
  GET_DOC_START,
  GET_DOC_SUCCESS,
  VALIDATE_SUCCESS,
  VALIDATE_FAIL,
} from './constant';

export function nextPage() {
  return { type: NEXT_PAGE };
}

export function prevPage() {
  return { type: PREV_PAGE };
}

export function gotoPage(page) {
  return { type: GOTO_PAGE, page };
}

export function preChangeGender(gender) {
  return { type: PRE_CHANGE_GENDER, gender };
}

export function changeGender(gender) {
  return { type: CHANGE_GENDER, gender };
}

export function preChangeBloodType(bloodType) {
  return { type: PRE_CHANGE_BLOOD_TYPE, bloodType };
}

export function changeBloodType(bloodType) {
  return { type: CHANGE_BLOOD_TYPE, bloodType };
}

export function preChangeCareer(career) {
  return { type: PRE_CHANGE_CAREER, career };
}

export function changeCareer(career) {
  return { type: CHANGE_CAREER, career };
}

export function preChangeMarriage(marriage) {
  return { type: PRE_CHANGE_MARRIAGE, marriage };
}

export function changeMarriage(marriage) {
  return { type: CHANGE_MARRIAGE, marriage };
}

export function preChangeEmergencyRelationship(relationship) {
  return { type: PRE_CHANGE_EMERGENCY_RELATIONSHIP, relationship };
}

export function changeEmergencyRelationship(relationship) {
  return { type: CHANGE_EMERGENCY_RELATIONSHIP, relationship };
}

export function changeDisease(disease) {
  return { type: CHANGE_DISEASE, disease };
}

export function changeAllergy(allergy) {
  return { type: CHANGE_ALLERGY, allergy };
}

export function preChangeDoDrug(doDrug) {
  return { type: PRE_CHANGE_DO_DRUG, doDrug };
}

export function changeDoDrug(doDrug) {
  return { type: CHANGE_DO_DRUG, doDrug };
}

export function preChangePregnant(pregnant) {
  return { type: PRE_CHANGE_PREGANT, pregnant };
}

export function changePregnant(pregnant) {
  return { type: CHANGE_PREGANT, pregnant };
}

export function preChangeSmoking(smoking) {
  return { type: PRE_CHANGE_SMOKING, smoking };
}

export function changeSmoking(smoking) {
  return { type: CHANGE_SMOKING, smoking };
}

export function changeOther(other) {
  return { type: CHANGE_OTHER, other };
}

export function changeName(name) {
  return { type: CHANGE_NAME, name };
}

export function changeBirth(birth) {
  return { type: CHANGE_BIRTH, birth };
}

export function changeNationalId(id) {
  return { type: CHANGE_NATIONAL_ID, id };
}

export function changePhone(phone) {
  return { type: CHANGE_PHONE, phone };
}

export function changeAddress(address) {
  return { type: CHANGE_ADDRESS, address };
}

export function changeIntroducer(name) {
  return { type: CHANGE_INTRODUCER, name };
}

export function changeEmergencyName(name) {
  return { type: CHANGE_EMERGENCY_NAME, name };
}

export function changeEmergencyPhone(phone) {
  return { type: CHANGE_EMERGENCY_PHONE, phone };
}

export function changeDrug(drug) {
  return { type: CHANGE_DRUG_A, drug };
}

export function changePregnantDate(date) {
  return { type: CHANGE_PREGANT_A, date };
}

export function changeSmokingAmount(amount) {
  return { type: CHANGE_SMOKING_A, amount };
}

export function changeIsSigEmpty(isEmpty) {
  return { type: CHANGE_IS_SIG_EMPTY, isEmpty };
}

export function initQuestionnaire(patient) {
  return { type: INIT_QUESTIONNAIRE, patient };
}

export function createQWSign(sign) {
  return { type: CREATE_Q_WITH_SIGN, sign };
}

export function createQWOSign() {
  return { type: CREATE_Q_WITHOUT_SIGN };
}

export function changeCreateQSuccess(id) {
  return { type: CREATE_Q_SUCCESS, id };
}

export function changeCreateQFailure(id) {
  return { type: CREATE_Q_FAILURE, id };
}

export function getPatient(pid) {
  return { type: GET_PATIENT_START, pid };
}

export function getPatientSuccess(patient) {
  return { type: GET_PATIENT_SUCCESS, patient };
}

export function initPage() {
  return { type: INIT_PAGE };
}

export function getDoc(id) {
  return { type: GET_DOC_START, id };
}

export function getDocSuccess(doc) {
  return { type: GET_DOC_SUCCESS, doc };
}

export function validateSuccess(page) {
  return { type: VALIDATE_SUCCESS, page };
}

export function valitationFail(page) {
  return { type: VALIDATE_FAIL, page };
}
