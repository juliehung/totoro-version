export default function convertMedicalRecordsToTableObject(medicalRecords) {
  if (!medicalRecords) return [];
  return medicalRecords
    .map(t => {
      if (t) {
        const key = t.id;

        const { date, seqNumber, medicalCategory, medicalInstitutionCode } = t;

        return { key, date, medicalCategory, medicalInstitutionCode, seqNumber };
      }
    })
    .filter(d => d?.date && d?.medicalInstitutionCode)
    .sort((a, b) => b.date - a.date);
}
