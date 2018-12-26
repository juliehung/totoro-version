import { Moment } from 'moment';

export interface INHIProcedure {
  id?: number;
  code?: string;
  name?: string;
  point?: number;
  start?: Moment;
  end?: Moment;
  englishName?: string;
}

export const defaultValue: Readonly<INHIProcedure> = {};
