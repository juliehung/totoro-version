import styled from 'styled-components';
import { Button, Modal, Tag } from 'antd';
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

  & .ant-modal-close {
    &:active {
      transform: translateY(2px) translateX(-2px);
    }
  }
  & .ant-modal-close-x {
    width: 32px;
    height: 32px;
    position: absolute;
    top: -10px;
    right: -10px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 5px 20px 0 rgba(0,0,0,.1);

  }
  & .ant-modal-close-icon {
    display: grid;
    height: 100%;

    & > svg {
      margin: auto;
      fill: black;
    }
  }
`;


export const StyledTag = styled(Tag)`
  &.ant-tag {
    color: #8f9bb3;
    border: #8f9bb3 1px solid;
    border-radius: 12px;
    font-weight: 600;
    padding: 0 16px;
    height: 24px;
    background: rgba(143, 155, 179,.08);
    margin: 2px;
  }
`;