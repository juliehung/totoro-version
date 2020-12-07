import { Popover, Dropdown, Menu } from 'antd';
import React from 'react';
import { render } from 'react-dom';
import styled from 'styled-components';
import { PhoneOutlined, UserOutlined, SolutionOutlined, EditOutlined } from '@ant-design/icons';
import VisionImg from '../../../component/VisionImg';
import VixWinImg from '../../../component/VixWinImg';
import CancelAppointmentButton from '../CancelAppointmentButton';
import RestoreAppointmentButton from '../RestoreAppointmentButton';
import { getBaseUrl } from '../../../utils/getBaseUrl';
import parseDateToString from './parseDateToString';

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

const NameSpan = styled.span`
  font-size: 24px;
  color: inherit;
`;

const LinkSpan = styled.a`
  height: 24px;
  color: #fff;
  text-decoration: inherit;
  background: #3266ff;
  border-radius: 34px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;

  > span {
    font-size: 10px;
  }

  &:hover,
  &:focus,
  &:active {
    text-decoration: none;
    color: #fff;
  }
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

const MenuStyle = styled(Menu)`
  border-radius: ${props => (props.is_first_menu === 'true' ? '8px 8px 0 0' : '0 0 8px 8px')} !important;
  padding: 3px !important;
  > li {
    margin-top: 0 !important;
    margin-bottom: 0 !important;
    border-radius: 8px;
    background-color: initial !important;
    color: rgba(0, 0, 0, 0.85);
    text-align: center !important;
    &:hover {
      border-radius: 8px;
      background-color: #f5f5f5 !important;
      color: rgba(0, 0, 0, 0.85) !important;

      > button {
        border-color: transparent !important;
        background-color: #f5f5f5 !important;
      }
    }
  }

  > button {
    width: 100%;
    border-radius: 8px !important;
    height: 40px;
    border: 0;
    box-shadow: none;

    &:hover {
      border-radius: 8px;
      background-color: #fee1dd !important;
    }

    &:focus {
      color: #fff;
      background: #ff4d4f;
      box-shadow: none;

      &:hover {
        color: #fff !important;
        background: #ff4d4f !important;
        box-shadow: none !important;
      }
    }
  }

  button.restore-appointment-btn {
    color: #222b45 !important;
    &:focus {
      background-color: #edf1f7 !important;
      color: #222b45 !important;

      &:hover {
        background-color: #edf1f7 !important;
        color: #222b45 !important;
      }
    }
    &:hover {
      background-color: #edf1f7 !important;
      color: #222b45 !important;
    }
  }
`;
//#endregion

export function handleEventRender(info, func, params, { clickTitle = () => {} }) {
  if (info.event.extendedProps.eventType === 'appointment') {
    const appointment = info.event.extendedProps.appointment;
    if (info.view.type.indexOf('Grid') !== -1) {
      const { id, birth, patientId, patientName, phone, doctor, note, status, registrationStatus } = appointment;

      if (info.view.type !== 'dayGridMonth') {
        const fcTitle = info.el.querySelector('.fc-title');
        if (fcTitle) {
          const fcTitleClone = fcTitle.innerHTML;
          fcTitle.innerHTML = note
            ? `<div><b>${fcTitleClone}(${parseDateToString(birth, false)})</b><br /><span>${note}</span></div>`
            : `<div><b>${fcTitleClone}(${parseDateToString(birth, false)})</b><br /></div>`;
        }

        const fcContent = info.el.querySelector('.fc-content');
        if (fcContent) {
          fcContent.innerHTML = `<div class="eventCard">${fcContent.innerHTML}</div>`;
        }

        const popoverContent = (
          <PopoverContainer>
            <NameSpan>
              {status === 'CANCEL' ? '[C]' : null} {patientName}
            </NameSpan>
            <HightLightSpan>{parseDateToString(birth, false)}</HightLightSpan>
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
            <XrayContainer>
              {params.xRayVendors?.[XRAY_VENDORS.vision] === 'true' && (
                <div
                  onClick={() => {
                    func.xray({ vendor: XRAY_VENDORS.vision, appointment });
                  }}
                >
                  <VisionImg width="23" />
                </div>
              )}
              {params.xRayVendors?.[XRAY_VENDORS.vixwin] === 'true' && (
                <div
                  onClick={() => {
                    func.xray({ vendor: XRAY_VENDORS.vixwin, appointment });
                  }}
                >
                  <VixWinImg width="23" />
                </div>
              )}
            </XrayContainer>
            <LinkSpan href={`${getBaseUrl()}#/patient/${patientId}`} target="_blank" rel="noopener noreferrer">
              查看詳細資訊
            </LinkSpan>
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
          <>
            <MenuStyle
              onClick={() => {
                func.edit(appointment);
                clickTitle();
              }}
              is_first_menu={'true'}
            >
              <Menu.Item key="edit" className="first-li">
                編輯預約
              </Menu.Item>
            </MenuStyle>
            {status === 'CANCEL' ? (
              <MenuStyle is_first_menu={'false'}>
                <RestoreAppointmentButton id={id} onConfirm={func.restore} className={'restore-appointment-btn'} />
              </MenuStyle>
            ) : (
              <MenuStyle is_first_menu={'false'}>
                <CancelAppointmentButton id={id} onConfirm={func.cancel} className={'cancel-appointment-btn'} />
              </MenuStyle>
            )}
          </>
        ) : (
          <div />
        );

        render(
          !info.isMirror ? (
            <Popover content={popoverContent} trigger="hover" placement="top">
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
            </Popover>
          ) : (
            <div style={{ height: '100%' }} dangerouslySetInnerHTML={{ __html: info.el.innerHTML }} />
          ),
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
          <XrayContainerListView>
            {params.xRayVendors?.[XRAY_VENDORS.vision] === 'true' && (
              <div
                onClick={() => {
                  func.xray({ vendor: XRAY_VENDORS.vision, appointment });
                }}
                onDoubleClick={e => e.stopPropagation()}
              >
                <VisionImg width="23" />
              </div>
            )}
            {params.xRayVendors?.[XRAY_VENDORS.vixwin] === 'true' && (
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
