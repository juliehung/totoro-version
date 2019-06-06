export interface INhiExtendPatient {
  id?: number;
  cardNumber?: string;
  cardAnnotation?: string;
  cardValidDate?: string;
  cardIssueDate?: string;
  nhiIdentity?: string;
  availableTimes?: number;
}

export const defaultValue: Readonly<INhiExtendPatient> = {};
