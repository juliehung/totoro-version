import moment from 'moment';
import { toRocString } from './';

export default function convertPatientToHeaderObject(patient) {
  if (!patient) return {};

  const { id, name, gender, medicalId, nationalId, birth } = patient;

  const ageMonths = moment().diff(birth, 'months', false);
  const age = { year: Math.floor(ageMonths / 12), month: ageMonths % 12 };

  const ROCBirth = toRocString(birth);

  return { id, name, gender, medicalId, nationalId, age, birth, ROCBirth, vipPatient: !!patient?.vipPatient };
}
