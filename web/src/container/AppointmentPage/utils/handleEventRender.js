import { Popover, Dropdown, Menu, Button, Popconfirm } from 'antd';
import React from 'react';
import { render } from 'react-dom';
import styled from 'styled-components';
import { PhoneOutlined, UserOutlined, SolutionOutlined, EditOutlined } from '@ant-design/icons';
import VisionImg from '../../../component/VisionImg';
import VixWinImg from '../../../component/VixWinImg';

import { XRAY_VENDORS } from '../constant';

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

const XrayContainer = styled.div`
  margin: 5px 0 10px;
  display: flex;
  & > div {
    cursor: pointer;
    margin-right: 10px;
    width: 33px;
    height: 33px;
    border-radius: 50%;
    background: #eee;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: box-shadow 200ms ease-in-out;
    &:hover {
      background-color: rgba(50, 102, 255, 0.2);
      box-shadow: 0 2px 13px 0 rgba(50, 102, 255, 0.3), 0 1px 3px 0 rgba(0, 0, 0, 0.18);
    }
  }
`;

const XrayContainerListView = styled.div`
  margin: 0;
  display: flex;
  & > div {
    cursor: pointer;
    margin-right: 10px;
    width: 33px;
    height: 33px;
    border-radius: 50%;
    background: #eee;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: box-shadow 200ms ease-in-out;
    &:hover {
      background-color: rgba(50, 102, 255, 0.2);
      box-shadow: 0 2px 13px 0 rgba(50, 102, 255, 0.3), 0 1px 3px 0 rgba(0, 0, 0, 0.18);
    }
  }
`;

const ListWeekContainer = styled.div`
  color: ${props => (props.isCanceled ? 'rgba(0,0,0,0.2)' : 'rgba(0,0,0,0.65)')};
  display: flex;
  justify-content: space-between;
`;
//#endregion

export function handleEventRender(info, func, params) {
  if (info.event.extendedProps.eventType === 'appointment') {
    const appointment = info.event.extendedProps.appointment;
    if (info.view.type.indexOf('Grid') !== -1) {
      const { id, medicalId, patientName, phone, doctor, note, status, registrationStatus } = appointment;

      if (info.view.type !== 'dayGridMonth') {
        const fcTitle = info.el.querySelector('.fc-title');
        if (fcTitle) {
          const fcTitleClone = fcTitle.innerHTML;
          fcTitle.innerHTML = note
            ? `<div><b>${fcTitleClone}(${medicalId})</b><br /><span>${note}</span></div>`
            : `<div><b>${fcTitleClone}(${medicalId})</b><br /></div>`;
        }

        const fcContent = info.el.querySelector('.fc-content');
        if (fcContent) {
          fcContent.innerHTML = `<div class="eventCard">${fcContent.innerHTML}</div>`;
        }

        const popoverContent = (
          <PopoverContainer>
            <HightLightSpan>
              {status === 'CANCEL' ? '[C]' : null} {patientName}
            </HightLightSpan>
            <HightLightSpan>{medicalId}</HightLightSpan>
            <BreakP>
              <StyledPhoneOutlined />
              {phone}
            </BreakP>
            <BreakP>
              <StyledUserOutlined />
              {doctor.user.firstName}
            </BreakP>
            <BreakP>
              <StyledSolutionOutlined />
              {note}
            </BreakP>
            {status !== 'CANCEL' && (
              <XrayContainer>
                {params.settings?.preferences?.generalSetting?.xRayVendorWeb?.includes(XRAY_VENDORS.vision) && (
                  <div
                    onClick={() => {
                      func.xray({ vendor: XRAY_VENDORS.vision, appointment });
                    }}
                  >
                    <VisionImg width="23" />
                  </div>
                )}
                {params.settings?.preferences?.generalSetting?.xRayVendorWeb?.includes(XRAY_VENDORS.vixwin) && (
                  <div
                    onClick={() => {
                      func.xray({ vendor: XRAY_VENDORS.vixwin, appointment });
                    }}
                  >
                    <VixWinImg width="23" />
                  </div>
                )}
              </XrayContainer>
            )}
            {!registrationStatus ? (
              status === 'CANCEL' ? (
                <Popconfirm
                  trigger="click"
                  title="確定恢復預約"
                  onConfirm={() => {
                    func.cancel({ id: id, status: 'CONFIRMED' });
                  }}
                >
                  <Button size="small">恢復預約</Button>
                </Popconfirm>
              ) : (
                <Popconfirm
                  trigger="click"
                  title="確定取消預約"
                  onConfirm={() => {
                    func.cancel({ id: id, status: 'CANCEL' });
                  }}
                >
                  <Button size="small">取消預約</Button>
                </Popconfirm>
              )
            ) : null}
            {!registrationStatus ? (
              <span>
                <StyledEditOutlined
                  onClick={() => {
                    func.edit(appointment);
                  }}
                />
              </span>
            ) : null}
          </PopoverContainer>
        );

        const contextMenu = !registrationStatus ? (
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
                  !registrationStatus
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
        if (!registrationStatus) {
          info.el.addEventListener('dblclick', () => {
            func.edit(appointment);
          });
        }
      }
    } else if (info.view.type === 'listWeek') {
      const { medicalId, patientName, phone, doctor, note, status } = appointment;
      const isCanceled = status === 'CANCEL';
      render(
        <ListWeekContainer
          isCanceled={isCanceled}
          onDoubleClick={
            isCanceled
              ? null
              : () => {
                  func.edit(appointment);
                }
          }
        >
          <span>
            {`${medicalId}, ${isCanceled ? '[C]' : ''}${patientName}, ${phone ? phone + `,` : ''} ${
              doctor.user.firstName
            } ${note ? ', ' + note : ''}`}
          </span>
          {!isCanceled && (
            <XrayContainerListView>
              {params.settings?.preferences?.generalSetting?.xRayVendorWeb?.includes(XRAY_VENDORS.vision) && (
                <div
                  onClick={() => {
                    func.xray({ vendor: XRAY_VENDORS.vision, appointment });
                  }}
                  onDoubleClick={e => e.stopPropagation()}
                >
                  <VisionImg width="23" />
                </div>
              )}
              {params.settings?.preferences?.generalSetting?.xRayVendorWeb?.includes(XRAY_VENDORS.vixwin) && (
                <div
                  onClick={() => {
                    func.xray({ vendor: XRAY_VENDORS.vixwin, appointment });
                  }}
                  onDoubleClick={e => e.stopPropagation()}
                >
                  <VixWinImg width="23" />
                </div>
              )}
            </XrayContainerListView>
          )}
        </ListWeekContainer>,
        info.el.querySelector('a'),
      );
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
              if (e.key === 'edit') {
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
