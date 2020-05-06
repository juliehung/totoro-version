import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeBirth } from '../actions';
import { Input } from 'antd';
import { Container } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { StyleRightCircleTwoTone } from './Address';

//#region

export const StyledInput = styled(Input)`
  margin: 20px 0 !important;
  font-size: 24px !important;
`;

//#endregion

export const checkBirthValidation = birth =>
  !birth || moment().isBefore(moment(birth)) || moment('1911-01-01').isAfter(moment(birth));

function Birth(props) {
  const onChange = e => {
    props.changeBirth(e.target.value);
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>生日*</span>
      </div>
      <StyledInput
        type="date"
        size="large"
        value={props.birth}
        onChange={onChange}
        max={moment().format('YYYY-MM-DD')}
      />
      <br />
      <ConfirmButton nextPage={props.nextPage} disabled={checkBirthValidation(props.birth)} />
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ birth: state.questionnairePageReducer.data.patient.birth });

const mapDispatchToProps = { nextPage, changeBirth };

export default connect(mapStateToProps, mapDispatchToProps)(Birth);
