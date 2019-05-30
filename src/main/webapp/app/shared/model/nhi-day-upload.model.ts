import { Moment } from 'moment';
import { INhiDayUploadDetails } from 'app/shared/model//nhi-day-upload-details.model';

export interface INhiDayUpload {
  id?: number;
  date?: Moment;
  dayUploadDetails?: INhiDayUploadDetails[];
}

export const defaultValue: Readonly<INhiDayUpload> = {};