import React from 'react';
import { connect } from 'react-redux';
import { changeFinishModalVisible } from './actions';
import { Modal } from 'antd';
import styled from 'styled-components';

//#region
const StyledModal = styled(Modal)`
  & .ant-modal-content {
    border-radius: 8px;
  }

  & .ant-modal-close {
    &:active {
      transform: translateY(2px) translateX(-2px);
    }
  }
  & .ant-modal-close-x {
    width: 32px;
    height: 32px;
    position: absolute;
    top: -10px;
    right: -10px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 5px 20px 0 rgba(0, 0, 0, 0.1);
  }
  & .ant-modal-close-icon {
    display: flex;
    height: 100%;
    justify-content: center;
    align-items: center;
    & > svg {
      fill: black;
    }
  }
`;
//#endregion

function FinishModal(props) {
  const { finishModalVisible, changeFinishModalVisible } = props;

  return (
    <StyledModal
      visible={finishModalVisible}
      onCancel={() => {
        changeFinishModalVisible(false);
      }}
      title="感謝填寫"
      footer={null}
      centered
    >
      <p>感謝您的填寫，請將表單交給醫療人員。</p>
    </StyledModal>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  finishModalVisible: questionnairePageReducer.flow.finishModalVisible,
});

const mapDispatchToProps = {
  changeFinishModalVisible,
};

export default connect(mapStateToProps, mapDispatchToProps)(FinishModal);
