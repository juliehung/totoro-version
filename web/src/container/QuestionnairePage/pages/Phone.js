import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changePhone, valitationFail } from '../actions';
import { Container, TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import { phoneValidator } from '../utils/validators';
import ErrorMessage from './ErrorMessage';

function Phone(props) {
  const { patient, nextPage, validationError, valitationFail } = props;
  const onInputChange = e => {
    props.changePhone(e.target.value);
  };

  const onPressEnter = () => {
    if (!phoneValidator(patient)) {
      valitationFail(6);
      return;
    }
    nextPage();
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>聯絡電話*</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={patient.phone}
        onPressEnter={onPressEnter}
      />
      {validationError.includes(6) && <ErrorMessage errorText="電話格式錯誤" />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { nextPage, changePhone, valitationFail };

export default connect(mapStateToProps, mapDispatchToProps)(Phone);
