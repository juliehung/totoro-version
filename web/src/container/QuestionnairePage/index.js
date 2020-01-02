import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Icon } from 'antd';
import {
  nextPage,
  prevPage,
  preChangeGender,
  preChangeBloodType,
  preChangeCareer,
  preChangeMarriage,
  preChangeEmergencyRelationship,
  changeDisease,
  changeAllergy,
  preChangeDoDrug,
  preChangePregnant,
} from './actions';
import './index.css';
import QutContent from './QutContent';
import Form from './pages/Form';
import Signature from './pages/Signature';
import { handleKeyEvent } from './utils/handleKeyEvent';
import { withRouter } from 'react-router-dom';
import Background from '../../images/questionnaire_bg.svg';

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

const PageControlContainer = styled.div`
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  width: 600px;
  font-size: 30px;
  & > * {
    margin: 0 10px;
    cursor: pointer;
  }
`;

function QuestionnairePage(props) {
  const {
    location,
    nextPage,
    prevPage,
    page,
    preChangeGender,
    preChangeBloodType,
    preChangeCareer,
    preChangeMarriage,
    preChangeEmergencyRelationship,
    changeDisease,
    changeAllergy,
    preChangeDoDrug,
    preChangePregnant,
  } = props;

  // TODO: Xu you can continue from here
  if (location.state) {
    console.log(location.state.patient);
  }

  useEffect(() => {
    function escFunction(keyEvent) {
      handleKeyEvent(page, keyEvent, {
        prevPage,
        nextPage,
        preChangeGender,
        preChangeBloodType,
        preChangeCareer,
        preChangeMarriage,
        preChangeEmergencyRelationship,
        changeDisease,
        changeAllergy,
        preChangeDoDrug,
        preChangePregnant,
      });
    }

    document.addEventListener('keydown', escFunction, false);

    return () => {
      document.removeEventListener('keydown', escFunction, false);
    };
  }, [
    nextPage,
    prevPage,
    page,
    preChangeGender,
    preChangeBloodType,
    preChangeCareer,
    preChangeMarriage,
    preChangeEmergencyRelationship,
    changeDisease,
    changeAllergy,
    preChangeDoDrug,
    preChangePregnant,
  ]);

  return (
    <Container>
      {props.page !== 20 && props.page !== 21 && <QutContent />}
      {props.page === 20 && <Form />}
      {props.page === 21 && <Signature />}
      {props.page !== 20 && props.page !== 21 && (
        <PageControlContainer>
          <Icon type="up" onClick={prevPage} />
          <Icon type="down" onClick={nextPage} />
        </PageControlContainer>
      )}
    </Container>
  );
}

const mapStateToProps = state => ({ page: state.questionnairePageReducer.flow.page });

const mapDispatchToProps = {
  nextPage,
  prevPage,
  preChangeGender,
  preChangeBloodType,
  preChangeCareer,
  preChangeMarriage,
  preChangeEmergencyRelationship,
  changeDisease,
  changeAllergy,
  preChangeDoDrug,
  preChangePregnant,
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(QuestionnairePage));
