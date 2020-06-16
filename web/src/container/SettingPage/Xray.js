import React, { useState } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Switch, message } from 'antd';
import Poye from '../../images/POYE@4x.png';
import Ray from '../../images/Ray@4x.png';
import { XRAY_VENDORS } from '../AppointmentPage/constant';
import Settings from '../../models/settings';
import { getSettings } from '../Home/actions';
import VixWinPathSettingModal from './VixWinPathSettingModal';

//#region
const Title = styled.span`
  font-size: 22px;
`;

const SettingItemsContainer = styled.div`
  margin-top: 10px;
  & > div {
    height: 90px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    & > div:first-child {
      display: flex;
      align-items: center;
      font-size: 18px;
      font-weight: 500;
      color: #4d4d4d;
      & > :first-child {
        margin-right: 25px;
      }
    }
  }
`;

const IconContainer = styled.div`
  width: 33px;
  height: 33px;
  background-color: #f2f2f2;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  & > img {
    width: 19px;
    height: 19px;
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
      const prevXrayVendorWeb = settings?.preferences?.generalSetting?.xrayVenderWeb ?? [];
      const isDeactivate = prevXrayVendorWeb.includes(vendor);
      const xrayVenderWeb = isDeactivate ? prevXrayVendorWeb.filter(v => v !== vendor) : [...prevXrayVendorWeb, vendor];
      const generalSetting = { ...(settings?.preferences?.generalSetting ?? {}), xrayVenderWeb };
      const preferences = { ...(settings?.preferences ?? {}), generalSetting };
      const newSettings = { ...(settings ?? {}), preferences };
      await Settings.put(newSettings);
      getSettings();
      if (isDeactivate) {
        message.success('X 光已關閉');
      } else {
        message.success('X 光開啟成功');
      }
    }
  };

  return (
    <React.Fragment>
      <VixWinPathSettingModal
        visible={vixWinPathSettingModalVisible}
        changeModalVisible={setVixWinPathSettingModalVisible}
      />
      <Title>串接 X 光機</Title>
      <SettingItemsContainer>
        <div>
          <div>
            <IconContainer>
              <img src={Poye} alt="Poye" />
            </IconContainer>
            <span>ExamVision</span>
            <SettingButton
              onClick={() => {
                setVixWinPathSettingModalVisible(true);
              }}
            >
              設定路徑
            </SettingButton>
          </div>
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
        </div>
        <div>
          <div>
            <IconContainer>
              <img src={Ray} alt="Ray" />
            </IconContainer>
            <span>VixWin</span>
          </div>
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
        </div>
      </SettingItemsContainer>
    </React.Fragment>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  xrayVender: homePageReducer.settings.settings?.preferences?.generalSetting?.xrayVenderWeb ?? [],
  settings: homePageReducer.settings.settings,
});

const mapDispatchToProps = { getSettings };

export default connect(mapStateToProps, mapDispatchToProps)(Xray);
