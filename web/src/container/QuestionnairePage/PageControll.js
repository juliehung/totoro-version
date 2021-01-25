import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { DownOutlined, UpOutlined } from '@ant-design/icons';
import { nextPage, prevPage, gotoPage, validateSuccess, valitationFail, changeFinishModalVisible } from './actions';
import { connect } from 'react-redux';
import pages from './pages';
import * as validators from './utils/validators';
import { Button } from 'antd';

//#region
const Container = styled.div`
  z-index: 9999;
  display: flex;
  justify-content: flex-end;
  font-size: 20px;
  color: #d0d7df;
  position: absolute;
  right: 0;
  top: 0;
  user-select: none;
  & > :last-child {
    margin-left: 30px;
  }
`;

const StyledButton = styled(Button)`
  border-radius: 4px !important;
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
    isLast,
    changeFinishModalVisible,
    isPatientExist,
    existedPatientId,
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
      const validation = validator(patient, isPatientExist, existedPatientId);
      if (!validation) {
        valitationFail(currentPage);
        return;
      }
      validateSuccess(currentPage);
    }

    nextPage
      ? typeof nextPage === 'number'
        ? gotoPage(nextPage)
        : typeof nextPage === 'function'
        ? gotoPage(nextPage(patient))
        : goNextPage()
      : goNextPage();
  };

  return (
    <Container>
      <StyledButton icon={<UpOutlined />} onClick={prevPageClick} size="large">
        上一題
      </StyledButton>
      {isLast ? (
        <StyledButton
          icon={<DownOutlined />}
          type="primary"
          onClick={() => changeFinishModalVisible(true)}
          size="large"
        >
          完成
        </StyledButton>
      ) : (
        <StyledButton icon={<DownOutlined />} type="primary" onClick={nextPageClick} size="large">
          下一題
        </StyledButton>
      )}
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => {
  const currentPageObj = pages.find(p => p.page === questionnairePageReducer.flow.page);

  return {
    currentPage: questionnairePageReducer.flow.page,
    nextPage: currentPageObj?.nextPage,
    prevPage: currentPageObj?.prevPage,
    validator: currentPageObj?.validator,
    isLast: currentPageObj?.isLast,
    patient: questionnairePageReducer.data.patient,
    error: questionnairePageReducer.flow.validationError,
    isPatientExist: questionnairePageReducer.data.isPatientExist,
    existedPatientId: questionnairePageReducer.data.existedPatientId,
  };
};

const mapDispatchToProps = {
  goNextPage: nextPage,
  goPrevPage: prevPage,
  gotoPage,
  validateSuccess,
  valitationFail,
  changeFinishModalVisible,
};

export default connect(mapStateToProps, mapDispatchToProps)(PageControll);
