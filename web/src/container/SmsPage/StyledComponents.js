import styled from 'styled-components';
import { Button, Modal } from 'antd';
import { Gray700, Gray100 } from '../../utils/colors'

export const StyledButton = styled(Button)`
  padding: 0 16px;
  &.ant-btn:active, &.ant-btn.active {
    background: transparent;
  }


  &.ant-btn-primary {
    background-color: #3266ff;
    border-color: #3266ff;
  }

  &.ant-btn-primary:hover, &.ant-btn-primary:focus, &.ant-btn-primary:active  {
    background-color: #3266ff;
    border-color: transparent;
  }

  &.ant-btn-primary:disabled  {
    color: ${Gray700};
    border: ${Gray700} 1px solid;
    background: ${Gray100};
    &:hover {
      color: ${Gray700};
      border: ${Gray700} 1px solid;
      background: ${Gray100};
    }
  }



  &.ant-btn-link {
    color: #3266ff;
  }

  &.ant-btn-link:hover, &.ant-btn-link:focus {
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

export const StyledMediumButton = styled(StyledButton)`
  &.styled-medium-btn {
    height: 40px;
    padding: 0 16px;
  }
`;

export const StyledModal = styled(Modal)`
  & .ant-modal-content {
    border-radius: 8px;
  }
`;
