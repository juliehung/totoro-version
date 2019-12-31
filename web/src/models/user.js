import request from "../utils/request";
import apiUrl from "../utils/apiUrl";

const LOCATION = `users`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class User {
  // GET
  static getAll = async () => {
    let requestURL = `${requestUrl}?size=100`;
    const result = await request(requestURL);
    return result;
  };
}
