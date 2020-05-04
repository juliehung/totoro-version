import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changePregnantDate } from '../actions';
import { Input } from 'antd';
import { Container } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { StyleRightCircleTwoTone } from './Address';

//#region
export const StyledInput = styled(Input)`
  font-size: 20px !important;
  margin: 20px 0 !important;
`;

//#endregion

export const checkPregnantDateValidation = date =>
  !date || moment().add(1, 'y').isBefore(moment(date)) || moment().add(-1, 'y').isAfter(moment(date));

function PregnantA(props) {
  const onChange = e => {
    props.changePregnantDate(e.target.value);
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>預產期</span>
      </div>
      <StyledInput
        type="date"
        size="large"
        onChange={onChange}
        value={props.date}
        max={moment().add(1, 'y').format('YYYY-MM-DD')}
        min={moment().add(-1, 'y').format('YYYY-MM-DD')}
      />
      <br />
      <ConfirmButton
        nextPage={() => {
          props.gotoPage(19);
        }}
        disabled={checkPregnantDateValidation(props.date)}
      />
      <PageControllContainer
        pre={() => {
          props.gotoPage(16);
        }}
        next={() => {
          props.gotoPage(16);
        }}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ date: state.questionnairePageReducer.data.patient.pregnantDate });

const mapDispatchToProps = { gotoPage, changePregnantDate };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantA);
