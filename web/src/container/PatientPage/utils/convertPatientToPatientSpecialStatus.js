export default function convertPatientToPatientSpecialStatus(patient) {
  if (!patient?.tags?.reduce) return [];
  const group = patient.tags.reduce((r, a) => {
    r[a.type] = [...(r[a.type] || []), a];
    return r;
  }, {});

  const allergy = {};
  const disease = {};
  const others = {};

  if (group.ALLERGY) {
    allergy.title = '藥物過敏';
    allergy.subTitle = group.ALLERGY.map(a => a.name).join(', ');
  }

  if (group.DISEASE) {
    disease.title = '疾病歷史';
    disease.subTitle = group.DISEASE.map(a => a.name).join(', ');
  }

  if (group.OTHER) {
    others.title = '其他';
    others.subTitle = group.OTHER.map(a => a.name).join(', ');
  }

  return [others, allergy, disease].filter(value => Object.keys(value).length !== 0);
}
