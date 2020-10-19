function convertDisposalsToTreatmentsAndPrescriptions(disposals) {
  if (!disposals) {
    return [];
  }

  const returnTreatments = [];
  const returnPrescriptions = [];

  disposals.forEach(d => {
    const { createdDate, createdBy, treatmentProcedures, prescription } = d;
    const template = { createdDate, createdBy };

    // treatmentProcedures
    treatmentProcedures?.forEach &&
      treatmentProcedures.forEach(t => {
        let catagory;
        let title;
        let content;
        if (t.nhiProcedure) {
          catagory = '健保';
          title = t?.nhiProcedure?.code ?? '';
          content = t?.nhiProcedure?.nhiIcd10Pcs?.map(n => `${n.code} ${n.nhiName}`)?.join(', ') ?? '';
        } else {
          catagory = '非健保';
          title = t?.procedure?.code ?? '';
        }
        const teeth = t.teeth?.map(teeth => teeth.position)?.join(', ') ?? '-';
        returnTreatments.push({ ...template, catagory, teeth, title, content });
      });

    // prescription
    prescription?.treatmentDrugs?.forEach &&
      prescription.treatmentDrugs.forEach(() => {
        const catagory = '藥品';
        const teeth = '-';
        let title;
        let content;
        returnPrescriptions.push({ ...template, catagory, teeth, title, content });
      });
  });

  return { treatments: returnTreatments, prescriptions: returnPrescriptions };
}

export default convertDisposalsToTreatmentsAndPrescriptions;
