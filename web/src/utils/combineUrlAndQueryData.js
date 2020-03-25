const combineUrlAndQueryData = (url, data) => {
  let requestURL = url;
  if (data) {
    const searchParams = new URLSearchParams(data);
    const querystring = searchParams.toString();
    return requestURL + '?' + querystring;
  }
  return requestURL;
};

export default combineUrlAndQueryData;
