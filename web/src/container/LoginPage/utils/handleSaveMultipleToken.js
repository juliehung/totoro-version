import { Cookies } from 'react-cookie';
import { cloudName } from '../../../utils/cloudName';
const cookies = new Cookies();

export function handleSaveMultipleToken(token) {
  if (cloudName) {
    const cookieToken = cookies.get('token');
    console.log('savedToken', JSON.stringify({ ...cookieToken, [cloudName]: token }));
    return JSON.stringify({ ...cookieToken, [cloudName]: token });
  } else {
    return token;
  }
}
