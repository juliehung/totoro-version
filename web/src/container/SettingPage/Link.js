import React, { useState, Fragment } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { message, Button, Tooltip, Input } from 'antd';
import Settings from '../../models/settings';
import { getSettings } from '../Home/actions';
import Pantone from '../../images/pantone.svg';
import Cube from '../../images/cube.svg';

//#region
const Title = styled.span`
  font-size: 22px;
`;

const Header = styled.div`
  display: flex;
`;

const SettingItemContainer = styled.div`
  position: relative;
  height: 90px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
`;

const StyledButton = styled(Button)`
  margin-left: 20px;
`;

const ItemContainer = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
  color: #222b45;
  font-weight: 600;
  width: 100%;
  padding: 3px;
  overflow: hidden;
  & > :first-child {
    margin-right: 22px;
    flex-shrink: 0;
  }
  & > :nth-child(2) {
    margin-right: 26px;
    font-weight: 600;
    flex-shrink: 0;
  }
  & > :last-child {
    font-size: 13px;
    color: #8f9bb3;
    max-width: 100%;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    user-select: text;
  }
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  & > span {
    position: absolute;
    bottom: 5px;
    color: #ff4948;
    font-size: 10px;
  }
`;

const StyleInput = styled(Input)`
  font-size: 16px !important;
  border-radius: 8px !important;
  background-color: rgba(228, 233, 242, 0.24) !important;
`;

//#endregion

const checkLinkRegex = new RegExp('^$|^(http|https)://', 'i');

function Link({ settings, getSettings, linkManagement }) {
  const [settingMode, setSettingMode] = useState(false);
  const [technicianSheet, setTechnicianSheet] = useState(undefined);
  const [toothMaterialSheet, setToothMaterialSheet] = useState(undefined);
  const [errorLinks, setErrorLinks] = useState([]);

  const onSave = async () => {
    if (errorLinks.length > 0) return;
    if (settings?.id) {
      try {
        const newLinkManagement = {
          ...linkManagement,
          ...(technicianSheet && { technicianSheet }),
          ...(toothMaterialSheet && { toothMaterialSheet }),
        };
        const generalSetting = { ...(settings?.preferences?.generalSetting ?? {}), linkManagement: newLinkManagement };
        const preferences = { ...(settings?.preferences ?? {}), generalSetting };
        const newSettings = { ...(settings ?? {}), preferences };
        await Settings.put(newSettings);
        getSettings();
        setSettingMode(false);
        message.success('連結更新成功');
      } catch (error) {
        message.error('請在試一次');
      }
    }
  };

  const onToothMaterialSheetChange = e => {
    setToothMaterialSheet(e.target.value);
    const valid = checkLinkRegex.test(e.target.value);
    if (valid) {
      setErrorLinks(prevErrorLinks => prevErrorLinks.filter(p => p !== 'toothMaterialSheet'));
    } else {
      setErrorLinks(prevErrorLinks => [...new Set([...prevErrorLinks, 'toothMaterialSheet'])]);
    }
  };

  const onTechnicianSheetChange = e => {
    setTechnicianSheet(e.target.value);
    const valid = checkLinkRegex.test(e.target.value);
    if (valid) {
      setErrorLinks(prevErrorLinks => prevErrorLinks.filter(p => p !== 'technicianSheet'));
    } else {
      setErrorLinks(prevErrorLinks => [...new Set([...prevErrorLinks, 'technicianSheet'])]);
    }
  };

  return (
    <Fragment>
      <Header>
        <Title>指定連結管理</Title>
        {settingMode ? (
          <StyledButton shape="round" type="primary" onClick={onSave} disabled={errorLinks.length > 0}>
            儲存
          </StyledButton>
        ) : (
          <StyledButton shape="round" onClick={() => setSettingMode(true)}>
            編輯
          </StyledButton>
        )}
      </Header>
      <SettingItemContainer>
        <ItemContainer>
          <img src={Pantone} width="24" alt="Pantone" />
          <span>技工管理表</span>
          {settingMode ? (
            <InputContainer>
              <StyleInput
                placeholder="貼入網址"
                size="large"
                onChange={onTechnicianSheetChange}
                defaultValue={linkManagement.technicianSheet}
              />
              {errorLinks.includes('technicianSheet') && <span>請輸入正確的網址</span>}
            </InputContainer>
          ) : (
            <Tooltip placement="bottom" title={linkManagement.technicianSheet} arrowPointAtCenter>
              <span>{linkManagement.technicianSheet} </span>
            </Tooltip>
          )}
        </ItemContainer>
      </SettingItemContainer>
      <SettingItemContainer>
        <ItemContainer>
          <img src={Cube} width="24" alt="Cube" />
          <span>牙材管理表</span>
          {settingMode ? (
            <InputContainer>
              <StyleInput
                placeholder="貼入網址"
                size="large"
                onChange={onToothMaterialSheetChange}
                defaultValue={linkManagement.toothMaterialSheet}
              />
              {errorLinks.includes('toothMaterialSheet') && <span>請輸入正確的網址</span>}
            </InputContainer>
          ) : (
            <Tooltip placement="bottom" title={linkManagement.toothMaterialSheet} arrowPointAtCenter>
              <span>{linkManagement.toothMaterialSheet} </span>
            </Tooltip>
          )}
        </ItemContainer>
      </SettingItemContainer>
    </Fragment>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  linkManagement: homePageReducer.settings.settings?.preferences?.generalSetting?.linkManagement ?? {},
  settings: homePageReducer.settings.settings,
});

const mapDispatchToProps = { getSettings };

export default connect(mapStateToProps, mapDispatchToProps)(Link);
