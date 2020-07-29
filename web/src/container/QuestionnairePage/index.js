import React, { useEffect, useRef } from 'react';
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
  validateSuccess,
  valitationFail,
  gotoPage,
} from './actions';
import './index.css';
import QutContent from './QutContent';
import Form from './pages/Form';
import Signature from './pages/Signature';
import { handleKeyEvent } from './utils/handleKeyEvent';
import { withRouter } from 'react-router-dom';
import Background from '../../images/questionnaire_bg.svg';
import { GApageView } from '../../ga';
import pages from './pages';

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

const ZeroAreaDiv = styled.div`
  width: 0;
  height: 0;
  overflow: hidden;
`;
//#endregion

function QuestionnairePage(props) {
  const {
    nextPage,
    prevPage,
    currentPage,
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
    createQFailure,
    createdQid,
    initPage,
    goNextPage,
    goPrevPage,
    validator,
    validateSuccess,
    valitationFail,
    gotoPage,
    patient,
    history,
  } = props;

  const focusRef = useRef(null);

  useEffect(() => {
    GApageView();
  }, []);

  useEffect(() => {
    function keyFunction(keyEvent) {
      handleKeyEvent(
        currentPage,
        keyEvent,
        {
          goPrevPage,
          goNextPage,
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
          gotoPage,
          validateSuccess,
          valitationFail,
        },
        { patient },
      );
    }

    document.addEventListener('keydown', keyFunction, false);

    return () => {
      document.removeEventListener('keydown', keyFunction, false);
    };
  }, [
    goNextPage,
    goPrevPage,
    currentPage,
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
    gotoPage,
    patient,
    validateSuccess,
    valitationFail,
  ]);

  useEffect(() => {
    initPage();
    const pid = match.params.pid;
    getPatient(pid);
  }, [match.params.pid, getPatient, initPage]);

  useEffect(() => {
    if (createQFailure) {
      message.error('請再試一次');
    }
  }, [createQFailure]);
  useEffect(() => {
    if (createQSuccess) {
      message.success('新增初診單成功');
      history.replace(`/q/history/${createdQid}`);
    }
    return () => {
      initPage();
    };
  }, [createQSuccess, initPage, history, createdQid]);

  const onSwipedUp = () => {
    focusRef.current.focus();
    if (validator) {
      const validation = validator(patient);
      if (!validation) {
        valitationFail(currentPage);
        return;
      }
      validateSuccess(currentPage);
    }
    nextPage ? gotoPage(nextPage) : goNextPage();
  };

  const onSwipedDown = () => {
    focusRef.current.focus();
    prevPage ? gotoPage(prevPage) : goPrevPage();
  };

  return (
    <Swipeable onSwipedUp={onSwipedUp} onSwipedDown={onSwipedDown}>
      <Container>
        {currentPage !== 20 && currentPage !== 21 && <QutContent />}
        {currentPage === 20 && <Form />}
        {currentPage === 21 && <Signature />}
        <ZeroAreaDiv>
          <button ref={focusRef}></button>
        </ZeroAreaDiv>
      </Container>
    </Swipeable>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  currentPage: questionnairePageReducer.flow.page,
  validator: pages.find(p => p.page === questionnairePageReducer.flow.page)?.validator,
  createQSuccess: questionnairePageReducer.flow.createQSuccess,
  createQFailure: questionnairePageReducer.flow.createQFailure,
  createdQid: questionnairePageReducer.flow.createdQId,
  nextPage: pages.find(p => p.page === questionnairePageReducer.flow.page)?.nextPage,
  prevPage: pages.find(p => p.page === questionnairePageReducer.flow.page)?.prevPage,
  patient: questionnairePageReducer.data.patient,
});

const mapDispatchToProps = {
  goNextPage: nextPage,
  goPrevPage: prevPage,
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
  validateSuccess,
  valitationFail,
  gotoPage,
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(QuestionnairePage));
