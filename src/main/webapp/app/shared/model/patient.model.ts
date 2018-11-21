import { Moment } from 'moment';
import { IAppointment } from 'app/shared/model//appointment.model';
import { IPatient } from 'app/shared/model//patient.model';
import { ITag } from 'app/shared/model//tag.model';

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
  emergencyName?: string;
  emergencyPhone?: string;
  deleteDate?: Moment;
  scaling?: Moment;
  lineId?: string;
  fbId?: string;
  reminder?: string;
  writeIcTime?: Moment;
  appointments?: IAppointment[];
  introducer?: IPatient;
  parents?: IPatient[];
  spouse1S?: IPatient[];
  tags?: ITag[];
  children?: IPatient[];
  spouse2S?: IPatient[];
}

export const defaultValue: Readonly<IPatient> = {};
