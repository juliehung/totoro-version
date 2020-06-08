import React from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { Menu, Layout } from 'antd';
import { getSettings } from '../Home/actions';
import Xray from './Xray';

const { Content, Sider } = Layout;
const { Item } = Menu;

//#region
const GlobalStyle = createGlobalStyle`
  *{
    user-select: none;
  }
  .ant-switch{
    background:#C5CEE0!important;
  }
  .ant-switch.ant-switch-checked{
    background:#3266FF!important;
  }
  .ant-menu-item{
    &:active{
      background-color:#fff!important;
    }
  }
  .ant-menu:not(.ant-menu-horizontal) .ant-menu-item-selected{
    background-color:#e4eaff!important;
    color:#3167fa!important;
  }
  .ant-menu-item-active{
    &:active{
      background-color:#e4eaff!important;
      color:#3167fa!important;
    }
  }
  .ant-menu-inline .ant-menu-selected::after, .ant-menu-inline .ant-menu-item-selected::after{
    border-right:3px solid #3266ff!important;
    z-index:999;
  }
  .ant-menu-inline{
    border:none!important;
    &::after{
      position:absolute;
      border-right:2px solid #edf1f7;
      height: 100%;
      top:0;
      right:0;
      z-index:998;
    }
  }
`;

const StyledLayout = styled(Layout)`
  padding-top: 30px !important;
  height: 100%;
  background: #fff !important;
`;

const StyledSider = styled(Sider)`
  height: 100% !important;
`;

const StyledMenu = styled(Menu)`
  height: 100% !important;
  overflow-y: scroll !important;
  scrollbar-width: none !important;
  &::-webkit-scrollbar {
    display: none !important;
  }
`;

const StyledContent = styled(Content)`
  background-color: #fff;
  padding: 10px 70px;
`;

const StyleMenuItem = styled(Item)`
  & > span {
    letter-spacing: 0.8px;
  }
`;

//#endregion

function SettingPage() {
  return (
    <StyledLayout>
      <GlobalStyle />
      <Helmet>
        <title>設定</title>
      </Helmet>
      <StyledSider breakpoint="lg" collapsedWidth="0" theme="light">
        <StyledMenu theme="light" mode="inline" defaultSelectedKeys={'xray'}>
          <StyleMenuItem key="xray">
            <span>X 光軟體設定</span>
          </StyleMenuItem>
        </StyledMenu>
      </StyledSider>
      <StyledContent>
        <Xray />
      </StyledContent>
    </StyledLayout>
  );
}

const mapDispatchToProps = { getSettings };

export default connect(null, mapDispatchToProps)(SettingPage);
