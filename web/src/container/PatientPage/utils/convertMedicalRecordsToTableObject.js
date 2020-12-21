export default function convertMedicalRecordsToTableObject(medicalRecords) {
  if (!medicalRecords) return [];
  const reMedicalRecords = medicalRecords.map(t => {
    const key = t.id;

    const { date, seqNumber, medicalCategory, medicalInstitutionCode } = t;

    return { key, date, medicalCategory, medicalInstitutionCode, seqNumber };
  });

  return reMedicalRecords.filter(d => d?.date && d?.medicalInstitutionCode).sort((a, b) => b.date - a.date);
}
