import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { message } from 'antd';
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
  preChangeSmoking,
  changeOther,
  initQuestionnaire,
  getPatient,
  initPage,
} from './actions';
import './index.css';
import QutContent from './QutContent';
import Form from './pages/Form';
import Signature from './pages/Signature';
import { handleKeyEvent } from './utils/handleKeyEvent';
import { withRouter } from 'react-router-dom';
import Background from '../../images/questionnaire_bg.svg';
import { GApageView } from '../../ga';

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

function QuestionnairePage(props) {
  const {
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
    preChangeSmoking,
    changeOther,
    match,
    getPatient,
    createQSuccess,
    initPage,
  } = props;

  useEffect(() => {
    GApageView();
  }, []);

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
        preChangeSmoking,
        changeOther,
      });
    }

    document.addEventListener('keydown', keyFunction, false);

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
    preChangeSmoking,
    preChangeEmergencyRelationship,
    changeDisease,
    changeAllergy,
    preChangeDoDrug,
    preChangePregnant,
    changeOther,
  ]);

  useEffect(() => {
    const pid = match.params.pid;
    getPatient(pid);
  }, [match.params.pid, getPatient]);

  useEffect(() => {
    if (createQSuccess) {
      message.success('新增初診單成功');
      props.history.replace('/registration');
    }

    return () => {
      initPage();
    };
  }, [createQSuccess, initPage, props.history]);

  return (
    <Swipeable onSwipedUp={nextPage} onSwipedDown={prevPage}>
      <Container>
        {props.page !== 20 && props.page !== 21 && <QutContent />}
        {props.page === 20 && <Form />}
        {props.page === 21 && <Signature />}
      </Container>
    </Swipeable>
  );
}

const mapStateToProps = state => ({
  page: state.questionnairePageReducer.flow.page,
  createQSuccess: state.questionnairePageReducer.flow.createQSuccess,
});

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
  preChangeSmoking,
  changeOther,
  initQuestionnaire,
  getPatient,
  initPage,
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(QuestionnairePage));
