import { Popover, Dropdown, Menu, Button, Popconfirm } from 'antd';
import React from 'react';
import { render } from 'react-dom';
import styled from 'styled-components';
import convertMrnTo5Digits from './convertMrnTo5Digits';
import { PhoneOutlined, UserOutlined, SolutionOutlined, EditOutlined } from '@ant-design/icons';

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

const StyledPhoneOutlined = styled(PhoneOutlined)`
  margin-right: 5px;
`;

const StyledUserOutlined = styled(UserOutlined)`
  margin-right: 5px;
`;

const StyledSolutionOutlined = styled(SolutionOutlined)`
  margin-right: 5px;
`;

const StyledEditOutlined = styled(EditOutlined)`
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 20px;
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
          const fcTitleClone = fcTitle.innerHTML;
          fcTitle.innerHTML = appointment.note
            ? `<div><b>${fcTitleClone}(${convertMrnTo5Digits(appointment.patientId)})</b><br /><span>${
                appointment.note
              }</span></div>`
            : `<div><b>${fcTitleClone}(${convertMrnTo5Digits(appointment.patientId)})</b><br /></div>`;
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
              <StyledPhoneOutlined />
              {appointment.phone}
            </BreakP>
            <BreakP>
              <StyledUserOutlined />
              {appointment.doctor.user.firstName}
            </BreakP>
            <BreakP>
              <StyledSolutionOutlined />
              {appointment.note}
            </BreakP>
            {!appointment.registrationStatus ? (
              appointment.status === 'CANCEL' ? (
                <Popconfirm
                  trigger="click"
                  title="確定恢復預約"
                  onConfirm={() => {
                    func.cancel({ id: appointment.id, status: 'CONFIRMED' });
                  }}
                >
                  <Button size="small">恢復預約</Button>
                </Popconfirm>
              ) : (
                <Popconfirm
                  trigger="click"
                  title="確定取消預約"
                  onConfirm={() => {
                    func.cancel({ id: appointment.id, status: 'CANCEL' });
                  }}
                >
                  <Button size="small">取消預約</Button>
                </Popconfirm>
              )
            ) : null}
            {!appointment.registrationStatus ? (
              <span>
                <StyledEditOutlined
                  onClick={() => {
                    func.edit(appointment);
                  }}
                />
                {process.env.NODE_ENV !== 'production' && (
                  <Button
                    type="link"
                    onClick={() => {
                      func.send(appointment);
                    }}
                  >
                    發送簡訊
                  </Button>
                )}
              </span>
            ) : null}
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
      } else if (info.view.type === 'dayGridMonth') {
        if (!appointment.registrationStatus) {
          info.el.addEventListener('dblclick', () => {
            func.edit(appointment);
          });
        }
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
          }${note ? ', ' + note : ''}</a>`,
        );
      } else {
        HTMLContent = info.el.innerHTML.replace(
          regex,
          `<a>${`No. ` + patientId}, ${patientName}, ${phone ? phone + `,` : ''} ${doctor.user.firstName}${
            note ? ', ' + note : ''
          }</a>`,
        );
      }

      info.el.innerHTML = HTMLContent;

      if (!appointment.registrationStatus) {
        info.el.addEventListener('dblclick', () => {
          func.edit(appointment);
        });
      }
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
      } else if (info.view.type === 'dayGridMonth') {
        info.el.addEventListener('dblclick', () => {
          func.edit(doctorDayOff);
        });
      }
    }
  }
}
