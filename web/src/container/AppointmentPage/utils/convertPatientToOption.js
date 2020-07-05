export default function convertPatientToOption(patients) {
  return patients
    .filter(p => p.name && p.name.length > 0)
    .map(p => ({
      name: p.name,
      medicalId: p.medicalId,
    }));
}
