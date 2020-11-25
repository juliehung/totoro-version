import React from 'react';
import styled from 'styled-components';
import { Tooltip } from 'antd';
import icImg from '../../../images/IC.svg';
import allDone from '../../../images/all-done.svg';
import close from '../../../images/close.svg';
import warning from '../../../images/alert-circle.svg';

export const Container = styled.div`
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 6px 10px 0 rgba(0, 0, 0, 0.1);
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
    margin-left: 10px;
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
  max-height: 300px;
  height: 100%;
  min-height: 300px;
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

const BorderDiv = styled.div`
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
`;

const Item = styled.div`
  display: flex;
  align-items: ${props => (props.singleLine ? 'center' : '')};
  padding: 10px 5px 10px 0;
  border-left: 4px solid ${props => (props.warning ? '#fe9f43' : props.can ? '#00b383' : '#f2877d')};
  & > * {
    margin-left: 16px;
  }
  & > div {
    display: flex;
    flex-direction: column;
    font-size: 13px;
    color: #222b45;
    & > :first-child {
      font-weight: 600;
    }
    .patient-status-msg {
      color: #8f9bb3;
    }
  }
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

export const PatientDeclarationStatusItem = props => (
  <BorderDiv>
    <Item can={props.validated}>
      <img src={props.validated ? allDone : close} height={'90%'} alt="icon" />
      <div>
        <span>{props.title}</span>
        {props.messages && <span className="patient-status-msg">{props.messages}</span>}
      </div>
    </Item>
  </BorderDiv>
);

export const PatientSpecialStatusItem = props => (
  <BorderDiv>
    <Item warning singleLine={!props.subTitle}>
      <img src={warning} height={'90%'} alt="icon" />
      <div>
        <span>{props.title}</span>
        {props.subTitle && <span>{`${props.subTitle}`}</span>}
      </div>
    </Item>
  </BorderDiv>
);
