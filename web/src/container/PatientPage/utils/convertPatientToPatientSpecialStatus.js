const [tagPregnantId, tagSmokingId] = [25, 26];

export default function convertPatientToPatientSpecialStatus(patient) {
  if (!patient?.tags?.reduce) return [];
  const group = patient.tags.reduce((r, a) => {
    r[a.type] = [...(r[a.type] || []), a];
    return r;
  }, {});

  const allergy = {};
  const disease = {};
  const others = {};
  const pregnant = {};
  const smoking = {};

  if (group.ALLERGY) {
    allergy.title = '藥物過敏';
    allergy.subTitle = group.ALLERGY.map(a => a.name).join(', ');
  }

  if (group.DISEASE) {
    disease.title = '疾病歷史';
    disease.subTitle = group.DISEASE.map(a => a.name).join(', ');
  }

  if (group.OTHER) {
    if (group.OTHER.find(o => o.id === tagPregnantId)) {
      pregnant.title = '懷孕中';
    }

    if (group.OTHER.find(o => o.id === tagSmokingId)) {
      smoking.title = '吸煙中';
    }
  }

  return [pregnant, smoking, allergy, disease, others].filter(value => Object.keys(value).length !== 0);
}
