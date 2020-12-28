function getCurrentMonthPoint(totalPointByDisposalDate) {
  if (!totalPointByDisposalDate?.data) {
    return {};
  }
  const filterWithDate = totalPointByDisposalDate?.data
    .filter(
      ({ disposalDate }) =>
        new Date(totalPointByDisposalDate?.startDate).getMonth() + 1 === new Date(disposalDate).getMonth() + 1,
    )
    .sort((a, b) => new Date(a.disposalDate).getTime() - new Date(b.disposalDate).getTime());
  const totalPoint = filterWithDate.map(({ total }) => total).reduce((a, b) => a + b);
  const treatmentTotalPoint = filterWithDate.map(({ treatmentPoint }) => treatmentPoint).reduce((a, b) => a + b);
  const examinationTotalPoint = filterWithDate
    .map(
      ({ infectionExaminationPoint, regularExaminationPoint }) => infectionExaminationPoint + regularExaminationPoint,
    )
    .reduce((a, b) => a + b);
  const totalAbs = Math.round(totalPoint / filterWithDate.length);
  const colors = filterWithDate.map(({ total = 0 }) => (total > totalAbs ? '#26c8f0' : '#ffc935'));

  return {
    totalPoint,
    treatmentTotalPoint,
    examinationTotalPoint,
    totalPointByDisposalDate: filterWithDate.map(data => {
      return {
        ...data,
        name: `${new Date(data?.disposalDate).getDate()}`,
        color: data?.total > totalAbs ? '#26c8f0' : '#ffc935',
      };
    }),
    colors: colors,
  };
}

export default getCurrentMonthPoint;
