export default function convertNhiExtentPatientToTableObject(nhiExtendPatient, nhiProcedures = []) {
  if (!nhiExtendPatient) return [];

  const nhiMedicalRecords = nhiExtendPatient.nhiMedicalRecords
    .map(n => {
      const key = n.id;
      const date = n.date;
      const teeth = n.part;
      const nhiCode = n.nhiCode;
      const isDental = nhiProcedures.find(nhiProcedure => nhiProcedure.code === nhiCode) ? true : false;
      return { key, date, teeth, nhiCode, isDental };
    })
    .filter((nhiMedicalRecord, index, self) => {
      const selfIndex = self.findIndex(
        m =>
          m.date === nhiMedicalRecord.date &&
          m.nhiCode === nhiMedicalRecord.nhiCode &&
          m.part === nhiMedicalRecord.part,
      );
      return index === selfIndex;
    });

  const orderedNhiMedicalRecords = nhiMedicalRecords.sort((a, b) => b.date - a.date);
  return orderedNhiMedicalRecords;
}
