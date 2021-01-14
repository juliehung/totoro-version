import React from 'react';
import styled from 'styled-components';
import man from '../../images/Man.png';
import woman from '../../images/Woman.png';
import defaultAvatar from '../../images/Default.png';
import VisionImg from '../../component/VisionImg';
import VixWinImg from '../../component/VixWinImg';
import { connect } from 'react-redux';
import { convertPatientToHeaderObject } from './utils';
import { openXray } from '../Home/actions';
import { XRAY_VENDORS } from '../AppointmentPage/constant';
import banner from '../../images/banner.svg';
import editIcon from '../../images/edit-2-fill.svg';
import { getBaseUrl } from '../../utils/getBaseUrl';
import { parsePatientNameWithVipMark } from '../../utils/patientHelper';

//#region
const Container = styled.div`
  overflow: hidden;
  border-radius: 8px;
  display: flex;
  z-index: 400;
  top: 0;
  position: sticky;
`;

const InfoContainer = styled.div`
  flex-grow: 1;
  height: 60px;
  font-size: 20px;
  color: #fff;
  display: flex;
  align-items: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-image: url(${banner});
  background-blend-mode: lighten, multiply;

  & > :first-child {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 24px;
    & > * {
      margin: 0px 10px;
    }
  }
  @media (max-width: 600px) {
    font-size: 15px;
    & > :first-child {
      margin-left: 1%;
      display: grid;
      & > img {
        display: none;
      }
    }
  }

  & > :nth-child(2) {
    margin-left: auto;
    margin-right: 21px;
    font-size: 14px;
    font-weight: 600;
    border-radius: 16px;
    color: #fff;
    background-color: #ff9ca8;
    padding: 5px 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    & > img {
      height: 14px;
      margin-right: 8px;
    }
    @media (max-width: 600px) {
      margin-right: 2%;
    }
  }
`;

const XrayContainer = styled.div`
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 20px;
  & > div {
    &:not(:last-child) {
      margin-right: 10px;
    }
    & > img {
      width: 29px;
      cursor: pointer;
    }
  }
  &:empty {
    padding: 0;
  }

  @media (max-width: 1100px) {
    display: none;
  }
`;
//#endregion

function PatientDetailHeader(props) {
  const { patient, openXray, xRayVendors, lastestDocNpId } = props;

  const AvatarSrc = patient.gender === 'MALE' ? man : patient.gender === 'FEMALE' ? woman : defaultAvatar;

  const handleXrayClick = (vendor, xrayPatientObj) => {
    openXray({ vendor, appointment: xrayPatientObj });
  };

  return (
    <Container>
      <InfoContainer>
        <div>
          <img src={AvatarSrc} height="30" alt="man" />
          <span>{parsePatientNameWithVipMark(patient.vipPatient, patient.name)}</span>
          <span>
            {patient.ROCBirth} ({patient?.age?.year}Y{patient?.age?.month}M)
          </span>
        </div>
        <a
          href={lastestDocNpId ? `${getBaseUrl()}#/q/history/${lastestDocNpId}` : `${getBaseUrl()}#/q/${patient.id}`}
          target="_blank"
          rel="noopener noreferrer"
        >
          <img src={editIcon} alt="editIcon" />
          <span>病歷表首頁</span>
        </a>
      </InfoContainer>
      <XrayContainer>
        {xRayVendors?.[XRAY_VENDORS.vision] === 'true' && (
          <div
            onClick={() => {
              const xrayPatientObj = {
                medicalId: patient.medicalId,
                nationalId: patient.nationalId,
                patientName: patient.name,
                birth: patient.birth,
                gender: patient.gender,
              };
              handleXrayClick(XRAY_VENDORS.vision, xrayPatientObj);
            }}
          >
            <VisionImg />
          </div>
        )}
        {xRayVendors?.[XRAY_VENDORS.vixwin] === 'true' && (
          <div
            onClick={() => {
              const xrayPatientObj = {
                medicalId: patient.medicalId,
                nationalId: patient.nationalId,
                patientName: patient.name,
                birth: patient.birth,
                gender: patient.gender,
              };
              handleXrayClick(XRAY_VENDORS.vixwin, xrayPatientObj);
            }}
          >
            <VixWinImg />
          </div>
        )}
      </XrayContainer>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer, settingPageReducer }) => ({
  patient: convertPatientToHeaderObject(patientPageReducer.patient.patient),
  xRayVendors: settingPageReducer.configurations.config.xRayVendors,
  lastestDocNpId: patientPageReducer.docNpHistory.docNps
    ?.map?.(d => d.id)
    ?.reduce?.((a, b) => {
      return Math.max(a, b);
    }, null),
});

const mapDispatchToProps = { openXray };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailHeader);
