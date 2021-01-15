export default function convertNhiExtentPatientToTableObject(nhiExtendPatient, nhiProcedures = []) {
  if (!nhiExtendPatient) return [];

  const nhiMedicalRecords = nhiExtendPatient.map(n => {
    const key = n?.id;
    const date = n?.date;
    const teeth = n?.part;
    const nhiCode = n?.nhiCode;
    const nhiMandarin = `${n?.nhiCode} ${n?.mandarin || ''}`;
    const isDental = !!nhiProcedures.find(nhiProcedure => nhiProcedure?.code === nhiCode);
    return { key, date, teeth, nhiCode, nhiMandarin, isDental };
  });

  return nhiMedicalRecords.sort((a, b) => b.date - a.date);
}
