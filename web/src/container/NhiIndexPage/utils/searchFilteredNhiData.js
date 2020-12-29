function searchFilteredNhiData(value, validNhiData) {
  const mappingSearchData = Object.values(validNhiData)
    .flat(Infinity)
    .filter(({ nhiExtendDisposal }) => {
      return (
        nhiExtendDisposal?.patientName.indexOf(value) !== -1 ||
        nhiExtendDisposal?.a13.indexOf(value) !== -1 ||
        nhiExtendDisposal?.serialNumber.indexOf(value) !== -1
      );
    });
  const reValidNhiData = {};
  for (const data of mappingSearchData) {
    if (data?.nhiExtendDisposal?.date) {
      reValidNhiData[data?.nhiExtendDisposal?.date] = [].concat(
        reValidNhiData[data?.nhiExtendDisposal?.date] ? reValidNhiData[data?.nhiExtendDisposal?.date] : [],
        [data],
      );
    }
  }

  return reValidNhiData;
}

export default searchFilteredNhiData;
