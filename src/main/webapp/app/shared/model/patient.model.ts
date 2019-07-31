import { Moment } from 'moment';
import { IQuestionnaire } from 'app/shared/model//questionnaire.model';
import { IAppointment } from 'app/shared/model//appointment.model';
import { IPatient } from 'app/shared/model//patient.model';
import { ITag } from 'app/shared/model//tag.model';
import { IPatientIdentity } from 'app/shared/model//patient-identity.model';
import { ITreatment } from 'app/shared/model//treatment.model';
import { ITodo } from 'app/shared/model//todo.model';
import { ITooth } from 'app/shared/model//tooth.model';

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
  lastModifiedDate?: string;
  lastModifiedBy?: string;
  name?: string;
  phone?: string;
  gender?: Gender;
  birth?: Moment;
  nationalId?: string;
  medicalId?: string;
  address?: string;
  email?: string;
  blood?: Blood;
  cardId?: string;
  vip?: string;
  emergencyName?: string;
  emergencyPhone?: string;
  deleteDate?: Moment;
  scaling?: Moment;
  lineId?: string;
  fbId?: string;
  note?: string;
  clinicNote?: string;
  writeIcTime?: Moment;
  avatarContentType?: string;
  avatar?: any;
  newPatient?: boolean;
  emergencyAddress?: string;
  emergencyRelationship?: string;
  mainNoticeChannel?: string;
  career?: string;
  marriage?: string;
  teethGraphPermanentSwitch?: string;
  questionnaire?: IQuestionnaire;
  appointments?: IAppointment[];
  introducer?: IPatient;
  parents?: IPatient[];
  spouse1S?: IPatient[];
  tags?: ITag[];
  children?: IPatient[];
  spouse2S?: IPatient[];
  patientIdentity?: IPatientIdentity;
  treatments?: ITreatment[];
  todos?: ITodo[];
  teeth?: ITooth[];
}

export const defaultValue: Readonly<IPatient> = {
  newPatient: false
};
