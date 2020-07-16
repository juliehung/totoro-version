import React, { useState, Fragment } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Switch } from 'antd';
import { XRAY_VENDORS } from '../AppointmentPage/constant';
import { setConfigs } from './actions';
import VixWinPathSettingModal from './VixWinPathSettingModal';
import VisionImg from '../../component/VisionImg';
import VixWinImg from '../../component/VixWinImg';
import { xRayVendorPrefix } from '../../models/configuration';

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

function Xray(props) {
  const { xRayVendors, setConfigs } = props;

  const [vixWinPathSettingModalVisible, setVixWinPathSettingModalVisible] = useState(false);
  const onChange = vendor => {
    const configKey = `${xRayVendorPrefix}.${vendor}`;
    const configValue = !(xRayVendors?.[vendor] === 'true');
    let update = [];
    let create = [];
    if (Object.keys(xRayVendors).includes(vendor)) {
      update = [...update, { configKey, configValue }];
    } else {
      create = [...create, { configKey, configValue }];
    }
    setConfigs({ update, create });
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
              checked={xRayVendors?.[XRAY_VENDORS.vision] === 'true'}
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
              checked={xRayVendors?.[XRAY_VENDORS.vixwin] === 'true'}
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

const mapStateToProps = ({ settingPageReducer }) => ({
  xRayVendors: settingPageReducer.configurations.config.xRayVendors,
});

const mapDispatchToProps = { setConfigs };

export default connect(mapStateToProps, mapDispatchToProps)(Xray);
