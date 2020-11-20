import React from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import search from '../../images/search.svg';
import { changeDrawerVisible } from './actions';

//#region
const Container = styled.div`
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background-color: #3266ff;
  position: fixed;
  right: 20px;
  bottom: 21px;
  z-index: 100000;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  box-shadow: 0 8px 16px -4px rgba(50, 102, 255, 0.4);
`;
//#endregion

function SearchPatientButton(props) {
  const { changeDrawerVisible } = props;

  return (
    <Container
      onClick={() => {
        changeDrawerVisible(true);
      }}
    >
      <img src={search} alt="search" />
    </Container>
  );
}

const mapDispatchToProps = { changeDrawerVisible };

export default connect(null, mapDispatchToProps)(SearchPatientButton);
