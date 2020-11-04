import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { gotoPage } from './actions';
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
  left: 0;
  bottom: 0;
  user-select: none;
  & > :last-child {
    margin-left: 20px;
  }
`;

const StyledButton = styled(Button)`
  border-radius: 4px !important;
`;
//#endregion

function PreviewControll(props) {
  const { patient, gotoPage } = props;

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

  return (
    <Container>
      <StyledButton
        disabled={disable}
        onClick={() => {
          gotoPage(20);
        }}
        size="large"
      >
        醫療人員預覽送出
      </StyledButton>
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => {
  const currentPageObj = pages.find(p => p.page === questionnairePageReducer.flow.page);

  return {
    currentPage: questionnairePageReducer.flow.page,
    validator: currentPageObj?.validator,
    isLast: currentPageObj?.isLast,
    patient: questionnairePageReducer.data.patient,
    error: questionnairePageReducer.flow.validationError,
  };
};

const mapDispatchToProps = { gotoPage };

export default connect(mapStateToProps, mapDispatchToProps)(PreviewControll);
