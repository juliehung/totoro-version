import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, prevPage } from './actions';
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import Name from './pages/Name';
import Gender from './pages/Gender';
import Disease from './pages/Disease';
import Birth from './pages/Birth';
import NationalId from './pages/NationalId';
import BloodType from './pages/BloodType';
import Phone from './pages/Phone';
import Address from './pages/Address';
import Career from './pages/Career';
import Marriage from './pages/Marriage';
import Introducer from './pages/Introducer';
import EmergencyContactName from './pages/EmergencyContactName';
import EmergencyContactPhone from './pages/EmergencyContactPhone';
import EmergencyContactRelationship from './pages/EmergencyContactRelationship';
import Allergy from './pages/Allergy';
import DoDrugQ from './pages/DoDrugQ';
import DoDrugA from './pages/DoDrugA';
import PregnantQ from './pages/PregnantQ';
import PregnantA from './pages/PregnantA';
import SmokingQ from './pages/SmokingQ';
import SmokingA from './pages/SmokingA';
import Other from './pages/Other';
import { Helmet } from 'react-helmet-async';
import './index.css';

//#region
const Container = styled.div`
  width: 600px;
  height: 400px;
  position: relative;
`;
//#endregion

function QutContent(props) {
  const classNames = props.reverse ? 'item-reverse' : 'item';
  return (
    <Container>
      <Helmet>
        <title>填寫初診單</title>
      </Helmet>
      <TransitionGroup>
        {props.page === 1 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Name />
            </div>
          </CSSTransition>
        )}
        {props.page === 2 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Birth />
            </div>
          </CSSTransition>
        )}
        {props.page === 3 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Gender />
            </div>
          </CSSTransition>
        )}
        {props.page === 4 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <NationalId />
            </div>
          </CSSTransition>
        )}
        {props.page === 5 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <BloodType />
            </div>
          </CSSTransition>
        )}
        {props.page === 6 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Phone />
            </div>
          </CSSTransition>
        )}
        {props.page === 7 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Address />
            </div>
          </CSSTransition>
        )}
        {props.page === 8 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Career />
            </div>
          </CSSTransition>
        )}
        {props.page === 9 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Marriage />
            </div>
          </CSSTransition>
        )}
        {props.page === 10 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Introducer />
            </div>
          </CSSTransition>
        )}
        {props.page === 11 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <EmergencyContactName />
            </div>
          </CSSTransition>
        )}
        {props.page === 12 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <EmergencyContactPhone />
            </div>
          </CSSTransition>
        )}
        {props.page === 13 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <EmergencyContactRelationship />
            </div>
          </CSSTransition>
        )}
        {props.page === 14 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Disease />
            </div>
          </CSSTransition>
        )}
        {props.page === 15 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Allergy />
            </div>
          </CSSTransition>
        )}
        {props.page === 16 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <DoDrugQ />
            </div>
          </CSSTransition>
        )}
        {props.page === 17 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <SmokingQ />
            </div>
          </CSSTransition>
        )}
        {props.page === 18 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <PregnantQ />
            </div>
          </CSSTransition>
        )}
        {props.page === 19 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <Other />
            </div>
          </CSSTransition>
        )}
        {props.page === 22 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <DoDrugA />
            </div>
          </CSSTransition>
        )}
        {props.page === 23 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <PregnantA />
            </div>
          </CSSTransition>
        )}
        {props.page === 24 && (
          <CSSTransition timeout={500} classNames={classNames}>
            <div>
              <SmokingA />
            </div>
          </CSSTransition>
        )}
      </TransitionGroup>
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  page: questionnairePageReducer.flow.page,
  reverse: questionnairePageReducer.flow.reverse,
});

const mapDispatchToProps = { nextPage, prevPage };

export default connect(mapStateToProps, mapDispatchToProps)(QutContent);
