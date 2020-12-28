import uuid from 'react-uuid';

function toRefreshValidNhiData(nhiOne, validNhiData, doctorData) {
  if (!validNhiData && !nhiOne) {
    return {};
  }
  const treatmentProcedures = [];
  const userNotionalIds = doctorData.map(doctor => {
    return {
      nationalId: doctor?.extendUser?.nationalId,
      doctorName: doctor?.firstName,
    };
  });
  const findMappingData = validNhiData.filter(({ disposalId }) => disposalId === nhiOne?.id);
  if (nhiOne && nhiOne?.treatmentProcedures.length !== 0) {
    for (const treatmentProcedure of nhiOne?.treatmentProcedures) {
      const treatmentProcedureName = `${treatmentProcedure?.nhiProcedure?.code} ${treatmentProcedure?.nhiProcedure?.name}`;
      treatmentProcedures.push({
        _id: `${nhiOne?.id}-${uuid()}`,
        treatmentProcedureName,
        point: treatmentProcedure?.nhiProcedure?.point,
        total: treatmentProcedure?.total,
        quantity: treatmentProcedure?.quantity,
        multiplier: `${
          Math.round(
            treatmentProcedure?.total / (treatmentProcedure?.quantity * treatmentProcedure?.nhiProcedure?.point),
          ) * 100
        }%`,
      });
    }
  }

  if (findMappingData.length !== 0) {
    const { nhiExtendDisposalList } = findMappingData[0];
    const findMappingDoctor = userNotionalIds.filter(({ nationalId }) => nationalId === nhiExtendDisposalList[0]?.a15);
    const examinationPoint = nhiExtendDisposalList.length !== 0 ? nhiExtendDisposalList[0].examinationPoint : 0;
    const total = treatmentProcedures.map(({ total }) => total).reduce((a, b) => a + b);

    return {
      ...nhiOne,
      patientName: nhiExtendDisposalList[0]?.patientName,
      doctorName: findMappingDoctor.length !== 0 ? findMappingDoctor[0].doctorName : '',
      patientNationalId: nhiExtendDisposalList[0]?.a12,
      examinationPoint,
      treatmentPoint: treatmentProcedures.map(({ total }) => total).reduce((a, b) => a + b),
      totalPoint: total + examinationPoint,
      treatmentProcedureArr: treatmentProcedures,
    };
  }
  return nhiOne;
}

export default toRefreshValidNhiData;
