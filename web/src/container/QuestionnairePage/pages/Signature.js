import React, { useRef } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import SignatureCanvas from 'react-signature-canvas';
import { CheckOutlined } from '@ant-design/icons';
import { Button } from 'antd';
import { changeIsSigEmpty, createQWSign, createQWOSign } from '../actions';
import { trimDataUrl } from '../utils/trimDataUrl';

//#region
const Container = styled.div`
  max-width: 900px;
  width: 95%;
  position: relative;
`;

const HeadContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.p`
  color: #222b45;
  font-size: 26px;
  font-weight: 600;
`;

const SubTitle = styled.p`
  color: rgb(130, 137, 145);
  font-size: 14px;
`;

const SigContainer = styled.div`
  border: 1px solid #dadada;
  border-radius: 3px;
  position: relative;
  height: 450px;
  .copy {
    position: absolute;
    z-index: -2;
    left: 0;
  }
`;

const SigNotification = styled.div`
  position: absolute;
  top: 55%;
  left: 0;
  text-align: right;
  padding: 0 15.5px;
  width: 100%;
  z-index: -1;
  .notification {
    color: #8f9bb3;
    font-size: 26px;
    margin-bottom: 11.5px;
    margin-top: 11.5px;
  }
  .line {
    height: 1px;
    background-color: #c5cee0;
  }
`;

const ClearButton = styled(Button)`
  position: absolute !important;
  left: 10px;
  top: 10px;
`;
//#endregion

function Signature(props) {
  let sigRef = useRef(null);
  let copyRef = useRef(null);
  const clearSig = () => {
    sigRef.clear();
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  const onEnd = () => {
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  const SendWSign = () => {
    const getOriginalSign = sigRef.toData();
    copyRef.fromData(getOriginalSign);
    const imgURL = trimDataUrl(copyRef.toDataURL());
    props.createQWSign({ base64: imgURL, contentType: 'image/png' });
  };

  return (
    <Container>
      <HeadContainer>
        <div>
          <Title>數位簽章</Title>
          <SubTitle>請簽下您的姓名以及當天日期</SubTitle>
        </div>
        {props.isEmpty ? (
          <Button size="large" onClick={props.createQWOSign} loading={props.sendLoading}>
            不簽名直接送出
          </Button>
        ) : (
          <Button size="large" type="primary" onClick={SendWSign} loading={props.sendLoading}>
            送出
            <CheckOutlined />
          </Button>
        )}
      </HeadContainer>
      <SigNotification>
        <div className="notification">（簽名）</div>
        <div className="line" />
        <div className="notification">（年月日）</div>
      </SigNotification>
      <SigContainer>
        <ClearButton onClick={clearSig}>清除全部</ClearButton>
        <SignatureCanvas
          minDistance={0.1}
          velocityFilterWeight={0.5}
          canvasProps={{
            style: { width: '100%', height: '100%' },
            className: 'original',
          }}
          ref={ref => (sigRef = ref)}
          onEnd={onEnd}
        />
        <SignatureCanvas
          minDistance={0.1}
          velocityFilterWeight={0.5}
          canvasProps={{
            style: { width: '100%', height: '100%' },
            className: 'copy',
          }}
          ref={ref => (copyRef = ref)}
          backgroundColor={'#fff'}
        />
      </SigContainer>
    </Container>
  );
}
const mapStateToProps = ({ questionnairePageReducer }) => ({
  visible: questionnairePageReducer.flow.signatureModalVisible,
  isEmpty: questionnairePageReducer.flow.isSigEmpty,
  sendLoading: questionnairePageReducer.flow.sendLoading,
});

const mapDispatchToProps = { changeIsSigEmpty, createQWSign, createQWOSign };

export default connect(mapStateToProps, mapDispatchToProps)(Signature);
