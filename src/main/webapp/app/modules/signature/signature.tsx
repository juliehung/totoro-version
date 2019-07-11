import React from 'react';
import { Button } from 'reactstrap';
import SignatureCanvas from 'react-signature-canvas';
import './signature.css';
import axios from 'axios';

interface ISignatureProps {
  imgType?: string;
  getSignatureURL?: (arg0: string) => void;
  pid?: number;
}

interface ISignatureSates {
  trimmedDataURL: string;
}

class Signature extends React.Component<ISignatureProps, ISignatureSates> {
  sigCanvas = {
    clear: () => {},
    toDataURL: (params: any) => '',
    fromDataURL: (base64String: string, option: any) => {}
  };

  state = {
    trimmedDataURL: ''
  };

  componentDidMount() {
    let base64Img: string;
    axios
      .get(`/api/lob/esign?patientId.equals=${this.props.pid}`)
      .then(response => {
        base64Img = response.data.base64;
        this.sigCanvas.fromDataURL(base64Img, { width: 713, height: 320 });
      })
      .catch();
  }

  clearSignature = () => {
    this.sigCanvas.clear();
  };

  trim = () => {
    const imgURL: string = this.sigCanvas.toDataURL(this.props.imgType ? `image/${this.props.imgType}` : 'image/png');
    return imgURL;
  };

  render() {
    return (
      <div className="signatureContainer">
        <div className="signatureBorder">
          <div className="signatureText">
            <p className="signatureTextBig">簽名 Signature</p>
            <p className="signatureTextSmall">未滿20歲須法定代理人簽名同意</p>
          </div>
          <div className="clearSignature">
            <Button
              id="clearSiganture"
              style={{ width: '100px' }}
              className="btn btn-primary"
              color="secondary"
              onClick={this.clearSignature}
            >
              重新填寫
            </Button>
          </div>
          <SignatureCanvas
            penColor="black"
            throttle={4}
            minDistance={0.1}
            velocityFilterWeight={0.5}
            canvasProps={{
              width: '713',
              height: '320',
              className: 'sigCanvas'
            }}
            backgroundColor="rgba(194,203,213,0.1)"
            ref={(ref: any) => {
              this.sigCanvas = ref;
            }}
          />
        </div>
      </div>
    );
  }
}

export default Signature;
