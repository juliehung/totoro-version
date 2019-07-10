export interface INhiExtendPatient {
  id?: number;
  cardNumber?: string;
  cardAnnotation?: string;
  cardValidDate?: string;
  cardIssueDate?: string;
  nhiIdentity?: string;
  availableTimes?: number;
  scaling?: string;
  fluoride?: string;
  perio?: string;
}

export const defaultValue: Readonly<INhiExtendPatient> = {};
