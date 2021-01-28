import moment from 'moment';
import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `business/nhi/statistics/doctor-salary`;

export default class DoctorNhiSalary {
  static getInitSalary = async range => {
    const params = {
      begin: `${moment(range.begin).format('YYYY-MM-DD')}`,
      end: `${moment(range.end).format('YYYY-MM-DD')}`,
      excludeDisposalId: range?.checkedModalData ? range?.checkedModalData : [],
    };
    const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}`, params);
    return await request(requestUrl);
  };

  static getSalaryOneByDoctorId = async range => {
    const params = {
      begin: `${moment(range.begin).format('YYYY-MM-DD')}`,
      doctorId: range.doctorId,
      end: `${moment(range.end).format('YYYY-MM-DD')}`,
      excludeDisposalId: range?.checkedModalData ? range?.checkedModalData : [],
    };
    const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}/expands`, params);
    return await request(requestUrl);
  };
  static getTotalPointPresentByDisposalDate = async range => {
    const params = {
      begin: `${moment(range.begin).format('YYYY-MM-DD')}`,
      end: `${moment(range.end).format('YYYY-MM-DD')}`,
      excludeDisposalId: range?.checkedModalData ? range?.checkedModalData : [],
    };
    const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}/present-by-disposal-date`, params);
    return await request(requestUrl);
  };

  static getValidNhiYearMonth = async () => {
    return await request(`${apiUrl}/nhi-month-declarations`);
  };

  static getValidNhiByYearMonth = async yearMonth => {
    return await request(`${apiUrl}/v2/nhi-extend-disposals/yearmonth/${yearMonth}?toleranceA18=true`);
  };

  static getNhiOneByDisposalId = async disposalId => {
    return await request(`${apiUrl}/disposals/rules-checked/${disposalId}`);
  };
}
