import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changePregnantDate } from '../actions';
import { Input } from 'antd';
import { Container } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import 'react-datepicker/dist/react-datepicker.css';
import ErrorMessage from './ErrorMessage';
import Dp from '../../../component/DatePicker';

//#region
export const StyledInput = styled(Input)`
  font-size: 20px !important;
  margin: 20px 0 !important;
`;

export const CalendarContainer = styled.div`
  margin: 20px 0;
`;

//#endregion

export const checkPregnantDateValidation = date =>
  !date || moment().add(1, 'y').isBefore(moment(date)) || moment().add(-1, 'y').isAfter(moment(date));

function PregnantA(props) {
  const { pregnantDate, validationError } = props;

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>預產期</span>
      </div>
      <CalendarContainer>
        <Dp
          date={pregnantDate ? moment(pregnantDate) : pregnantDate}
          onDateChange={date => {
            props.changePregnantDate(moment(date).format('YYYY-MM-DD'));
          }}
          placeholderText="請選擇預產期"
          readOnly
          size="large"
        />
      </CalendarContainer>

      <br />
      {validationError.includes(25) && <ErrorMessage errorText={`日期錯誤，若無懷孕請回上頁選取"無"`} />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  pregnantDate: questionnairePageReducer.data.patient?.pregnantDate,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { gotoPage, changePregnantDate };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantA);
