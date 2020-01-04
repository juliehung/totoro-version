import React, { useRef } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import SignatureCanvas from 'react-signature-canvas';
import { Button, Icon } from 'antd';
import { changeIsSigEmpty } from '../actions';

//#region
const Container = styled.div`
  max-width: 600px;
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

  const onEnd = e => {
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  return (
    <Container>
      <HeadContainer>
        <div>
          <Title
            onClick={() => {
              const imgURL = sigRef.toDataURL('image/png');
              console.log(imgURL);
            }}
          >
            數位簽章
          </Title>
          <SubTitle>請簽下您的姓名以及當天日期</SubTitle>
        </div>
        <Button size="large" type="primary" disabled={props.isEmpty}>
          送出
          <Icon type="check" />
        </Button>
      </HeadContainer>
      <SigContainer>
        <ClearButton onClick={clearSig}>清除</ClearButton>
        <SignatureCanvas
          penColor="black"
          minDistance={0.1}
          velocityFilterWeight={0.5}
          canvasProps={{
            width: '598',
            height: '400',
            className: 'sigCanvas',
          }}
          ref={ref => (sigRef = ref)}
          onEnd={onEnd}
        />
      </SigContainer>
    </Container>
  );
}
const mapStateToProps = ({ questionnairePageReducer }) => ({
  visible: questionnairePageReducer.flow.signatureModalVisible,
  isEmpty: questionnairePageReducer.data.isSigEmpty,
});

const mapDispatchToProps = { changeIsSigEmpty };

export default connect(mapStateToProps, mapDispatchToProps)(Signature);
