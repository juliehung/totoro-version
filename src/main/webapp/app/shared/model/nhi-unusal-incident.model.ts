import { Moment } from 'moment';
import { INHIUnusalContent } from 'app/shared/model//nhi-unusal-content.model';

export interface INHIUnusalIncident {
  id?: number;
  start?: Moment;
  end?: Moment;
  nhiUnusalContent?: INHIUnusalContent;
}

export const defaultValue: Readonly<INHIUnusalIncident> = {};
