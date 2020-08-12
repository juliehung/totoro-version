import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeBirth } from '../actions';
import { Container } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import 'react-datepicker/dist/react-datepicker.css';
import ErrorMessage from './ErrorMessage';
import DatePicker from '../../../component/DatePicker';

//#region
export const CalendarContainer = styled.div`
  margin: 20px 0;
`;
//#endregion

export const checkBirthValidation = birth =>
  !birth || moment().isBefore(moment(birth)) || moment('1911-01-01').isAfter(moment(birth));

function Birth(props) {
  const { validationError, patient, changeBirth } = props;

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>生日*</span>
      </div>
      <CalendarContainer>
        <DatePicker
          date={patient.birth ? moment(patient.birth) : patient.birth}
          onDateChange={date => changeBirth(moment(date).format('YYYY-MM-DD'))}
          placeholderText="請選擇生日"
          readOnly
          size="large"
        />
        {validationError.includes(2) && <ErrorMessage errorText={'日期錯誤'} />}
      </CalendarContainer>
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
