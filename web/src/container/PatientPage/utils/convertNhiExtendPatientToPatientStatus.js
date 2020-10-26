import moment from 'moment';
const defaultObject = { is: false, can: false, next: undefined };

export default function convertNhiExtendPatientToPatientStatus(nhiExtendPatient, patient) {
  let perioObject = { ...defaultObject };
  let scalingObject = { ...defaultObject };
  let fluorideObject = { ...defaultObject };
  if (nhiExtendPatient && patient) {
    const { perio, scaling, fluoride } = nhiExtendPatient;
    const { birth } = patient;
    const age = moment().diff(moment(birth), 'years');

    // perio
    if (perio) {
      const { prev, next } = parseDate(perio);
      const is = true;
      if (next.isBefore(moment())) {
        perioObject.can = true;
      }
      perioObject = { ...perioObject, prev, next, is };
    }

    // scaling
    if (scaling && age >= 12) {
      const { prev, next } = parseDate(scaling);
      const is = true;
      if (next.isBefore(moment())) {
        scalingObject.can = true;
      }
      scalingObject = { ...scalingObject, prev, next, is };
    }

    // fluoride
    if (fluoride && age <= 6) {
      const { prev, next } = parseDate(fluoride);
      const is = true;
      if (next.isBefore(moment())) {
        fluorideObject.can = true;
      }
      fluorideObject = { ...fluorideObject, prev, next, is };
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
