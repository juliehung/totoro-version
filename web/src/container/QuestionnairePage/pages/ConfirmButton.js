import React from 'react';
import styled from 'styled-components';
import { CheckOutlined } from '@ant-design/icons';
import { Button } from 'antd';

const StyledButton = styled(Button)`
  font-size: 16px !important;
`;

export default function ConfirmButton(props) {
  return (
    <StyledButton type="primary" onClick={props.nextPage} disabled={props.disabled}>
      <span>
        確認
        <CheckOutlined />
      </span>
    </StyledButton>
  );
}
