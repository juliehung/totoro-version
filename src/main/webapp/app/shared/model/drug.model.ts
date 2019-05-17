export interface IDrug {
  id?: number;
  name?: string;
  chineseName?: string;
  unit?: string;
  price?: number;
  quantity?: number;
  frequency?: string;
  way?: string;
  nhiCode?: string;
  warning?: string;
  days?: number;
  order?: number;
}

export const defaultValue: Readonly<IDrug> = {};
