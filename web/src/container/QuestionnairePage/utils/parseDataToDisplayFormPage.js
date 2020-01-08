import { GenderOption, CareerOption, MarriageOption, RelationshipOption } from '../constant_options';

export function parseDataToDisplayFormPage(patient) {
  console.log(patient);
  const name = patient.name;
  const birth = patient.birth;
  const nationalId = patient.nationalId;
  const gender = findTextInOption(patient.gender, GenderOption);
  const bloodType = patient.blood;
  const phone = patient.phone;
  const address = patient.address;
  const career = findTextInOption(patient.career, CareerOption);
  const marriage = findTextInOption(patient.marriage, MarriageOption);
  const introducer = patient.introducer;
  const emergencyName = patient.emergencyName;
  const emergencyPhone = patient.emergencyPhone;
  const emergencyRelationship = findTextInOption(patient.emergencyRelationship, RelationshipOption);
  const disease = patient.tags
    ? patient.tags
        .filter(t => t.type === 'DISEASE')
        .map(t => t.name)
        .join(', ')
    : undefined;
  const drug = patient.questionnaire ? patient.questionnaire.drug : undefined;
  const drugName = drug ? (patient.questionnaire ? patient.questionnaire.drugName : undefined) : undefined;
  const allergy = patient.tags
    ? patient.tags
        .filter(t => t.type === 'ALLERGY')
        .map(t => t.name)
        .join(', ')
    : undefined;
  const other = patient.tags
    .filter(t => t.type === 'OTHER' && ![25, 26].includes(t.id))
    .map(t => t.name)
    .join(', ');
  console.log(patient.tags);

  // !todo
  // const pregnant = patient.tags ? patient.tags.find(t => t.id === 25) : undefined;
  // const smoking = patient.tags ? patient.tags.find(t => t.id === 26) : undefined;

  const patientForDisplay = {
    name,
    birth,
    nationalId,
    gender,
    bloodType,
    phone,
    address,
    career,
    marriage,
    introducer,
    emergencyName,
    emergencyPhone,
    emergencyRelationship,
    disease,
    drugName,
    allergy,
    other,
  };

  console.log(123, patientForDisplay);

  return patientForDisplay;
}

function findTextInOption(text, defaultOptions) {
  if (!text) {
    return undefined;
  }
  const displayText = defaultOptions.find(d => d.code === text);
  if (displayText) {
    return displayText.value;
  }
  return undefined;
}
