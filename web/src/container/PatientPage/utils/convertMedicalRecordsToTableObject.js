export default function convertMedicalRecordsToTableObject(medicalRecords) {
  if (!medicalRecords) return [];
  return medicalRecords.map(t => {
    const key = t.id;

    const { date, seqNumber, medicalCategory, medicalInstitutionCode } = t;

    return { key, date, medicalCategory, medicalInstitutionCode, seqNumber };
  });
}
