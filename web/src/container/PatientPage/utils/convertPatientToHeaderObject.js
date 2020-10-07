import moment from 'moment';

export default function convertPatientToHeaderObject(patient) {
  if (!patient) return {};

  const { id, name, gender, medicalId, nationalId, birth } = patient;

  const ageMonths = moment().diff(birth, 'months', false);
  const age = { year: Math.floor(ageMonths / 12), month: ageMonths % 12 };

  const ROCBirthYear = moment(birth).year() - 1911;
  const ROCBirth = `${ROCBirthYear}${moment(birth).format('-MM-DD')}`;

  return { id, name, gender, medicalId, nationalId, age, birth, ROCBirth };
}
