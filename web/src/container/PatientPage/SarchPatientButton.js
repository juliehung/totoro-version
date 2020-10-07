import React from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import search from '../../images/search.svg';
import { changeDrawerVisible } from './actions';

//#region

const Container = styled.div`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #3266ff;
  position: fixed;
  right: 20px;
  bottom: 20px;
  z-index: 100000;
  z-index: 400;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

//#endregion

function SarchPatientButton(props) {
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

export default connect(null, mapDispatchToProps)(SarchPatientButton);
