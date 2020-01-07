import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Icon } from 'antd';
import { Swipeable } from 'react-swipeable';
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
  initQuestionnaire,
  getPatient,
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
  font-size: 20px;
  & > div {
    border: 1px solid rgb(208, 215, 223);
    box-shadow: 0px 2px 9px 0px rgba(23, 104, 172, 0.13);
    width: 35px;
    height: 35px;
    margin: 0 10px;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
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
    match,
    getPatient,
  } = props;

  // TODO: Xu you can continue from here

  if (location.state) {
    const patient = location.state.patient;
    if (patient) {
      props.initQuestionnaire(patient);
    }
  }

  useEffect(() => {
    function keyFunction(keyEvent) {
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

    document.addEventListener('keydown', keyFunction, false);

    const pid = match.params.pid;
    getPatient(pid);

    return () => {
      document.removeEventListener('keydown', keyFunction, false);
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
    match,
    getPatient,
  ]);

  return (
    <Swipeable onSwipedUp={nextPage} onSwipedDown={prevPage}>
      <Container>
        {props.page !== 20 && props.page !== 21 && <QutContent />}
        {props.page === 20 && <Form />}
        {props.page === 21 && <Signature />}
        {props.page !== 20 && props.page !== 21 && (
          <PageControlContainer>
            <div onClick={prevPage}>
              <Icon type="up" />
            </div>
            <div onClick={nextPage}>
              <Icon type="down" />
            </div>
          </PageControlContainer>
        )}
      </Container>
    </Swipeable>
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
  initQuestionnaire,
  getPatient,
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(QuestionnairePage));
