import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { Switch } from 'antd';
import Settings from '../../models/settings';
import { getSettings } from '../Home/actions';

//#region
const Container = styled.div`
  padding: 30px;
`;

const Item = styled.div`
  & > span {
    margin-right: 10px;
  }
`;
//#endregion

function SettingPage({ generalSetting, settings, getSettings }) {
  const shift = generalSetting && generalSetting.showShift;
  const onChange = async () => {
    const showShift = !shift;
    if (settings.id) {
      const generalSetting = { ...{ ...(settings.preferences ? settings.preferences.generalSetting : {}) }, showShift };
      const preferences = { ...settings.preferences, generalSetting };
      const newSettings = { ...settings, preferences };
      await Settings.put(newSettings);
      getSettings();
    }
  };
  return (
    <Container>
      <Helmet>
        <title>設定</title>
      </Helmet>
      <Item>
        <span>排班</span>
        <Switch onChange={onChange} checked={shift} />
      </Item>
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  generalSetting: homePageReducer.settings.generalSetting,
  settings: homePageReducer.settings.settings,
});

const mapDispatchToProps = { getSettings };

export default connect(mapStateToProps, mapDispatchToProps)(SettingPage);
