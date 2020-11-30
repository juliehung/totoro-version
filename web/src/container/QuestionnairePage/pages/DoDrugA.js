import React from 'react';
import { connect } from 'react-redux';
import { gotoPage, changeDrug, valitationFail } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import { drugValidator } from '../utils/validators';
import ErrorMessage from './ErrorMessage';

export const checkDrugValidation = drug => !drug || drug.length === 0;

function DoDrugA(props) {
  const { validationError, patient, valitationFail } = props;

  const onInputChange = e => {
    props.changeDrug(e.target.value);
  };

  const onPressEnter = () => {
    if (!drugValidator(patient)) {
      valitationFail(23);
      return;
    }
    props.gotoPage(17);
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>正在服用的藥物名稱</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={patient.drug}
        onPressEnter={onPressEnter}
      />
      {validationError.includes(23) && <ErrorMessage errorText={`需填內容，若無用藥請回上頁選取"無"`} />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { gotoPage, changeDrug, valitationFail };

export default connect(mapStateToProps, mapDispatchToProps)(DoDrugA);
