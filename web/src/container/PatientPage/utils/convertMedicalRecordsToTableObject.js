export default function convertMedicalRecordsToTableObject(medicalRecords, twRecords) {
  if (!medicalRecords) return [];
  const reMedicalRecords = medicalRecords.map(t => {
    const key = t.id;
    const findMappedTw = twRecords.filter(({ hospitalId }) => hospitalId === t?.medicalInstitutionCode)[0];

    const { date, seqNumber, medicalCategory, medicalInstitutionCode } = t;

    return {
      key,
      date,
      medicalCategory,
      medicalInstitutionCode: `${medicalInstitutionCode} ${findMappedTw?.name}`,
      seqNumber,
    };
  });

  return reMedicalRecords.filter(d => d?.date && d?.medicalInstitutionCode).sort((a, b) => b.date - a.date);
}
