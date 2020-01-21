import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Icon } from 'antd';
import { nextPage, prevPage, gotoPage } from './actions';
import { connect } from 'react-redux';

const Container = styled.div`
  display: flex;
  justify-content: flex-end;
  font-size: 20px;
  color: #d0d7df;
  position: absolute;
  right: 0;
  bottom: 0px;
  & > div {
    border: 1px solid rgb(208, 215, 223);
    box-shadow: 0px 2px 9px 0px rgba(23, 104, 172, 0.13);
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
  & > div:not(:nth-child(2)) {
    font-size: 20px;
    width: 35px;
    height: 35px;
    margin: 0 10px;
  }
`;

const ConfirmButton = styled.div`
  font-size: 16px;
  padding: 4px 20px;
  color: ${props => (props.disabled ? ' #d0d7df' : 'rgb(77, 161, 255)')};
`;

function PageControllContainer(props) {
  const [disable, setDisable] = useState(false);
  const { patient } = props;
  useEffect(() => {
    setDisable(!patient.name || patient.name.length === 0 || !patient.phone || patient.phone.length === 0);
  }, [disable, setDisable, patient]);

  return (
    <Container page={props.page} id="pc">
      <div onClick={props.pre || props.prevPage}>
        <Icon type="up" />
      </div>
      <ConfirmButton
        disabled={disable}
        onClick={() => {
          if (!disable) {
            props.gotoPage(20);
          }
        }}
      >
        <span>完成送出</span>
      </ConfirmButton>
      <div onClick={props.next || props.nextPage}>
        <Icon type="down" />
      </div>
    </Container>
  );
}

const mapStateToProps = state => ({
  page: state.questionnairePageReducer.flow.page,
  patient: state.questionnairePageReducer.data.patient,
});

const mapDispatchToProps = { nextPage, prevPage, gotoPage };

export default connect(mapStateToProps, mapDispatchToProps)(PageControllContainer);
