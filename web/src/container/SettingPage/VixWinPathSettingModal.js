import React, { useState } from 'react';
import { connect } from 'react-redux';
import { Modal, Input, message, Button } from 'antd';
import styled from 'styled-components';
import { getSettings } from '../Home/actions';
import Settings from '../../models/settings';

//#region
const StyledModal = styled(Modal)`
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
    box-shadow: 0 5px 20px 0 rgba(0, 0, 0, 0.1);
  }
  & .ant-modal-close-icon {
    display: flex;
    height: 100%;
    justify-content: center;
    align-items: center;
    & > svg {
      fill: black;
    }
  }
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  background: #f8fafb;
  height: 56px;
  padding: 0 15px;
  border-radius: 4px 4px 0 0;
  font-size: 15px;
  font-weight: bold;
  color: #222b45;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
`;

const ContentContainer = styled.div`
  padding: 16px 60px 24px;
`;

const FooterContainer = styled.div`
  margin-top: 48px;
  display: flex;
  justify-content: flex-end;

  & > :not(:last-child) {
    margin-right: 16px;
  }
`;

const PathItemContainer = styled.div`
  margin-top: 26px;
  display: flex;
  flex-direction: column;
  font-size: 15px;
  color: #222b45;

  & > :nth-child(2) {
    display: flex;
    margin-top: 12px;
  }
`;

const StyleInput = styled(Input)`
  font-size: 16px !important;
  border-radius: 8px !important;
  background-color: rgba(228, 233, 242, 0.24) !important;
`;

//#endregion

function VixWinPathSettingModal(props) {
  const { visible, changeModalVisible, preImagePath, preApplicationPath, settings } = props;

  const [newImagePath, setNewImagePath] = useState(undefined);
  const [newApplicationPath, setNewApplicationPath] = useState(undefined);

  const defaultImagePath = preImagePath;

  const defaultApplicationPath = preApplicationPath;

  const onCancel = () => {
    changeModalVisible(false);
  };

  const onOk = async () => {
    try {
      if (settings?.id) {
        const imagePath = newImagePath ?? preImagePath ?? '';
        const applicationPath = newApplicationPath ?? preApplicationPath ?? '';
        const vixwin = { imagePath, applicationPath };
        const preXrayVendorSettingWeb = settings?.preferences?.generalSetting?.xrayVenderSettingWeb ?? {};
        const xRayVendorSettingWeb = { ...preXrayVendorSettingWeb, vixwin };
        const generalSetting = { ...(settings?.preferences?.generalSetting ?? {}), xRayVendorSettingWeb };
        const preferences = { ...(settings?.preferences ?? {}), generalSetting };
        const newSettings = { ...(settings ?? {}), preferences };
        await Settings.put(newSettings);
        getSettings();
        message.success('VixWin 路徑設定成功');
        changeModalVisible(false);
      }
    } catch (error) {
      console.log(error);
      message.error('VixWin 路徑設定失敗');
    }
  };

  return (
    <StyledModal
      visible={visible}
      centered
      closable
      bodyStyle={{ padding: '0' }}
      onCancel={onCancel}
      footer={null}
      destroyOnClose
    >
      <HeaderContainer>
        <span>設定 VixWin 路徑</span>
      </HeaderContainer>
      <ContentContainer>
        <div>
          <PathItemContainer>
            <span>軟體路徑</span>
            <div>
              <StyleInput
                type="text"
                placeholder="請貼上路徑"
                defaultValue={defaultImagePath}
                value={newImagePath}
                onChange={e => {
                  setNewImagePath(e.target.value);
                }}
              />
            </div>
          </PathItemContainer>
          <PathItemContainer>
            <span>影像路徑</span>
            <div>
              <StyleInput
                type="text"
                placeholder="請貼上路徑"
                defaultValue={defaultApplicationPath}
                value={newApplicationPath}
                onChange={e => {
                  setNewApplicationPath(e.target.value);
                }}
              />
            </div>
          </PathItemContainer>
        </div>
        <FooterContainer>
          <Button onClick={onCancel} shape="round" size="large" type="text">
            取消
          </Button>
          <Button onClick={onOk} shape="round" type="primary" size="large">
            確認
          </Button>
        </FooterContainer>
      </ContentContainer>
    </StyledModal>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  preImagePath: homePageReducer.settings.settings?.preferences?.generalSetting?.xRayVendorSettingWeb?.vixwin?.imagePath,
  preApplicationPath:
    homePageReducer.settings.settings?.preferences?.generalSetting?.xRayVendorSettingWeb?.vixwin.applicationPath,
  settings: homePageReducer.settings.settings,
});

const mapDispatchToProps = { getSettings };

export default connect(mapStateToProps, mapDispatchToProps)(VixWinPathSettingModal);
