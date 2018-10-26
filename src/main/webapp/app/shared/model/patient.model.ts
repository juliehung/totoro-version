import { Moment } from 'moment';
import { IAppointment } from 'app/shared/model//appointment.model';

export const enum Gender {
  OTHER = 'OTHER',
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export const enum Blood {
  UNKNOWN = 'UNKNOWN',
  A = 'A',
  AB = 'AB',
  B = 'B',
  O = 'O'
}

export interface IPatient {
  id?: number;
  name?: string;
  nationalId?: string;
  gender?: Gender;
  birth?: Moment;
  phone?: string;
  medicalId?: string;
  zip?: string;
  address?: string;
  email?: string;
  photo?: string;
  blood?: Blood;
  cardId?: string;
  vip?: string;
  dominantDoctor?: number;
  firstDoctor?: number;
  introducer?: number;
  updateUser?: number;
  emergencyName?: string;
  emergencyPhone?: string;
  deleteDate?: Moment;
  scaling?: Moment;
  allergy?: boolean;
  inconvenience?: boolean;
  seriousDisease?: boolean;
  lineId?: string;
  fbId?: string;
  reminder?: string;
  lastModifiedTime?: Moment;
  writeIcTime?: Moment;
  appointments?: IAppointment[];
}

export const defaultValue: Readonly<IPatient> = {
  allergy: false,
  inconvenience: false,
  seriousDisease: false
};
