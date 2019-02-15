import { Moment } from 'moment';

export interface IDrug {
  id?: number;
  name?: string;
  chineseName?: string;
  type?: string;
  validDate?: Moment;
  endDate?: Moment;
  unit?: string;
  price?: number;
  quantity?: number;
  frequency?: string;
  way?: string;
}

export const defaultValue: Readonly<IDrug> = {};
