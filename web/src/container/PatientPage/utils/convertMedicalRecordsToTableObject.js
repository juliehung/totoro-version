export default function convertMedicalRecordsToTableObject(medicalRecords) {
  if (!medicalRecords) return [];
  return medicalRecords.map(t => {
    const key = t.id;

    const date = t.date;

    const medicalCategory = t.medicalCategory;

    const clinic = t.medicalInstitutionCode;

    return { key, date, medicalCategory, clinic };
  });
}
