export interface ITooth {
  id?: number;
  position?: string;
  before?: string;
  planned?: string;
  after?: string;
}

export const defaultValue: Readonly<ITooth> = {};
