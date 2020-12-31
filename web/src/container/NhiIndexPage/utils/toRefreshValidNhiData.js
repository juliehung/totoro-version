function toRefreshValidNhiData(validNhiData, user) {
  if (!validNhiData) {
    return {};
  }
  const userNotionalIds = user.map(user => {
    return {
      nationalId: user?.extendUser?.nationalId,
      doctorName: user?.firstName,
    };
  });
  const reValidNhiData = {};

  validNhiData = validNhiData
    .slice()
    .sort(
      (a, b) =>
        new Date(a?.nhiExtendDisposalList[0]?.date).getTime() - new Date(b?.nhiExtendDisposalList[0]?.date).getTime(),
    );

  for (const data of validNhiData) {
    if (data?.nhiExtendDisposalList && data?.nhiExtendDisposalList[0] && data?.nhiExtendDisposalList[0]?.date) {
      const findDoctor = userNotionalIds.filter(({ nationalId }) => nationalId === data?.nhiExtendDisposalList[0]?.a15);
      reValidNhiData[data?.nhiExtendDisposalList[0]?.date] = [].concat(
        reValidNhiData[data?.nhiExtendDisposalList[0]?.date]
          ? reValidNhiData[data?.nhiExtendDisposalList[0]?.date]
          : [],
        [
          {
            disposalId: data?.disposalId,
            nhiExtendDisposal: {
              ...data?.nhiExtendDisposalList[0],
              doctorName: findDoctor.length !== 0 ? findDoctor[0]?.doctorName : '',
            },
          },
        ],
      );
    }
  }
  return reValidNhiData;
}

export default toRefreshValidNhiData;
