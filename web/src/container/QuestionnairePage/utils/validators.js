import moment from 'moment';

export function nameValidator(patient) {
  return patient.name?.length > 0;
}

export function nationalIdValidator(patient, isPatientExist, existedPatientId) {
  return !isPatientExist || !existedPatientId || patient.id === existedPatientId;
}

export function birthValidator(patient) {
  const { birth } = patient;
  return birth && moment().isAfter(moment(birth)) && moment('1911-01-01').isBefore(moment(birth));
}

export function phoneValidator(patient) {
  const { phone } = patient;
  return phone && (phone.match(/^09\d{2}-?\d{3}-?\d{3}$/) || phone.match(/^(?!09).*/));
}

export function drugValidator(patient) {
  const { doDrug, drug } = patient;
  if (doDrug === 'A') {
    return drug?.length;
  }
  return true;
}

export function smokingAmountValidator(patient) {
  const { smoking, smokingAmount } = patient;
  if (smoking === 'A') {
    return smokingAmount > 0;
  }
  return true;
}

export function pregnantDateValidator(patient) {
  const { pregnant, pregnantDate } = patient;
  if (pregnant === 'A') {
    return (
      pregnantDate && moment().add(2, 'y').isAfter(moment(pregnantDate)) && moment().isBefore(moment(pregnantDate))
    );
  }
  return true;
}
