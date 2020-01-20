import React from 'react';
import styled from 'styled-components';
import { Button, Icon } from 'antd';

const StyledButton = styled(Button)`
  font-size: 16px !important;
  visibility: ${props => (props.disabled ? 'hidden' : 'show')};
`;

export default function ConfirmButton(props) {
  return (
    <StyledButton type="primary" onClick={props.nextPage} disabled={props.disabled}>
      <span>
        確認
        <Icon type="check" />
      </span>
    </StyledButton>
  );
}
