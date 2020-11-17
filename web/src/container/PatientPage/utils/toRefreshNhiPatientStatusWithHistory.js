export default function toRefreshNhiPatientStatusWithHistory(patientStatusObj) {
  if (!patientStatusObj && !patientStatusObj?.messages) {
    return patientStatusObj;
  }
  const new_arr = [];
  for (const msg of patientStatusObj.messages) {
    new_arr.push({
      validated: patientStatusObj.validated,
      messages: msg,
    });
  }

  return new_arr;
}
