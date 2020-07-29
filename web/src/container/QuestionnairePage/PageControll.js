import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { DownOutlined, UpOutlined } from '@ant-design/icons';
import { nextPage, prevPage, gotoPage, validateSuccess, valitationFail } from './actions';
import { connect } from 'react-redux';
import pages from './pages';
import * as validators from './utils/validators';

//#region
const Container = styled.div`
  z-index: 9999;
  display: flex;
  justify-content: flex-end;
  font-size: 20px;
  color: #d0d7df;
  position: absolute;
  right: 0;
  bottom: 0;
  user-select: none;
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
    margin: 0 36px;
  }
`;

const ConfirmButton = styled.div`
  font-size: 16px;
  padding: 4px 20px;
  color: ${props => (props.disabled ? ' #d0d7df' : 'rgb(77, 161, 255)')};
`;
//#endregion

function PageControll(props) {
  const {
    patient,
    gotoPage,
    nextPage,
    prevPage,
    validator,
    currentPage,
    validateSuccess,
    valitationFail,
    goPrevPage,
    goNextPage,
  } = props;

  const [disable, setDisable] = useState(false);

  useEffect(() => {
    for (const validator in validators) {
      if (!validators[validator](patient)) {
        setDisable(true);
        return;
      }
      setDisable(false);
    }
  }, [disable, setDisable, patient]);

  const prevPageClick = () => {
    prevPage ? gotoPage(prevPage) : goPrevPage();
  };

  const nextPageClick = () => {
    if (validator) {
      const validation = validator(patient);
      if (!validation) {
        valitationFail(currentPage);
        return;
      }
      validateSuccess(currentPage);
    }
    nextPage ? gotoPage(nextPage) : goNextPage();
  };

  return (
    <Container>
      <div onClick={prevPageClick}>
        <UpOutlined />
      </div>
      <ConfirmButton
        disabled={disable}
        onClick={() => {
          if (!disable) {
            gotoPage(20);
          }
        }}
      >
        <span>完成送出</span>
      </ConfirmButton>
      <div onClick={nextPageClick}>
        <DownOutlined />
      </div>
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  currentPage: questionnairePageReducer.flow.page,
  nextPage: pages.find(p => p.page === questionnairePageReducer.flow.page)?.nextPage,
  prevPage: pages.find(p => p.page === questionnairePageReducer.flow.page)?.prevPage,
  validator: pages.find(p => p.page === questionnairePageReducer.flow.page)?.validator,
  patient: questionnairePageReducer.data.patient,
  error: questionnairePageReducer.flow.validationError,
});

const mapDispatchToProps = { goNextPage: nextPage, goPrevPage: prevPage, gotoPage, validateSuccess, valitationFail };

export default connect(mapStateToProps, mapDispatchToProps)(PageControll);
