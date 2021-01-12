import { Popover, Dropdown, Menu, Tag } from 'antd';
import React from 'react';
import { render } from 'react-dom';
import styled from 'styled-components';
import { EditOutlined } from '@ant-design/icons';
import VisionImg from '../../../component/VisionImg';
import VixWinImg from '../../../component/VixWinImg';
import CancelAppointmentButton from '../CancelAppointmentButton';
import RestoreAppointmentButton from '../RestoreAppointmentButton';
import { getBaseUrl } from '../../../utils/getBaseUrl';
import parseDateToString from './parseDateToString';
import PersonFillIcon from '../../../images/personIcon-fill.svg';
import PhoneFillIcon from '../../../images/phone-fill.svg';
import FileTextFillIcon from '../../../images/file-text-fill.svg';

import { XRAY_VENDORS } from '../constant';

//#region
const PopoverContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 265px;
  .name {
    font-size: 22px;
    font-weight: 600;
    color: inherit;
    line-height: 1.45;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 90%;
  }
  .btn-wrap {
    display: flex;
    align-items: center;
    margin-top: 7px;
    .xray-container {
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
    }
    .link {
      text-align: right;
      font-family: PingFangSC;
      font-size: 14px;
      font-weight: 600;
      color: #3366ff;
      line-height: 1.14;
      padding: 5px;
      margin-left: auto;

      &:hover,
      &:focus,
      &:active {
        text-decoration: none;
      }
    }
  }
`;

const InfoWrap = styled.div`
  display: flex;
  flex-direction: column;
  font-family: OpenSans;
  font-size: 13px;
  line-height: 1.38;
  margin-top: 5px;
  .info {
    margin: 5px 0;
    overflow-wrap: break-all;
    display: flex;
    .info-icon {
      margin-right: 5px;
    }
  }
`;

const StyledEditOutlined = styled(EditOutlined)`
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 20px;
`;

const HightLightSpan = styled.span`
  font-weight: bold;
  font-style: ${props => (props.birth ? 'italic' : 'normal')};
  line-height: normal;
  color: ${props => (props.birth ? '#222b45' : '#8f9bb3')};
  font-size: 11px;
  letter-spacing: -1px;
`;

const TagsWrap = styled.div`
  margin-top: 4px;
  margin-bottom: 5px;
  .danger {
    border-radius: 9px;
    border: solid 0.8px #ffa39e;
    background-color: #fff1f0;
    color: #ff4d4f;
    line-height: 1.5;
    margin-right: 4px;
  }
  .normal {
    border-radius: 9px;
    border: solid 0.8px #c5cee0;
    background-color: rgba(143, 155, 179, 0.15);
    color: #222b45;
    line-height: 1.5;
    margin-right: 4px;
    margin-bottom: 3px;
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
  padding: 0 3px 2px !important;
  width: 108px;
  li {
    margin-top: 0 !important;
    margin-bottom: 0 !important;
    border-radius: 8px;
    background-color: initial !important;
    color: rgba(0, 0, 0, 0.85);
    text-align: center !important;
    &.ant-menu-item:hover {
      border-radius: 8px;
      background-color: #f5f5f5 !important;
      color: rgba(0, 0, 0, 0.85) !important;

      > button {
        border-color: transparent !important;
        background-color: #f5f5f5 !important;
      }
    }
  }
  .ant-menu-item-group-list .ant-menu-item {
    padding: 0;
  }
  .ant-menu-item-group-title {
    color: #828991;
    margin-bottom: 2px;
    padding: 2px 12px;
    border-bottom: 1px solid #edf1f7;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: left;
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
      const {
        id,
        birth,
        patientId,
        patientName,
        phone,
        doctor,
        note,
        status,
        registrationStatus,
        tags,
        firstVisit,
        baseFloor,
      } = appointment;
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

        const sortTags = { dangerTags: [], normalTags: [] };
        if (tags) {
          // TODO: id 33 & 44 have same value but different type
          const ids = [];
          tags.forEach(t => ids.push(t.id));
          tags.forEach(tag => {
            if (
              tag.type === 'BLOOD_DISEASE' ||
              (tag.type === 'OTHER' && tag.id === 9999) ||
              (tag.type === 'OTHER' && tag.id === 25)
            ) {
              sortTags.dangerTags.push(tag);
            } else if (tag.id === 33) {
              ids.indexOf(44) === -1 && sortTags.dangerTags.push(tag);
            } else {
              sortTags.normalTags.push(tag);
            }
          });
        }

        const popoverContent = (
          <PopoverContainer>
            <HightLightSpan birth={birth}>{birth ? parseDateToString(birth, false) : '生日未填'}</HightLightSpan>
            <span className="name">
              {status === 'CANCEL' ? '[C]' : null} {firstVisit ? '[N] ' : null}
              {patientName}
            </span>
            <InfoWrap>
              <span className="info">
                <img src={PhoneFillIcon} alt="phone-icon" className="info-icon" />
                {phone}
              </span>
              <span className="info">
                <img src={PersonFillIcon} alt="person-icon" className="info-icon" />
                {doctor.user.firstName}
              </span>
              {note && (
                <span className="info">
                  <img src={FileTextFillIcon} alt="file-icon" className="info-icon" />
                  {note}
                </span>
              )}
            </InfoWrap>
            <TagsWrap>
              {baseFloor && (
                <Tag key={id} className="danger">
                  行動不便
                </Tag>
              )}
              {sortTags.dangerTags.map(({ name, id }) => (
                <Tag key={id} className="danger">
                  {name}
                </Tag>
              ))}
              {sortTags.normalTags.map(({ name, type, id }) => (
                <Tag key={id} className="normal">
                  {type === 'ALLERGY' ? `${name}過敏` : name}
                </Tag>
              ))}
            </TagsWrap>
            <div className="btn-wrap">
              <div className="xray-container">
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
              </div>
              <a
                className="link"
                href={`${getBaseUrl()}#/patient/${patientId}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                關於病患
              </a>
            </div>
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
              <Menu.ItemGroup title={patientName}>
                <Menu.Item key="edit" className="first-li">
                  編輯預約
                </Menu.Item>
              </Menu.ItemGroup>
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
