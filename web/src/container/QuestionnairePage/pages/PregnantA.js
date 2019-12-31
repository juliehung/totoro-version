import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changePregnantDate } from '../actions';
import { Icon, DatePicker } from 'antd';
import { Container } from './Name';
import ConfirmButton from './ConfirmButton';

//#region
const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const StyledDatePicker = styled(DatePicker)`
  margin: 20px 0 !important;
`;
//#endregion

function PregnantA(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>預產期</span>
      </div>
      <StyledDatePicker
        size="large"
        placeholder="請選擇生日"
        readOnly
        onChange={props.changePregnantDate}
        value={props.date}
      />
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

const mapStateToProps = state => ({ date: state.questionnairePageReducer.data.pregnantDate });

const mapDispatchToProps = { gotoPage, changePregnantDate };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantA);
