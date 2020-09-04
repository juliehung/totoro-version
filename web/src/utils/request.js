import { Cookies } from 'react-cookie';
const cookies = new Cookies();

export function parseJSON(response) {
  if (response.status === 204 || response.status === 205) {
    return null;
  }
  return response.json();
}

export function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

export default function request(url, options) {
  const token = cookies.get('token');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  let option;

  if (options) {
    option = {
      ...options,
      headers: {
        ...options.headers,
        Authorization: `Bearer ${token}`,
      },
    };
  } else {
    option = { headers };
  }

  return fetch(url, option).then(checkStatus).then(parseJSON);
}
