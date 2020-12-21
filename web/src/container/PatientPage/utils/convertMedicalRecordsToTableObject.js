export default function convertMedicalRecordsToTableObject(medicalRecords, twRecords) {
  if (!medicalRecords) return [];
  return medicalRecords.map(t => {
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
}
