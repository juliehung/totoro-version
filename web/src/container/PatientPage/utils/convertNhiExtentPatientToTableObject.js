export default function convertNhiExtentPatientToTableObject(nhiExtendPatient, nhiProcedure = []) {
  if (!nhiExtendPatient) return [];
  const nhiMedicalRecords = nhiExtendPatient.nhiMedicalRecords.map(n => {
    const key = n.id;
    const date = n.date;
    const teeth = n.part;
    const nhiCode = n.nhiCode;
    const isDental = nhiProcedure.find(n => n.code === nhiCode) ? true : false;
    return { key, date, teeth, nhiCode, isDental };
  });
  const orderedNhiMedicalRecords = nhiMedicalRecords.sort((a, b) => b.date - a.date);
  return orderedNhiMedicalRecords;
}
