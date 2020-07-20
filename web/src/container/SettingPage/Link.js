import React, { useState, Fragment, useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Button, Tooltip, Input } from 'antd';
import { getSettings } from '../Home/actions';
import Pantone from '../../images/pantone.svg';
import Cube from '../../images/cube.svg';
import { linkManagmentPrefix } from '../../models/configuration';
import { setConfigs } from './actions';

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

function Link({ linkManagement, setConfigs, putSuccess }) {
  const [settingMode, setSettingMode] = useState(false);
  const [technicianSheet, setTechnicianSheet] = useState(undefined);
  const [toothMaterialSheet, setToothMaterialSheet] = useState(undefined);
  const [errorLinks, setErrorLinks] = useState([]);

  useEffect(() => {
    if (putSuccess) {
      setSettingMode(false);
    }
  }, [putSuccess]);

  const onSave = () => {
    if (errorLinks.length > 0) return;

    const technicianSheetConfig = {
      name: 'technicianSheet',
      configKey: `${linkManagmentPrefix}.technicianSheet`,
      configValue: technicianSheet,
    };
    const toothMaterialSheetConfig = {
      name: 'toothMaterialSheet',
      configKey: `${linkManagmentPrefix}.toothMaterialSheet`,
      configValue: toothMaterialSheet,
    };

    let update = [];
    let create = [];

    const items = [technicianSheetConfig, toothMaterialSheetConfig];
    items
      .filter(i => i.configValue !== undefined)
      .forEach(i => {
        const item = { configKey: i.configKey, configValue: i.configValue };
        if (Object.keys(linkManagement).includes(i.name)) {
          update = [...update, item];
        } else {
          create = [...create, item];
        }
      });

    setConfigs({ update, create });
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

const mapStateToProps = ({ settingPageReducer }) => ({
  linkManagement: settingPageReducer.configurations.config.linkManagement,
  putSuccess: settingPageReducer.configurations.putSuccess,
});

const mapDispatchToProps = { getSettings, setConfigs };

export default connect(mapStateToProps, mapDispatchToProps)(Link);
