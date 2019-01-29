export interface ILedger {
  id?: number;
  amount?: number;
  charge?: number;
  arrears?: number;
}

export const defaultValue: Readonly<ILedger> = {};
