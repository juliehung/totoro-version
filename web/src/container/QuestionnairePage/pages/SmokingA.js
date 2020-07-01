import React from 'react';
import { connect } from 'react-redux';
import { gotoPage, changeSmokingAmount, valitationFail } from '../actions';
import { Container, TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import ErrorMessage from './ErrorMessage';

export const checkSmokingAmountValidation = smokingAmount => !smokingAmount || smokingAmount < 0;

function SmokingA(props) {
  const { patient, validationError, valitationFail } = props;

  const onInputChange = e => {
    props.changeSmokingAmount(e.target.value);
  };

  const onPressEnter = () => {
    if (!props.smokingAmount) {
      valitationFail(23);
      return;
    }
    props.gotoPage(18);
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>一天大約幾支菸</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        type="number"
        onChange={onInputChange}
        onPressEnter={onPressEnter}
        value={patient.smokingAmount}
      />
      {validationError.includes(23) && <ErrorMessage errorText={`數字必須大於零，若無吸菸請回上頁選取"無"`} />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { gotoPage, changeSmokingAmount, valitationFail };

export default connect(mapStateToProps, mapDispatchToProps)(SmokingA);
