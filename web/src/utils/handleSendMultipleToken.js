import { Cookies } from 'react-cookie';
import { cloudName } from './cloudName';

const cookies = new Cookies();

export function handleSendMultipleToken() {
  const token = cookies.get('token') || undefined;
  if (cloudName && token) {
    try {
      return token[cloudName];

      const tokens = JSON.parse(unescape(token));
      return tokens[cloudName];
    } catch (e) {
      console.log(e);
    }
  }

  return token;
}
