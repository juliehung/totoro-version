import React from 'react';
import styled from 'styled-components';
import { Icon } from 'antd';
import { nextPage, prevPage } from './actions';
import { connect } from 'react-redux';

const Container = styled.div`
  display: flex;
  justify-content: flex-end;
  font-size: 20px;
  color: #d0d7df;
  position: absolute;
  right: 0;
  bottom: -20px;
  & > div {
    border: 1px solid rgb(208, 215, 223);
    box-shadow: 0px 2px 9px 0px rgba(23, 104, 172, 0.13);
    width: 35px;
    height: 35px;
    margin: 0 10px;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

function PageControllContainer(props) {
  return (
    <Container page={props.page} id="pc">
      <div onClick={props.pre || props.prevPage}>
        <Icon type="up" />
      </div>
      <div onClick={props.next || props.nextPage}>
        <Icon type="down" />
      </div>
    </Container>
  );
}

const mapStateToProps = state => ({ page: state.questionnairePageReducer.flow.page });

const mapDispatchToProps = { nextPage, prevPage };

export default connect(mapStateToProps, mapDispatchToProps)(PageControllContainer);
