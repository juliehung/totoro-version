import moment from 'moment';
import { GenderOption, CareerOption, MarriageOption, RelationshipOption } from '../constant_options';

export function parseDataToDisplayFormPage(patient) {
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

  const pregnant = patient.tags ? patient.tags.find(t => t.id === 25) : undefined;
  const smoking = patient.tags ? patient.tags.find(t => t.id === 26) : undefined;

  let pregnantString;
  if (pregnant) {
    if (patient.dueDate) {
      pregnantString = `懷孕中 (預產期${moment(patient.dueDate).format('YYYY-MM-DD')}) `;
    } else {
      pregnantString = '懷孕中';
    }
  }

  let smokingString = smoking ? '抽菸' : undefined;
  if (smoking) {
    if (patient.questionnaire && patient.questionnaire.smokeNumberADay) {
      smokingString = `抽菸 (一天${patient.questionnaire.smokeNumberADay}支) `;
    } else {
      smokingString = `抽菸`;
    }
  }

  const special = [pregnantString, smokingString].filter(s => s).join(', ');

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
    special,
  };

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
