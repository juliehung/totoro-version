export interface IConditionType {
  id?: number;
  major?: string;
  minor?: string;
  display?: boolean;
}

export const defaultValue: Readonly<IConditionType> = {
  display: false
};
