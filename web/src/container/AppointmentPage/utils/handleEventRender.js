import { Popover, Icon, Dropdown, Menu, Button, Popconfirm } from 'antd';
import React from 'react';
import { render } from 'react-dom';
import styled from 'styled-components';
import convertMrnTo5Digits from './convertMrnTo5Digits';

//#region
const PopoverContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 160px;
`;

const BreakP = styled.p`
  margin: 5px 0;
  overflow-wrap: break-word;
`;

const StyledIcon = styled(Icon)`
  margin-right: 5px;
`;

const ButtonsContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 10px 0 0;
`;

const Button50 = styled(Button)`
  width: 50px;
`;

const Button100 = styled(Button)`
  width: 100px;
`;

const HightLightSpan = styled.span`
  font-weight: bold;
  font-style: italic;
`;
//#endregion

export function handleEventRender(info, func) {
  if (info.event.extendedProps.eventType === 'appointment') {
    const appointment = info.event.extendedProps.appointment;
    if (info.view.type.indexOf('Grid') !== -1) {
      if (info.view.type !== 'dayGridMonth') {
        const fcTitle = info.el.querySelector('.fc-title');
        if (fcTitle) {
          fcTitle.innerHTML += appointment.note
            ? `<br /><i>${convertMrnTo5Digits(appointment.patientId)}</i><br/><span>${appointment.note}</span>`
            : `<br /><i>${convertMrnTo5Digits(appointment.patientId)}</i>`;
        }

        const fcContent = info.el.querySelector('.fc-content');
        if (fcContent) {
          fcContent.innerHTML = `<div class="eventCard">${fcContent.innerHTML}</div>`;
        }

        const popoverContent = (
          <PopoverContainer>
            <HightLightSpan>
              {appointment.status === 'CANCEL' ? '[C]' : null} {appointment.patientName}
            </HightLightSpan>
            <HightLightSpan>{convertMrnTo5Digits(appointment.patientId)}</HightLightSpan>
            <BreakP>
              <StyledIcon type="phone" />
              {appointment.phone}
            </BreakP>
            <BreakP>
              <StyledIcon type="user" />
              {appointment.doctor.user.firstName}
            </BreakP>
            <BreakP>
              <StyledIcon type="solution" />
              {appointment.note}
            </BreakP>
            <ButtonsContainer>
              {!appointment.registrationStatus ? (
                appointment.status === 'CANCEL' ? (
                  <Popconfirm
                    trigger="click"
                    title="確定恢復預約"
                    onConfirm={() => {
                      func.cancel({ id: appointment.id, status: 'CONFIRMED' });
                    }}
                  >
                    <Button50 size="small">恢復</Button50>
                  </Popconfirm>
                ) : (
                  <Popconfirm
                    trigger="click"
                    title="確定取消預約"
                    onConfirm={() => {
                      func.cancel({ id: appointment.id, status: 'CANCEL' });
                    }}
                  >
                    <Button50 size="small">取消</Button50>
                  </Popconfirm>
                )
              ) : null}
              {!appointment.registrationStatus ? (
                <Button100
                  size="small"
                  onClick={() => {
                    func.edit(appointment);
                  }}
                >
                  編輯
                </Button100>
              ) : null}
            </ButtonsContainer>
          </PopoverContainer>
        );

        const contextMenu = !appointment.registrationStatus ? (
          <Menu
            onClick={() => {
              func.edit(appointment);
            }}
          >
            <Menu.Item key="edit">編輯</Menu.Item>
          </Menu>
        ) : (
          <div />
        );

        render(
          <Popover content={popoverContent} trigger="click" placement="right">
            <Dropdown overlay={contextMenu} trigger={['contextMenu']}>
              <div
                style={{ height: '100%' }}
                dangerouslySetInnerHTML={{ __html: info.el.innerHTML }}
                onDoubleClick={
                  !appointment.registrationStatus
                    ? () => {
                        func.edit(appointment);
                      }
                    : null
                }
              />
            </Dropdown>
          </Popover>,
          info.el,
        );
      }
    } else if (info.view.type === 'listWeek') {
      const regex = /<a>(.*?)<\/a>/;
      const { patientId, patientName, phone, doctor, note, status } = appointment;

      let HTMLContent;
      if (status === 'CANCEL') {
        HTMLContent = info.el.innerHTML.replace(
          regex,
          `<a style='color:rgba(0,0,0,0.2)'>${patientId}, '[C]'${patientName}, ${phone ? phone + `,` : ''} ${
            doctor.user.firstName
          }, ${note}</a>`,
        );
      } else {
        HTMLContent = info.el.innerHTML.replace(
          regex,
          `<a>${`No. ` + patientId}, ${patientName}, ${phone ? phone + `,` : ''} ${doctor.user.firstName}, ${note}</a>`,
        );
      }

      info.el.innerHTML = HTMLContent;
    }
  } else if (info.event.extendedProps.eventType === 'doctorDayOff') {
    const doctorDayOff = info.event.extendedProps.doctorDayOff;
    if (info.view.type.indexOf('Grid') !== -1) {
      if (info.view.type !== 'dayGridMonth') {
        const fcContent = info.el.querySelector('.fc-content');
        const fcTitle = info.el.querySelector('.fc-title');

        if (doctorDayOff.note && fcTitle) {
          fcTitle.innerHTML += `<br /><i>${doctorDayOff.note}</i><br />`;
        }

        if (fcContent) {
          fcContent.innerHTML = `<div class="eventCard">${fcContent.innerHTML}</div>`;
        }
        const contentMenu = (
          <Menu
            onClick={e => {
              if (e.key === 'delete') {
              } else if (e.key === 'edit') {
                func.edit(doctorDayOff);
              }
            }}
          >
            <Menu.Item key="edit">編輯</Menu.Item>
          </Menu>
        );

        render(
          <Dropdown overlay={contentMenu} trigger={['contextMenu']}>
            <div
              style={{ height: '100%' }}
              dangerouslySetInnerHTML={{ __html: info.el.innerHTML }}
              onDoubleClick={() => {
                func.edit(doctorDayOff);
              }}
            />
          </Dropdown>,
          info.el,
        );
      }
    }
  }
}
