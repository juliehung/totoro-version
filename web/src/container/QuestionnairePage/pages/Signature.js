import React, { useRef } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import SignatureCanvas from 'react-signature-canvas';
import { Button, Icon } from 'antd';
import { changeIsSigEmpty, createQWSign, createQWOSign } from '../actions';
import { trimDataUrl } from '../utils/trimDataUrl';

//#region
const Container = styled.div`
  max-width: 900px;
  width: 95%;
  background: transparent;
`;

const HeadContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.p`
  color: rgb(25, 155, 255);
  font-size: 24px;
  font-weight: 600;
`;

const SubTitle = styled.p`
  color: rgb(130, 137, 145);
  font-size: 14px;
`;

const SigContainer = styled.div`
  border: 1px solid #dadada;
  background: #f5f5f5;
  border-radius: 3px;
  position: relative;
  height: 450px;
`;

const ClearButton = styled(Button)`
  position: absolute !important;
  left: 10px;
  top: 10px;
`;
//#endregion

function Signature(props) {
  let sigRef = useRef(null);

  const clearSig = () => {
    sigRef.clear();
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  const onEnd = () => {
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  const SendWSign = () => {
    const imgURL = trimDataUrl(sigRef.toDataURL());
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
            <Icon type="check" />
          </Button>
        ) : (
          <Button size="large" type="primary" onClick={SendWSign} loading={props.sendLoading}>
            送出
            <Icon type="check" />
          </Button>
        )}
      </HeadContainer>
      <SigContainer>
        <ClearButton onClick={clearSig}>清除</ClearButton>
        <SignatureCanvas
          minDistance={0.1}
          velocityFilterWeight={0.5}
          canvasProps={{
            style: { width: '100%', height: '100%' },
          }}
          ref={ref => (sigRef = ref)}
          onEnd={onEnd}
          backgroundColor="#fff"
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
