import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, nextPage } from '../actions';
import { Button } from 'antd';
import { parseDataToDisplay } from '../utils/parseDataToDisplay';
import { StyleRightCircleTwoTone } from './Address';

//#region

const Container = styled.div`
  max-width: 600px;
  width: 95%;
  max-height: 85vh;
  font-size: 20px;
  overflow-y: scroll;
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
    padding: 0.5em 1em;
    min-height: 36px;
  }

  color: #000;
  font-size: 14px;
`;

const InfoRowContainer = styled.div`
  display: flex;
  align-items: center;
  & > div {
    width: 33.3%;
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
`;
//#endregion

function Form(props) {
  const displayData = parseDataToDisplay(props.patient);

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>資料確認</span>
      </div>
      <FormContainer>
        <Label>基本資訊</Label>
        <InfoContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>姓名:</InfoLabel>
              <Info>{displayData.name}</Info>
            </div>
            <div>
              <InfoLabel>生日:</InfoLabel>
              <Info>{displayData.birth}</Info>
            </div>
            <div>
              <InfoLabel>身分證字號:</InfoLabel>
              <Info>{displayData.nationalId}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>性別:</InfoLabel>
              <Info>{displayData.gender}</Info>
            </div>
            <div>
              <InfoLabel>血型:</InfoLabel>
              <Info>{displayData.bloodType}</Info>
            </div>
            <div>
              <InfoLabel>電話:</InfoLabel>
              <Info>{displayData.phone}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <InfoLabel>地址:</InfoLabel>
              <Info>{displayData.address}</Info>
            </FlexGrowDiv>
            <div>
              <InfoLabel>職業:</InfoLabel>
              <Info>{displayData.career}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>婚姻:</InfoLabel>
              <Info>{displayData.marriage}</Info>
            </div>
            <div>
              <InfoLabel>介紹人:</InfoLabel>
              <Info>{displayData.introducer}</Info>
            </div>
          </InfoRowContainer>
        </InfoContainer>
        <Label>緊急聯絡</Label>
        <InfoContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>姓名:</InfoLabel>
              <Info>{displayData.emergencyContactName}</Info>
            </div>
            <div>
              <InfoLabel>電話:</InfoLabel>
              <Info>{displayData.emergencyContactPhone}</Info>
            </div>
            <div>
              <InfoLabel>關係:</InfoLabel>
              <Info>{displayData.emergencyContactRelationship}</Info>
            </div>
          </InfoRowContainer>
        </InfoContainer>
        <Label>疾病史</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <Info>{displayData.disease}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>藥物</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <InfoLabel>正在服用:</InfoLabel>
              <Info>{displayData.drug}</Info>
            </FlexGrowDiv>
            <FlexGrowDiv>
              <InfoLabel>過敏:</InfoLabel>
              <Info>{displayData.allergy}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>特殊</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <Info>{displayData.special}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>治療困難</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
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
          disabled={
            !displayData.name || displayData.name.length === 0 || !displayData.phone || displayData.phone.length === 0
          }
          onClick={() => {
            props.gotoPage(21);
          }}
        >
          確認進行數位簽章
        </StyledButton>
      </ButtonsContainer>
    </Container>
  );
}

const mapStateToProps = state => ({
  patient: state.questionnairePageReducer.data.patient,
});

const mapDispatchToProps = { gotoPage, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Form);
