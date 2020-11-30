import {
  GenderOption,
  BloodTypeOption,
  CareerOption,
  MarriageOption,
  RelationshipOption,
  BooleanOption,
  tags as TagOption,
} from '../constant_options';

export function handlePatientForApi(patientEntity, patient) {
  const name = patient.name;
  const birth = patient.birth;
  const phone = patient.phone;
  const address = patient.address;
  const nationalId = patient.nationalId;
  const introducer = patient.introducer;
  const gender = mapOptionToText(patient.gender, GenderOption);
  const blood = mapOptionToText(patient.bloodType, BloodTypeOption);
  const career = mapOptionToText(patient.career, CareerOption);
  const marriage = mapOptionToText(patient.marriage, MarriageOption);
  const emergencyName = patient.emergencyContact.name;
  const emergencyPhone = patient.emergencyContact.phone;
  const emergencyRelationship = mapOptionToText(patient.emergencyContact.relationship, RelationshipOption);
  const drug = mapOptionToText(patient.doDrug, BooleanOption);
  const drugName = drug ? patient.drug : undefined;
  const smoking = mapOptionToText(patient.smoking, BooleanOption);
  const smokeNumberADay = smoking ? patient.smokingAmount : undefined;
  const pregnant = mapOptionToText(patient.pregnant, BooleanOption);
  const dueDate = pregnant ? patient.pregnantDate : undefined;

  const diseaseOption = TagOption.filter(t => t.jhi_type === 'DISEASE');
  const disease = patient.disease.map(d => mapOptionToText(d, diseaseOption));
  const bloodDiseaseOption = TagOption.filter(t => t.jhi_type === 'BLOOD_DISEASE');
  const bloodDisease = patient.bloodDisease.map(d => mapOptionToText(d, bloodDiseaseOption));

  const allergyOption = TagOption.filter(t => t.jhi_type === 'ALLERGY');
  const allergy = patient.allergy.map(a => mapOptionToText(a, allergyOption));

  const otherOption = TagOption.filter(t => t.jhi_type === 'OTHER');
  const other = patient.other.map(o => mapOptionToText(o, otherOption));

  const tags = [...disease, ...bloodDisease, ...allergy, ...other].filter(t => t).map(id => ({ id }));
  if (pregnant) {
    tags.push({ id: 25 });
  }

  if (smoking) {
    tags.push({ id: 26 });
  }

  let deepCopyPatientEntity = JSON.parse(JSON.stringify(patientEntity));

  const questionnaire = { ...deepCopyPatientEntity.questionnaire, drug, drugName, smokeNumberADay };
  const json = {
    name,
    birth,
    gender,
    blood,
    phone,
    address,
    emergencyName,
    emergencyPhone,
    emergencyRelationship,
    tags,
    questionnaire,
    marriage,
    career,
    nationalId,
    introducer,
    dueDate,
  };

  deepCopyPatientEntity = { ...deepCopyPatientEntity, ...json };

  return deepCopyPatientEntity;
}

function mapOptionToText(option, defaultOptions) {
  if (!option) {
    return undefined;
  }

  const selectedOption = defaultOptions.find(d => d.key === option);
  if (selectedOption) {
    return selectedOption.code;
  }
  return undefined;
}
