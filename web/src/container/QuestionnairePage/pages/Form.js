import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, nextPage } from '../actions';
import { Button } from 'antd';
import { parseDataToDisplay } from '../utils/parseDataToDisplay';
import { StyleRightCircleTwoTone } from './Address';
import { checkBirthValidation } from './Birth';
import { checkSmokingAmountValidation } from './SmokingA';
import { checkPregnantDateValidation } from './PregnantA';
import { checkDrugValidation } from './DoDrugA';

//#region

const Container = styled.div`
  max-width: 600px;
  width: 95%;
  max-height: 85vh;
  font-size: 20px;
  overflow-y: scroll;
  > div:nth-child(2) {
    > span {
      font-size: 14px;
      line-height: 1.29;
      color: #828991;
    }
  }
`;

const FormContainer = styled.div``;

const Label = styled.p`
  color: #989ea6;
  font-size: 16px;
  margin: 20px 0;
`;

const InfoContainer = styled.div`
  & > div:nth-child(2n + 1) {
    background: #f5f5f5;
  }

  & > div:nth-child(2n) {
    background: #fff;
  }

  & > div {
    height: 45px;
  }

  color: #000;
  font-size: 14px;
`;

const InfoRowContainer = styled.div`
  display: flex;
  align-items: center;
  & > div {
    width: 33.3%;
    height: 100%;
    padding: 0.5em 1em;
    display: flex;
    align-items: center;
    &:hover,
    &:focus {
      border-radius: 8px;
      border: solid 2px #3266ff;
      cursor: pointer;
    }
  }
`;

const InfoLabel = styled.span`
  margin-right: 10px;
`;

const Info = styled.span`
  font-weight: 600;
`;

const FlexGrowDiv = styled.div`
  flex-grow: 1;
`;

const ButtonsContainer = styled.div`
  display: flex;
  justify-content: flex-end;
`;

const StyledButton = styled(Button)`
  margin: 10px;
  border-radius: 4px !important;
`;
//#endregion

function Form(props) {
  const { patient, displayData } = props;

  const disabled =
    !patient.name ||
    patient.name.length === 0 ||
    !patient.phone ||
    patient.phone.length === 0 ||
    checkBirthValidation(patient.birth) ||
    (patient.smoking === 'A' && checkSmokingAmountValidation(patient.smokingAmount)) ||
    (patient.pregnant === 'A' && checkPregnantDateValidation(patient.pregnantDate)) ||
    (patient.doDrug === 'A' && checkDrugValidation(patient.drug));

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>資料確認</span>
      </div>
      <div>
        <span>&#x26B9;直接點擊表格編輯內容</span>
      </div>
      <FormContainer>
        <Label>基本資訊</Label>
        <InfoContainer>
          <InfoRowContainer>
            <div onClick={() => props.gotoPage(1)}>
              <InfoLabel>姓名:</InfoLabel>
              <Info>{displayData.name}</Info>
            </div>
            <div onClick={() => props.gotoPage(2)}>
              <InfoLabel>生日:</InfoLabel>
              <Info>{displayData.birth}</Info>
            </div>
            <div onClick={() => props.gotoPage(4)}>
              <InfoLabel>身分證字號:</InfoLabel>
              <Info>{displayData.nationalId}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <div onClick={() => props.gotoPage(3)}>
              <InfoLabel>性別:</InfoLabel>
              <Info>{displayData.gender}</Info>
            </div>
            <div onClick={() => props.gotoPage(5)}>
              <InfoLabel>血型:</InfoLabel>
              <Info>{displayData.bloodType}</Info>
            </div>
            <div onClick={() => props.gotoPage(6)}>
              <InfoLabel>電話:</InfoLabel>
              <Info>{displayData.phone}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <FlexGrowDiv onClick={() => props.gotoPage(7)}>
              <InfoLabel>地址:</InfoLabel>
              <Info>{displayData.address}</Info>
            </FlexGrowDiv>
            <div onClick={() => props.gotoPage(8)}>
              <InfoLabel>職業:</InfoLabel>
              <Info>{displayData.career}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <div onClick={() => props.gotoPage(9)}>
              <InfoLabel>婚姻:</InfoLabel>
              <Info>{displayData.marriage}</Info>
            </div>
            <div onClick={() => props.gotoPage(10)}>
              <InfoLabel>介紹人:</InfoLabel>
              <Info>{displayData.introducer}</Info>
            </div>
          </InfoRowContainer>
        </InfoContainer>
        <Label>緊急聯絡</Label>
        <InfoContainer>
          <InfoRowContainer>
            <div onClick={() => props.gotoPage(11)}>
              <InfoLabel>姓名:</InfoLabel>
              <Info>{displayData.emergencyContactName}</Info>
            </div>
            <div onClick={() => props.gotoPage(12)}>
              <InfoLabel>電話:</InfoLabel>
              <Info>{displayData.emergencyContactPhone}</Info>
            </div>
            <div onClick={() => props.gotoPage(13)}>
              <InfoLabel>關係:</InfoLabel>
              <Info>{displayData.emergencyContactRelationship}</Info>
            </div>
          </InfoRowContainer>
        </InfoContainer>
        <Label>疾病史</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv onClick={() => props.gotoPage(14)}>
              <Info>{displayData.disease}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>血液疾病史</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv onClick={() => props.gotoPage(15)}>
              <Info>{displayData.bloodDisease}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>藥物</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv onClick={() => props.gotoPage(17)}>
              <InfoLabel>正在服用:</InfoLabel>
              <Info>{displayData.drug}</Info>
            </FlexGrowDiv>
            <FlexGrowDiv onClick={() => props.gotoPage(16)}>
              <InfoLabel>過敏:</InfoLabel>
              <Info>{displayData.allergy}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>特殊</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv onClick={() => props.gotoPage(19)}>
              <InfoLabel>懷孕狀況:</InfoLabel>
              <Info>{displayData.pregnant}</Info>
            </FlexGrowDiv>
            <FlexGrowDiv onClick={() => props.gotoPage(18)}>
              <InfoLabel>吸菸狀況:</InfoLabel>
              <Info>{displayData.smoking}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>治療困難</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv onClick={() => props.gotoPage(20)}>
              <Info>{displayData.other}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
      </FormContainer>
      <ButtonsContainer>
        <StyledButton
          onClick={() => {
            props.gotoPage(1);
          }}
        >
          返回編輯
        </StyledButton>
        <StyledButton
          type="primary"
          disabled={disabled}
          onClick={() => {
            props.gotoPage(22);
          }}
        >
          確認進行數位簽章
        </StyledButton>
      </ButtonsContainer>
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  displayData: parseDataToDisplay(questionnairePageReducer.data.patient),
});

const mapDispatchToProps = { gotoPage, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Form);
