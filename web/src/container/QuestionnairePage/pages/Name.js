import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeName, valitationFail } from '../actions';
import { Input } from 'antd';
import { withRouter } from 'react-router-dom';
import { StyleRightCircleTwoTone } from './Address';
import { nameValidator } from '../utils/validators';
import ErrorMessagess from './ErrorMessage';

//#region

export const Container = styled.div`
  font-size: 24px;
  color: #1890ff;
`;

export const TransparentInput = styled(Input)`
  margin: 20px 0 !important;
  border: none !important;
  border-color: transparent !important;
  background: transparent !important;
  font-size: 24px !important;
  &:focus {
    box-shadow: none !important;
  }
`;

//#endregion

function Name(props) {
  const { validationError, patient, changeName, nextPage, valitationFail } = props;

  const onInputChange = e => {
    changeName(e.target.value);
  };

  const onPressEnter = () => {
    if (!nameValidator(patient)) {
      valitationFail(1);
      return;
    }
    nextPage();
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>姓名*</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={patient.name}
        onPressEnter={onPressEnter}
      />
      {validationError.includes(1) && <ErrorMessagess errorText="必填項目" />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { nextPage, changeName, valitationFail };

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Name));
