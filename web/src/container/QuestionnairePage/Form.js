import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import Background from '../../images/questionnaire_bg.svg';
import { withRouter, Link } from 'react-router-dom';
import { getDoc } from './actions';
import { Button } from 'antd';

//#region

const Container = styled.div`
  position: fixed;
  height: 100%;
  width: 100vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url(${Background});
  background-repeat: no-repeat;
  background-position: bottom;
  background-size: contain;
`;

const GoBackButton = styled(Button)`
  margin: 20px 0;
`;

const FormContainer = styled.div`
  max-width: 600px;
  width: 95%;
  max-height: 85vh;
  font-size: 20px;
  overflow-y: scroll;
`;

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

  color: #000;
  font-size: 14px;
`;

const InfoRowContainer = styled.div`
  display: flex;
  height: 36px;
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

const ImageContainer = styled.div`
  margin-top: 10px;
  border: 2px solid #eee;
`;
//#endregion

function Form(props) {
  const { match, getDoc } = props;

  useEffect(() => {
    const id = match.params.id;
    getDoc(id);
  }, [match.params.id, getDoc]);

  return (
    <Container>
      <FormContainer>
        <Label>基本資訊</Label>
        <InfoContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>姓名:</InfoLabel>
              <Info>{props.patient.name}</Info>
            </div>
            <div>
              <InfoLabel>生日:</InfoLabel>
              <Info>{props.patient.birth}</Info>
            </div>
            <div>
              <InfoLabel>身分證字號:</InfoLabel>
              <Info>{props.patient.nationalId}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>性別:</InfoLabel>
              <Info>{props.patient.gender}</Info>
            </div>
            <div>
              <InfoLabel>血型:</InfoLabel>
              <Info>{props.patient.bloodType}</Info>
            </div>
            <div>
              <InfoLabel>電話:</InfoLabel>
              <Info>{props.patient.phone}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <InfoLabel>地址:</InfoLabel>
              <Info>{props.patient.address}</Info>
            </FlexGrowDiv>
            <div>
              <InfoLabel>職業:</InfoLabel>
              <Info>{props.patient.career}</Info>
            </div>
          </InfoRowContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>婚姻:</InfoLabel>
              <Info>{props.patient.marriage}</Info>
            </div>
            <div>
              <InfoLabel>介紹人:</InfoLabel>
              <Info>{props.patient.introducer}</Info>
            </div>
          </InfoRowContainer>
        </InfoContainer>
        <Label>緊急聯絡</Label>
        <InfoContainer>
          <InfoRowContainer>
            <div>
              <InfoLabel>姓名:</InfoLabel>
              <Info>{props.patient.emergencyName}</Info>
            </div>
            <div>
              <InfoLabel>電話:</InfoLabel>
              <Info>{props.patient.emergencyPhone}</Info>
            </div>
            <div>
              <InfoLabel>關係:</InfoLabel>
              <Info>{props.patient.emergencyRelationship}</Info>
            </div>
          </InfoRowContainer>
        </InfoContainer>
        <Label>疾病史</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <Info>{props.patient.disease}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>藥物</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <InfoLabel>正在服用:</InfoLabel>
              <Info>{props.patient.drugName}</Info>
            </FlexGrowDiv>
            <FlexGrowDiv>
              <InfoLabel>過敏:</InfoLabel>
              <Info>{props.patient.allergy}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>特殊</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <Info>{props.patient.special}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        <Label>治療困難</Label>
        <InfoContainer>
          <InfoRowContainer>
            <FlexGrowDiv>
              <Info>{props.patient.other}</Info>
            </FlexGrowDiv>
          </InfoRowContainer>
        </InfoContainer>
        {props.esign && (
          <ImageContainer>
            <img src={props.esign} alt="esign" width="100%"></img>
          </ImageContainer>
        )}
      </FormContainer>
      <Link to="/registration">
        <GoBackButton type="primary">返回列表</GoBackButton>
      </Link>
    </Container>
  );
}

const mapStateToProps = state => ({
  patient: state.questionnairePageReducer.form.patient,
  esign: state.questionnairePageReducer.form.esign,
});

const mapDispatchToProps = { getDoc };

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Form));
