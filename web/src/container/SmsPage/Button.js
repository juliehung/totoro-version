import styled from 'styled-components';
import { Button } from 'antd';

export const StyledButton = styled(Button)`
  padding: 0 16px;
  &.ant-btn:active,
  &.ant-btn.active {
    background: transparent;
  }

  &.ant-btn-primary {
    background-color: #3266ff;
    border-color: #3266ff;
  }

  &.ant-btn-primary:hover,
  &.ant-btn-primary:focus,
  &.ant-btn-primary:active {
    background-color: #3266ff;
    border-color: transparent;
  }

  &.ant-btn-link {
    color: #3266ff;
  }

  &.ant-btn-link:hover,
  &.ant-btn-link:focus {
    color: #3266ff;
    & > svg:hover {
      fill: #3266ff;
    }
  }
`;

export const StyledLargerButton = styled(StyledButton)`
  &.styled-larger-btn {
    height: 48px;
    padding: 0 20px;
  }
`;
