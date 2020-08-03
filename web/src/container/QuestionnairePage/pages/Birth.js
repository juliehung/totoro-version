import React, { useState, Fragment } from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeBirth } from '../actions';
import { Container } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import MobileDatePicker from 'react-mobile-datepicker';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ErrorMessage from './ErrorMessage';

//#region

export const DateContainer = styled.div`
  margin: 20px 0 !important;
  font-size: 24px !important;
  min-height: 50px !important;
  color: #333;
  padding: 3px 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
`;

export const DatePickerContainer = styled.div`
  width: 100%;
  margin: 20px 0;
`;

export const StyledDatePicker = styled(DatePicker)`
  color: #333;
  border: 1px solid #ccc;
`;
//#endregion

export const checkBirthValidation = birth =>
  !birth || moment().isBefore(moment(birth)) || moment('1911-01-01').isAfter(moment(birth));

function Birth(props) {
  const { validationError, patient, changeBirth } = props;
  const [mobileBirthInputOpen, setMobileBirthInputOpen] = useState(false);

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>生日*</span>
      </div>
      {window.navigator.userAgent.match(/^((?!chrome|android).)*safari/i) &&
      (window.navigator.userAgent.match(/iPhone/i) || window.navigator.userAgent.match(/iPad/i)) ? (
        <Fragment>
          <DateContainer
            onClick={() => {
              setMobileBirthInputOpen(true);
            }}
          >
            <span> {patient.birth ? patient.birth : '填寫生日'}</span>
          </DateContainer>
          <MobileDatePicker
            isOpen={mobileBirthInputOpen}
            theme="ios"
            onSelect={date => {
              changeBirth(moment(date).format('YYYY-MM-DD'));
              setMobileBirthInputOpen(false);
            }}
            onCancel={() => {
              setMobileBirthInputOpen(false);
            }}
          />
        </Fragment>
      ) : (
        <DatePickerContainer>
          <StyledDatePicker
            selected={patient.birth ? moment(patient.birth).toDate() : patient.birth}
            placeholderText="填寫生日 格式(年-月-日)"
            dateFormat="yyyy-MM-dd"
            showPopperArrow={true}
            onChange={date => {
              changeBirth(moment(date).format('YYYY-MM-DD'));
            }}
          />
        </DatePickerContainer>
      )}
      {validationError.includes(2) && <ErrorMessage errorText={'日期錯誤'} />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  patient: questionnairePageReducer.data.patient,
  birth: questionnairePageReducer.data.patient.birth,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { nextPage, changeBirth };

export default connect(mapStateToProps, mapDispatchToProps)(Birth);
