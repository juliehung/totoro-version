import React, { useState } from 'react';
import { connect } from 'react-redux';
import { Modal, Input, message } from 'antd';
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
  font-size: 18px;
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

const Button = styled.button`
  font-size: 14px;
  height: 40px;
  border: 0;
  outline: 0;
  padding-right: 20px !important;
  padding-left: 20px !important;
  border-radius: 34px !important;
  cursor: pointer;
  transition: box-shadow 200ms ease;
  font-weight: bold;
`;

const StyledButton = styled(Button)`
  background-color: #3266ff;
  color: #ffffff;
  &:hover {
    background-color: #3266ff;
    box-shadow: 0 8px 12px -8px rgba(50, 102, 255, 0.3);
  }
  &:active {
    background-color: #244edb;
  }
`;

const NoStyleButton = styled(Button)`
  background-color: #fff;
  color: #8f9bb3;
  &:hover {
    background-color: rgba(143, 155, 179, 0.08);
  }
  &:active {
    background-color: rgba(143, 155, 179, 0.16);
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
    & input {
      padding: 0 10px;
      flex: 1;
      height: 40px;
      border-radius: 2px;
    }
  }

  & input {
    border: 1px solid #ccc;
  }
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
              <Input
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
              <Input
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
          <NoStyleButton onClick={onCancel}>取消</NoStyleButton>
          <StyledButton onClick={onOk}>確認</StyledButton>
        </FooterContainer>
      </ContentContainer>
    </StyledModal>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  preImagePath: homePageReducer.settings.settings?.preferences?.generalSetting?.xrayVenderSettingWeb?.vixwin?.imagePath,
  preApplicationPath:
    homePageReducer.settings.settings?.preferences?.generalSetting?.xrayVenderSettingWeb?.vixwin.applicationPath,
  settings: homePageReducer.settings.settings,
});

const mapDispatchToProps = { getSettings };

export default connect(mapStateToProps, mapDispatchToProps)(VixWinPathSettingModal);
