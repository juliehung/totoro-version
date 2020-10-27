import React from 'react';
import styled from 'styled-components';
import { Tooltip } from 'antd';
import icImg from '../../../images/IC.svg';
import allDone from '../../../images/all-done.svg';
import close from '../../../images/close.svg';
import warning from '../../../images/alert-circle.svg';
import { toRocString } from '../utils';

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
  max-height: 400px;
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
  min-height: 60px;
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
    <Item can={props.can}>
      <img src={props.can ? allDone : close} height={'90%'} alt="icon" />
      <div>
        <span>{props.title}</span>
        <span>{`${props.can ? toRocString(props?.prev) : toRocString(props?.next)}`}</span>
      </div>
      <div>
        <span>{props.can ? '可申報' : '不可申報'}</span>
        <span>{props.can ? '上次申報' : '始可申報'}</span>
      </div>
    </Item>
  </BorderDiv>
);

export const PatientSpecialStatusItem = props => (
  <BorderDiv>
    <Item warning>
      <img src={warning} height={'90%'} alt="icon" />
      <div>
        <span>{props.title}</span>
        {props.subTitle && <span>{`${props.subTitle}`}</span>}
      </div>
    </Item>
  </BorderDiv>
);
