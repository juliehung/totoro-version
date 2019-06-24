import { Moment } from 'moment';
import { IHospital } from 'app/shared/model//hospital.model';
import { IRegistration } from 'app/shared/model//registration.model';

export interface IAccounting {
  id?: number;
  registrationFee?: number;
  partialBurden?: number;
  deposit?: number;
  ownExpense?: number;
  other?: number;
  patientIdentity?: string;
  discountReason?: string;
  discount?: number;
  withdrawal?: number;
  transactionTime?: Moment;
  staff?: string;
  hospital?: IHospital;
  registration?: IRegistration;
}

export const defaultValue: Readonly<IAccounting> = {};
