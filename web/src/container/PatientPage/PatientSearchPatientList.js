import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import man from '../../images/Man.png';
import woman from '../../images/Woman.png';
import defaultAvatar from '../../images/Default.png';
import { Tooltip } from 'antd';
import PatientSearchPatientListText from './PatientSearchPatientListText';
import { toRocString } from './utils';

//#region
const Container = styled.div`
  height: 60vh;
  overflow-y: scroll;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const LimitWidthItem = styled.div`
  max-width: 756px;
  width: 90%;
  min-width: 375px;
`;

const ListContainer = styled.div`
  width: 100%;
  margin-top: 10px;
`;

const ItemContainer = styled(Link)`
  background-color: #ffffff;
  height: 55px;
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;

  & > div {
    display: grid;
    grid-template-columns: 0.3fr 0.7fr 1fr 1fr 1fr 1fr;
    grid-column-gap: 5px;

    & > * {
      display: grid;
      place-items: center;
      font-size: 15px;
      color: #222b45;
    }
    & > :first-child {
      vertical-align: middle;
      width: 30px;
      height: 30px;
      border-radius: 50%;
    }
    & > span {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    & > span:first-of-type {
      font-size: 15px;
      font-weight: 600;
      color: #222b45;
    }
  }

  &:hover {
    background-color: #f7f9fc;
  }
`;

//#endregion

function PatientSearchPatientList(props) {
  const { patients = [], changeDrawerVisible, keyword = '' } = props;

  return (
    <Container>
      <LimitWidthItem>
        <span>符合搜尋條件的病患：</span>
      </LimitWidthItem>
      <ListContainer>
        {patients.map(p => (
          <ItemContainer
            key={p.id}
            to={`/patient/${p.id}`}
            onClick={() => {
              changeDrawerVisible(false);
            }}
          >
            <LimitWidthItem>
              <img src={p.gender === 'MALE' ? man : p.gender === 'FEMALE' ? woman : defaultAvatar} alt="avatar" />
              <Tooltip title={p.name}>
                <PatientSearchPatientListText text={p.name} keyword={keyword} />
              </Tooltip>
              <Tooltip title={p.birth}>
                <PatientSearchPatientListText text={toRocString(p.birth)} keyword={keyword} isBirth />
              </Tooltip>
              <Tooltip title={p.phone}>
                <PatientSearchPatientListText text={p.phone} keyword={keyword} />
              </Tooltip>
              <Tooltip title={p.medicalId}>
                <PatientSearchPatientListText text={p.medicalId} keyword={keyword} />
              </Tooltip>
              <Tooltip title={p.nationalId}>
                <PatientSearchPatientListText text={p.nationalId} keyword={keyword} />
              </Tooltip>
            </LimitWidthItem>
          </ItemContainer>
        ))}
      </ListContainer>
    </Container>
  );
}

export default PatientSearchPatientList;
