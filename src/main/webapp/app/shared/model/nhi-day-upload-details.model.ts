import { INhiDayUpload } from 'app/shared/model//nhi-day-upload.model';

export const enum NhiDayUploadDetailType {
  NORMAL = 'NORMAL',
  CORRECTION = 'CORRECTION'
}

export interface INhiDayUploadDetails {
  id?: number;
  type?: NhiDayUploadDetailType;
  nhiDayUpload?: INhiDayUpload;
}

export const defaultValue: Readonly<INhiDayUploadDetails> = {};
