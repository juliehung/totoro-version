import React, { useState } from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changePregnantDate } from '../actions';
import { Input } from 'antd';
import { Container } from './Name';
import { StyleRightCircleTwoTone } from './Address';
import MobileDatePicker from 'react-mobile-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { DateContainer, DatePickerContainer, StyledDatePicker } from './Birth';
import ErrorMessage from './ErrorMessage';

//#region
export const StyledInput = styled(Input)`
  font-size: 20px !important;
  margin: 20px 0 !important;
`;

//#endregion

export const checkPregnantDateValidation = date =>
  !date || moment().add(1, 'y').isBefore(moment(date)) || moment().add(-1, 'y').isAfter(moment(date));

function PregnantA(props) {
  const { pregnantDate, validationError } = props;

  const [mobileDateInputOpen, setMobileDateInputOpen] = useState(false);
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>預產期</span>
      </div>
      {window.navigator.userAgent.match(/^((?!chrome|android).)*safari/i) &&
      (window.navigator.userAgent.match(/iPhone/i) || window.navigator.userAgent.match(/iPad/i)) ? (
        <>
          <DateContainer
            onClick={() => {
              setMobileDateInputOpen(true);
            }}
          >
            <span> {pregnantDate ? pregnantDate : '填寫日期'}</span>
          </DateContainer>
          <MobileDatePicker
            isOpen={mobileDateInputOpen}
            theme="ios"
            onSelect={date => {
              props.changePregnantDate(moment(date).format('YYYY-MM-DD'));
              setMobileDateInputOpen(false);
            }}
            onCancel={() => {
              setMobileDateInputOpen(false);
            }}
            max={moment().add(2, 'y').toDate()}
            min={moment().add(-2, 'y').toDate()}
          />
        </>
      ) : (
        <DatePickerContainer>
          <StyledDatePicker
            selected={pregnantDate ? moment(pregnantDate).toDate() : pregnantDate}
            placeholderText="填寫日期 格式(年-月-日)"
            dateFormat="yyyy-MM-dd"
            showPopperArrow={true}
            maxDate={moment().add(1, 'y').toDate()}
            minDate={moment().toDate()}
            onChange={date => {
              props.changePregnantDate(moment(date).format('YYYY-MM-DD'));
            }}
          />
        </DatePickerContainer>
      )}
      <br />
      {validationError.includes(24) && <ErrorMessage errorText={`日期錯誤，若無懷孕請回上頁選取"無"`} />}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  pregnantDate: questionnairePageReducer.data.patient?.pregnantDate,
  validationError: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { gotoPage, changePregnantDate };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantA);
