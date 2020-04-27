import React from 'react';
import styled, { keyframes } from 'styled-components';
import PlusIcon from '../../images/plus.svg';
import CalendarIcon from '../../images/calendar-fill.svg';
import MoonIcon from '../../images/moon-fill.svg';

//#region
const rotate = keyframes`
  0% { transform: rotate(0deg); }
  100% { transform: rotate(405deg); }
`;

const rotateReverse = keyframes`
  0% { transform: rotate(405deg); }
  100% { transform: rotate(0deg); }
`;

const expandText = keyframes`
  0%{ opacity: 0; }
  60% { opacity: 0; }
  100% { opacity: 1; }
`;

const Container = styled.div`
  position: fixed;
  right: 20px;
  bottom: 20px;
  z-index: 100000;
`;

const FloatingButton = styled.div`
  width: 200px;
  height: 58px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  transition: all ease-in-out 300ms;
  & > :first-child {
    user-select: none;
    background-color: #101426;
    border-radius: 8px;
    color: #fff;
    padding: 8px;
    opacity: ${props => (props.expand ? 1 : 0)};
    animation: ${props => (props.expand ? expandText : '')} 600ms;
  }
  & > :nth-child(2) {
    cursor: pointer;
    margin-left: 15px;
    border-radius: 50%;
  }
`;

const MainButton = styled(FloatingButton)`
  & > :nth-child(2) {
    background-color: #3266ff;
    width: 56px;
    height: 56px;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 8px 16px -4px rgba(50, 102, 255, 0.2);
    transform: ${props => (props.expand ? 'rotate(45deg)' : 'rotate(0)')};
    animation: ${props => (props.loaded ? (props.expand ? rotate : rotateReverse) : '')} 300ms;
    & > img {
      height: 28px;
      width: 28px;
    }
  }
`;

const SubButton = styled(FloatingButton)`
  position: absolute;
  z-index: -1;
  bottom: ${props => (props.expand ? props.distance : 0)}px;
  & > :nth-child(2) {
    background-color: #edf1f7;
    width: 48px;
    height: 48px;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 8px 16px -4px rgba(0, 149, 255, 0.2);
    margin-right: 4px;
    & > img {
      height: 24px;
      width: 24px;
    }
  }
`;

//#endregion

function FloatingActionButton(props) {
  const { expand, loaded, toggleExpand, moonClick, calClick } = props;

  return (
    <Container>
      <SubButton expand={expand} distance={130}>
        <span>醫師與院所休診</span>
        <div onClick={moonClick}>
          <img src={MoonIcon} alt="moon" />
        </div>
      </SubButton>
      <SubButton expand={expand} distance={70}>
        <span>病患約診</span>
        <div onClick={calClick}>
          <img src={CalendarIcon} alt="calendar" />
        </div>
      </SubButton>
      <MainButton expand={expand} loaded={loaded}>
        <span>關閉選單</span>
        <div onClick={toggleExpand}>
          <img src={PlusIcon} alt="plus" />
        </div>
      </MainButton>
    </Container>
  );
}

export default FloatingActionButton;
