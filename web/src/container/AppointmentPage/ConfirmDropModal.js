import { Modal, Button } from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import { changeConfirmModalVisible, editAppointment } from './actions';
import styled from 'styled-components';
import { parseDeltaToBool } from './utils/parseDeltaToBool';

//#region
const Container = styled.div`
  width: 100%;
`;
const ButtonsContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
`;
const StyledButton = styled(Button)`
  margin: 0 5px;
`;

const HightLightSpan = styled.span`
  font-weight: bold;
  margin: 0 5px;
`;

//#endregion
function ConfirmDropModal(props) {
  const onCancel = () => {
    props.changeConfirmModalVisible(false);
    props.info.revert();
  };

  const onOK = () => {
    const id = props.info.event.extendedProps.appointment.id;

    if (props.info.newResource) {
      const doctor = { id: props.info.newResource.id };
      if (props.info.delta.milliseconds === 0) {
        props.editAppointment({ id, doctor });
      } else {
        const expectedArrivalTime = props.info.event.start;
        props.editAppointment({ id, doctor, expectedArrivalTime });
      }
    } else {
      if (props.info.delta) {
        const expectedArrivalTime = props.info.event.start;
        props.editAppointment({ id, expectedArrivalTime });
      } else if (props.info.endDelta) {
        const requiredTreatmentTime = moment(props.info.event.end).diff(moment(props.info.event.start), 'm');
        props.editAppointment({ id, requiredTreatmentTime });
      }
    }
  };

  return (
    <Modal centered title={null} footer={null} visible={props.visible} closable={false} onCancel={onCancel}>
      <Container>
        {!parseDeltaToBool(props.info.delta) && props.info.newResource && (
          <p>
            <span>確定更改</span>
            <HightLightSpan>{props.info.event ? props.info.event.title : ''}</HightLightSpan>
            <span>預約至</span>
            <HightLightSpan>{props.info.newResource.title}</HightLightSpan>
            <span>醫師</span>
            <span>?</span>
          </p>
        )}
        {parseDeltaToBool(props.info.delta) && props.info.newResource && (
          <p>
            <span>確定更改</span>
            <HightLightSpan>{props.info.event ? props.info.event.title : ''}</HightLightSpan>
            <span>預約至</span>
            <HightLightSpan>
              {props.info.event ? moment(props.info.event.start).format('YYYY-MM-DD HH:mm') : ''}
            </HightLightSpan>
            <span>和</span>
            <HightLightSpan>{props.info.newResource.title}</HightLightSpan>
            <span>醫師</span>
            <span>?</span>
          </p>
        )}
        {parseDeltaToBool(props.info.delta) && !props.info.newResource && (
          <p>
            <span>確定更改</span>
            <HightLightSpan>{props.info.event ? props.info.event.title : ''}</HightLightSpan>
            <span>預約至</span>
            <HightLightSpan>
              {props.info.event ? moment(props.info.event.start).format('YYYY-MM-DD HH:mm') : ''}
            </HightLightSpan>
            <span>?</span>
          </p>
        )}
        {props.info.endDelta && (
          <p>
            <span>確定更改</span>
            <HightLightSpan>{props.info.event ? props.info.event.title : ''}</HightLightSpan>
            <span>預約長度至</span>
            <HightLightSpan>
              {props.info.event ? moment(props.info.event.end).diff(moment(props.info.event.start), 'm') : ''}
            </HightLightSpan>
            <span>分鐘</span>
            <span>?</span>
          </p>
        )}
        <ButtonsContainer>
          <StyledButton onClick={onCancel}>取消</StyledButton>
          <StyledButton type="primary" onClick={onOK} loading={props.loading}>
            確定
          </StyledButton>
        </ButtonsContainer>
      </Container>
    </Modal>
  );
}

const mapStateToProps = ({ appointmentPageReducer }) => ({
  visible: appointmentPageReducer.drop.visible,
  info: appointmentPageReducer.drop.pendingInfo,
  range: appointmentPageReducer.calendar.range,
  loading: appointmentPageReducer.drop.oKLoading,
});

const mapDispatchToProps = { changeConfirmModalVisible, editAppointment };

export default connect(mapStateToProps, mapDispatchToProps)(ConfirmDropModal);
