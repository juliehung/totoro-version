import React, { useState, Fragment } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Switch, message } from 'antd';
import { XRAY_VENDORS } from '../AppointmentPage/constant';
import Settings from '../../models/settings';
import { getSettings } from '../Home/actions';
import VixWinPathSettingModal from './VixWinPathSettingModal';
import VisionImg from '../../component/VisionImg';
import VixWinImg from '../../component/VixWinImg';

//#region
const Title = styled.span`
  font-size: 22px;
`;

const SettingItemContainer = styled.div`
  height: 90px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
`;

const TitleContainer = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
  color: #222b45;
  font-weight: 600;
  & > :first-child {
    margin-right: 25px;
  }
`;

const SettingButton = styled.a`
  margin-left: 27px;
  font-size: 12px;
`;
//#endregion

function Xray({ settings, getSettings, xrayVender }) {
  const [vixWinPathSettingModalVisible, setVixWinPathSettingModalVisible] = useState(false);

  const onChange = async vendor => {
    if (settings?.id) {
      const prevXrayVendorWeb = xrayVender ?? [];
      const isDeactivate = prevXrayVendorWeb.includes(vendor);
      const xRayVendorWeb = isDeactivate ? prevXrayVendorWeb.filter(v => v !== vendor) : [...prevXrayVendorWeb, vendor];
      const generalSetting = { ...(settings?.preferences?.generalSetting ?? {}), xRayVendorWeb };
      const preferences = { ...(settings?.preferences ?? {}), generalSetting };
      const newSettings = { ...(settings ?? {}), preferences };
      await Settings.put(newSettings);
      getSettings();
      if (isDeactivate) {
        message.warning('X 光已關閉');
      } else {
        message.success('X 光開啟成功');
      }
    }
  };

  return (
    <Fragment>
      <VixWinPathSettingModal
        visible={vixWinPathSettingModalVisible}
        changeModalVisible={setVixWinPathSettingModalVisible}
      />
      <div>
        <Title>串接 X 光機</Title>
        <SettingItemContainer>
          <TitleContainer>
            <VisionImg width={33} />
            <span>eXamVision</span>
          </TitleContainer>
          <div>
            <Switch
              checked={xrayVender.includes(XRAY_VENDORS.vision)}
              checkedChildren="開啟"
              unCheckedChildren="關閉"
              onChange={() => {
                onChange(XRAY_VENDORS.vision);
              }}
            />
          </div>
        </SettingItemContainer>
        <SettingItemContainer>
          <TitleContainer>
            <VixWinImg width={33} />
            <span>VixWin</span>
            <SettingButton
              onClick={() => {
                setVixWinPathSettingModalVisible(true);
              }}
            >
              設定路徑
            </SettingButton>
          </TitleContainer>
          <div>
            <Switch
              checked={xrayVender.includes(XRAY_VENDORS.vixwin)}
              checkedChildren="開啟"
              unCheckedChildren="關閉"
              onChange={() => {
                onChange(XRAY_VENDORS.vixwin);
              }}
            />
          </div>
        </SettingItemContainer>
      </div>
    </Fragment>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  xrayVender: homePageReducer.settings.settings?.preferences?.generalSetting?.xRayVendorWeb ?? [],
  settings: homePageReducer.settings.settings,
});

const mapDispatchToProps = { getSettings };

export default connect(mapStateToProps, mapDispatchToProps)(Xray);
