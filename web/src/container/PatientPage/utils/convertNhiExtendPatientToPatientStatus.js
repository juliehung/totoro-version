import moment from 'moment';
const defaultObject = { can: false, next: undefined };

export default function convertNhiExtendPatientToPatientStatus(nhiExtendPatient, patient) {
  let perioObject = { ...defaultObject };
  let scalingObject = { ...defaultObject };
  let fluorideObject = { ...defaultObject };
  if (nhiExtendPatient && patient) {
    const { perio, scaling, fluoride } = nhiExtendPatient;
    const { birth, patientIdentity } = patient;
    const age = moment().diff(moment(birth), 'years');
    const patientIdentityCode = patientIdentity?.code;

    // perio
    if (!perio) {
      perioObject.can = true;
    } else {
      const { prev, next } = parseDate(perio);
      if (next.isBefore(moment())) {
        perioObject.can = true;
      }
      perioObject = { ...perioObject, prev, next };
    }

    // scaling
    if (!scaling) {
      scalingObject.can = true;
    } else {
      const { prev, next } = parseDate(scaling);
      if (next.isBefore(moment())) {
        scalingObject.can = true;
      }
      scalingObject = { ...scalingObject, prev, next };
    }

    // fluoride
    if (!fluoride) {
      // 兒童牙齒塗氟:
      // 1. 未滿六歲兒童，每半年補助一次
      // 2. 未滿十二歲之低收入戶(003)、身心障礙、設籍原住民族地區、偏遠及離島地區兒童(007)，每三個月補助一次。
      const canFluorideAge =
        (age < 6 && age >= 0) || (age < 12 && (patientIdentityCode === '003' || patientIdentityCode === '007'));

      if (canFluorideAge) {
        fluorideObject.can = true;
      }
    } else {
      const { prev, next } = parseDate(fluoride);
      if (next.isBefore(moment())) {
        fluorideObject.can = true;
      }
      fluorideObject = { ...fluorideObject, prev, next };
    }
  }

  return { perio: perioObject, scaling: scalingObject, fluoride: fluorideObject };
}

function parseDate(date) {
  const dateArray = date.split('/');
  const prev = moment(dateArray[0]);
  const next = moment(dateArray[1]);
  return { next, prev };
}
