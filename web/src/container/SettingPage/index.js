import React from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { Layout, Tabs } from 'antd';
import { getSettings } from '../Home/actions';
import Xray from './Xray';
import Link from './Link';
import { useWindowSize } from '../../utils/hooks/useWindowSize';

const { Content } = Layout;
const { TabPane } = Tabs;

//#region
const GlobalStyle = createGlobalStyle`
  *{
    user-select: none;
  }
  .ant-tabs.ant-tabs-left{
    height: 100%!important;
  }
  .ant-tabs-ink-bar.ant-tabs-ink-bar-animated{
    width:3px;
  }
`;

const StyledLayout = styled(Layout)`
  padding-top: 30px !important;
  height: 100%;
  background: #fff !important;
`;

const StyledContent = styled(Content)`
  background-color: #fff;
  padding: 40px 5vw;
`;

const TabText = styled.span`
  padding: 0 50px;
  text-align: center;
  letter-spacing: 0.8px;
  width: 100%;
  font-size: 14px;
  font-weight: 500;
`;

//#endregion

function SettingPage() {
  const size = useWindowSize();

  return (
    <StyledLayout>
      <GlobalStyle />
      <Helmet>
        <title>設定</title>
      </Helmet>
      <Tabs defaultActiveKey="link" tabPosition={size.width >= 1100 ? 'left' : 'top'}>
        <TabPane tab={<TabText>X 光軟體設定</TabText>} key="xray">
          <StyledContent>
            <Xray />
          </StyledContent>
        </TabPane>
        <TabPane tab={<TabText>指定連結管理</TabText>} key="link">
          <StyledContent>
            <Link />
          </StyledContent>
        </TabPane>
      </Tabs>
    </StyledLayout>
  );
}

const mapDispatchToProps = { getSettings };

export default connect(null, mapDispatchToProps)(SettingPage);
