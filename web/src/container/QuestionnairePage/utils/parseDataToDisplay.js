import moment from 'moment';
import {
  BloodTypeOption,
  GenderOption,
  CareerOption,
  MarriageOption,
  RelationshipOption,
  DiseaseOption,
  tags,
} from '../constant_options';

export function parseDataToDisplay(data) {
  const name = data.name;
  const birth = data.birth ? moment(data.birth).format('YYYY-MM-DD') : undefined;
  const nationalId = data.nationalId;
  const gender = parseKeyToValue(data.gender, GenderOption);
  const bloodType = parseKeyToValue(data.bloodType, BloodTypeOption);
  const phone = data.phone;
  const address = data.address;
  const career = parseKeyToValue(data.career, CareerOption);
  const marriage = parseKeyToValue(data.marriage, MarriageOption);
  const introducer = data.introducer;

  const emergencyContactName = data.emergencyContact.name;
  const emergencyContactPhone = data.emergencyContact.phone;
  const emergencyContactRelationship = parseKeyToValue(data.emergencyContact.relationship, RelationshipOption);

  const disease = data.disease.map(d => parseKeyToValue(d, DiseaseOption)).join(', ');

  const doDrug = data.doDrug;
  const drug = doDrug === 'A' ? data.drug : undefined;

  const allergyOption = tags.filter(t => t.jhi_type === 'ALLERGY');
  const allergy = data.allergy.map(a => parseKeyToValue(a, allergyOption)).join(', ');

  const specialArray = [];
  const pregnant =
    data.pregnant === 'A' ? `懷孕中(預產期${moment(data.pregnantDate).format('YYYY-MM-DD')})` : undefined;
  const smoking = data.smoking === 'A' ? `吸菸(每日${data.smokingAmount}支)` : undefined;
  if (pregnant) {
    specialArray.push(pregnant);
  }

  if (smoking) {
    specialArray.push(smoking);
  }

  const special = specialArray.join(', ');

  const otherOption = tags.filter(t => t.jhi_type === 'OTHER');
  const other = data.other.map(o => parseKeyToValue(o, otherOption)).join(', ');

  return {
    name,
    birth,
    gender,
    nationalId,
    bloodType,
    phone,
    address,
    career,
    marriage,
    introducer,
    disease,
    allergy,
    drug,
    special,
    other,
    emergencyContactName,
    emergencyContactPhone,
    emergencyContactRelationship,
  };
}

function parseKeyToValue(key, options) {
  const option = options.find(o => o.key === key);
  if (option) {
    return option.value;
  }
  return option;
}