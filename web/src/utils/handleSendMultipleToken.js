import { Cookies } from 'react-cookie';
import { cloudName } from './cloudName';

const cookies = new Cookies();

export function handleSendMultipleToken() {
  const token = cookies.get('token') || undefined;

  console.log('cloudName', cloudName);
  console.log('token', token);

  if (cloudName && token) {
    try {
      console.log('JSON.parse');

      const tokens = JSON.parse(unescape(token));
      console.log('tokens', tokens);

      return tokens[cloudName];
    } catch (e) {
      console.log(e);
    }
  }

  return token;
}
