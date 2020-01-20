import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changePregnantDate } from '../actions';
import { Icon, Input } from 'antd';
import { Container } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';

//#region
const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

export const StyledInput = styled(Input)`
  font-size: 20px !important;
  margin: 20px 0 !important;
`;

//#endregion

function PregnantA(props) {
  const onChange = e => {
    props.changePregnantDate(e.target.value);
  };

  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>預產期</span>
      </div>
      <StyledInput
        type="date"
        size="large"
        onChange={onChange}
        value={props.date}
        max={moment()
          .add(2, 'year')
          .format('YYYY-MM-DD')}
        min={moment().format('YYYY-MM-DD')}
      />
      <br />
      <ConfirmButton
        nextPage={() => {
          props.gotoPage(19);
        }}
        disabled={!props.date}
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
