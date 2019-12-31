import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeBirth } from '../actions';
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

function Birth(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>生日*</span>
      </div>
      <StyledDatePicker size="large" placeholder="請選擇生日" value={props.birth} onChange={props.changeBirth} />
      <br />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.birth} />
    </Container>
  );
}

const mapStateToProps = state => ({ birth: state.questionnairePageReducer.data.birth });

const mapDispatchToProps = { nextPage, changeBirth };

export default connect(mapStateToProps, mapDispatchToProps)(Birth);
