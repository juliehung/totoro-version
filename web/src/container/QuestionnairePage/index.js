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
  changeFinishModalVisible,
} from './actions';
import './index.css';
import QutContent from './QutContent';
import Form from './pages/Form';
import Signature from './pages/Signature';
import { handleKeyEvent } from './utils/handleKeyEvent';
import { withRouter } from 'react-router-dom';
import Background from '../../images/questionnaire_bg.svg';
import GAHelper from '../../ga';
import pages from './pages';
import FinishModal from './FinishModal';
import { Helmet } from 'react-helmet-async';
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
    isLast,
    changeFinishModalVisible,
  } = props;

  const focusRef = useRef(null);

  useEffect(() => {
    GAHelper.pageView();
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
          changeFinishModalVisible,
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
    changeFinishModalVisible,
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

    isLast ? changeFinishModalVisible(true) : nextPage ? gotoPage(nextPage) : goNextPage();
  };

  const onSwipedDown = () => {
    focusRef.current.focus();
    prevPage ? gotoPage(prevPage) : goPrevPage();
  };

  return (
    <Swipeable onSwipedUp={onSwipedUp} onSwipedDown={onSwipedDown}>
      <Container>
        <Helmet>
          <title>病歷</title>
        </Helmet>
        {currentPage !== 21 && currentPage !== 22 && <QutContent />}
        {currentPage === 21 && <Form />}
        {currentPage === 22 && <Signature />}
        <ZeroAreaDiv>
          <button ref={focusRef} />
        </ZeroAreaDiv>
        <FinishModal />
      </Container>
    </Swipeable>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => {
  const currentPageObj = pages.find(p => p.page === questionnairePageReducer.flow.page);

  return {
    currentPage: questionnairePageReducer.flow.page,
    createQSuccess: questionnairePageReducer.flow.createQSuccess,
    createQFailure: questionnairePageReducer.flow.createQFailure,
    createdQid: questionnairePageReducer.flow.createdQId,
    patient: questionnairePageReducer.data.patient,
    validator: currentPageObj?.validator,
    nextPage: currentPageObj?.nextPage,
    prevPage: currentPageObj?.prevPage,
    isLast: currentPageObj?.isLast,
  };
};

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
  changeFinishModalVisible,
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(QuestionnairePage));
