import React from 'react';
import styled from 'styled-components';
import { Tooltip } from 'antd';
import icImg from '../../../images/IC.svg';

export const Container = styled.div`
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  font-weight: bold;
  padding: 10px;
  & > :first-child {
    display: flex;
    align-items: center;
    &:first-child {
      font-size: 15px;
      font-weight: 600;
    }
  }
  & > :last-child {
    display: flex;
    align-items: center;
  }
`;

export const Content = styled.div`
  height: 400px;
  overflow-y: scroll;
  padding: ${props => (props.noPadding ? 0 : '10px')};
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

export const Count = styled.div`
  font-size: 12px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  background: #e4eaff;
  padding: 2px 7px;
  min-width: 20px;
  min-height: 20px;
  margin-left: 6px;
`;

export const Ic = () => (
  <Tooltip title="病患IC卡的內容">
    <img src={icImg} alt="IC" style={{ marginLeft: '25px' }} />
  </Tooltip>
);

export const BlueDottedUnderlineText = props => (
  <span style={{ fontSize: '14px', color: '#3366ff', borderBottom: '1px dashed #3366ff', cursor: 'pointer' }}>
    {props.text}
  </span>
);
