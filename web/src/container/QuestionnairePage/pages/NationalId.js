import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changeNationalId, getExistNationalId, valitationFail } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import ErrorMessagess from './ErrorMessage';
import { nationalIdValidator } from '../utils/validators';

function NationalId(props) {
  const onInputChange = e => {
    props.changeNationalId(e.target.value);
  };

  const onPressEnter = () => {
    if (!nationalIdValidator(props.patient, props.isPatientExist, props.existedNationalId)) {
      valitationFail(4);
      return;
    }
    nextPage();
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>身分證字號</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        onPressEnter={onPressEnter}
        value={props.nationalId}
        onBlur={() => props?.nationalId && props.nationalId.length !== 0 && props.getExistNationalId(props.nationalId)}
      />
      {!nationalIdValidator(props.nationalId, props.isPatientExist, props.existedNationalId) && (
        <ErrorMessagess errorText={'身分證號重複'} />
      )}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  nationalId: questionnairePageReducer.data.patient.nationalId,
  isPatientExist: questionnairePageReducer.data.isPatientExist,
  existedNationalId: questionnairePageReducer.data.existedNationalId,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { nextPage, changeNationalId, getExistNationalId };

export default connect(mapStateToProps, mapDispatchToProps)(NationalId);
