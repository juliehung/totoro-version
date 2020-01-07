import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changePregnantDate } from '../actions';
import { Icon, Input } from 'antd';
import { Container } from './Name';
import ConfirmButton from './ConfirmButton';

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
      <StyledInput type="date" size="large" placeholder="請選擇生日" onChange={onChange} value={props.date} />
      <br />
      <ConfirmButton
        nextPage={() => {
          props.gotoPage(19);
        }}
        disabled={!props.date}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ date: state.questionnairePageReducer.data.patient.pregnantDate });

const mapDispatchToProps = { gotoPage, changePregnantDate };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantA);
