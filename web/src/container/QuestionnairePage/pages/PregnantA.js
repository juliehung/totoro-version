import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, prevPage } from '../actions';
import { Icon, DatePicker } from 'antd';
import { Container } from './Name';

//#region
const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const StyledDatePicker = styled(DatePicker)`
  margin-top: 20px !important;
`;
//#endregion

function PregnantA() {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>預產期</span>
      </div>
      <StyledDatePicker size="large" placeholder="請選擇生日" />
    </Container>
  );
}

const mapStateToProps = state => ({ page: state.questionnairePageReducer.flow.page });

const mapDispatchToProps = { nextPage, prevPage };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantA);
