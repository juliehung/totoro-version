import styled from 'styled-components';
import { Default, Gray600 } from './colors';


export const NoMarginText = styled.p`
  margin: auto 0;
  color : ${Default};
`;

export const Title = styled(NoMarginText)`
  font-size: 18px;
  font-weight: 600;
`;

export const Subtitle = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
`;


export const Caption = styled(NoMarginText)`
  font-size: 12px;
  color: ${Gray600};
`;

export const P2 = styled(NoMarginText)`
  font-size: 13px;
`;
