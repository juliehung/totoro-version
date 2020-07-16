import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { Layout, Tabs, message } from 'antd';
import { getSettings } from '../Home/actions';
import { onLeavePage, getConfig } from './actions';
import Xray from './Xray';
import Link from './Link';
import { useWindowSize } from '../../utils/hooks/useWindowSize';
import { withRouter } from 'react-router-dom';

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
  .ant-tabs-tab{
    margin: 0!important;
  }
  .ant-tabs-tab:active,
  .ant-tabs-tab.ant-tabs-tab-active{
    background-color: #e4eaff;
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

function SettingPage(props) {
  const { match, onLeavePage, putSuccess, putFailure, getConfig } = props;

  const defaultActiveKey = match.params.section;

  const onTabsChange = key => {
    window.history.pushState({}, '', `#/setting/${key}`);
  };

  useEffect(() => {
    return onLeavePage;
  }, [onLeavePage]);

  useEffect(() => {
    if (putSuccess) {
      getConfig();
      message.success('儲存成功!');
    }
    if (putFailure) {
      message.error('儲存失敗，請在試一次');
    }
  }, [putSuccess, putFailure, getConfig]);

  const size = useWindowSize();

  return (
    <StyledLayout>
      <GlobalStyle />
      <Helmet>
        <title>設定</title>
      </Helmet>
      <Tabs
        defaultActiveKey={defaultActiveKey}
        tabPosition={size.width >= 1100 ? 'left' : 'top'}
        onChange={onTabsChange}
      >
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

const mapStateToProps = ({ settingPageReducer }) => ({
  putSuccess: settingPageReducer.configurations.putSuccess,
  putFailure: settingPageReducer.configurations.putFailure,
});

const mapDispatchToProps = { getSettings, onLeavePage, getConfig };

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(SettingPage));
