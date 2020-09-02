export function parseIndexTreatmentProcedureToTableObject(indexTreatmentProcedure) {
  const groupedArray = groupIndexTreatmentProcedureByDisposalId(indexTreatmentProcedure);
  const tableObject = groupedArrayToTableIbject(groupedArray);

  return tableObject;
}

function groupIndexTreatmentProcedureByDisposalId(indexTreatmentProcedure) {
  const disposalIds = [...new Set(indexTreatmentProcedure.map(i => i.disposalId))];
  const groupedArray = disposalIds.map(d => ({
    [d]: indexTreatmentProcedure.filter(i => d === i.disposalId),
  }));
  return groupedArray;
}

function groupedArrayToTableIbject(groupedArray) {
  const tableObject = groupedArray.map(g => {
    const values = Object.values(g)[0];
    const sample = values[0];
    const { did, serialNumber, examinationPoint, examinationCode, a31, a32, disposalId } = sample;
    return { did, serialNumber, examinationPoint, examinationCode, a31, a32, disposalId, treatments: values };
  });
  return tableObject;
}
