import moment from 'moment';

export default function toRefreshNhiPatientStatusWithHistory({ patientStatusObj, patient, type }) {
  if (!patientStatusObj && !patientStatusObj?.messages) {
    return patientStatusObj;
  } else {
    const new_arr = [];
    if (!patient) {
      return new_arr;
    } else {
      const { birth } = patient;
      const age = moment().diff(moment(birth), 'years');

      for (const msg of patientStatusObj.messages) {
        if (type === 'fluoride' && age <= 6) {
          new_arr.push({
            validated: patientStatusObj.validated,
            messages: msg,
          });
        }
        if (type === 'scaling' && age >= 12) {
          new_arr.push({
            validated: patientStatusObj.validated,
            messages: msg,
          });
        }
      }

      return new_arr;
    }
  }
}
