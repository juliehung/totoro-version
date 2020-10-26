import React, { useEffect, useRef } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Container, Header, Content } from './component';
import { Input, Button } from 'antd';
import {
  changeDiagnosisNoteFocused,
  changeClinicNote,
  addDateClinicNote,
  updateClinicNote,
  restoreClinicNote,
  restoreClinicNoteUpdateSuccess,
} from './actions';

import { CheckOutlined } from '@ant-design/icons';

import calendarPlus from '../../images/calendar-plus.svg';

const { TextArea } = Input;

//#region
const StyledContainer = styled(Container)`
  &:not(:focus-within) {
    & .hidingButton {
      display: none;
    }
  }
`;

const ButtonsContainer = styled.div`
  & > :last-child {
    margin-left: 9px;
  }
`;

const ButtonContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  color: #3366ff;
`;
//#endregion

function PatientDetailDiagnosisNote(props) {
  const {
    clinicNote,
    changeClinicNote,
    editedClinicNote,
    addDateClinicNote,
    updateClinicNote,
    restoreClinicNote,
    updateSuccess,
    restoreClinicNoteUpdateSuccess,
  } = props;

  const saveButtonRef = useRef(null);

  const onTextChange = e => {
    changeClinicNote(e.target.value);
  };

  useEffect(() => {
    if (updateSuccess) {
      const restoreClinicNoteUpdateSuccessTimeout = setTimeout(() => {
        restoreClinicNoteUpdateSuccess();
        saveButtonRef.current.blur();
      }, 500);

      return () => {
        clearTimeout(restoreClinicNoteUpdateSuccessTimeout);
      };
    }
  }, [updateSuccess, restoreClinicNoteUpdateSuccess]);

  return (
    <StyledContainer>
      <Header>
        <div>
          <span>診療筆記</span>
          <Button
            size="small"
            shape="round"
            onClick={addDateClinicNote}
            style={{ marginLeft: '16px', borderColor: '#e4eaff', backgroundColor: '#e4eaff' }}
            className="hidingButton"
          >
            <ButtonContent>
              <img src={calendarPlus} alt="calendar" style={{ height: '12px', marginRight: '8px' }} />
              <span>加入日期</span>
            </ButtonContent>
          </Button>
        </div>
        <ButtonsContainer className="hidingButton">
          <Button size="small" shape="round" onClick={restoreClinicNote}>
            放棄
          </Button>
          <Button
            size="small"
            type="primary"
            shape="round"
            onClick={updateClinicNote}
            icon={updateSuccess ? <CheckOutlined /> : null}
            ref={saveButtonRef}
          >
            儲存
          </Button>
        </ButtonsContainer>
      </Header>
      <Content>
        <TextArea
          autoSize={{ minRows: 15, maxRows: 15 }}
          value={editedClinicNote || clinicNote}
          onChange={onTextChange}
          style={{ fontSize: '15px', color: '#222b45' }}
        />
      </Content>
    </StyledContainer>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  clinicNote: patientPageReducer.patient.editedClinicNote,
  updateSuccess: patientPageReducer.patient.updateSuccess,
});

const mapDispatchToProps = {
  changeDiagnosisNoteFocused,
  changeClinicNote,
  addDateClinicNote,
  updateClinicNote,
  restoreClinicNote,
  restoreClinicNoteUpdateSuccess,
};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailDiagnosisNote);
