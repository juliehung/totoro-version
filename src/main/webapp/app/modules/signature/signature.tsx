import React from 'react';
import { Button } from 'reactstrap';
import SignatureCanvas from 'react-signature-canvas';
import './signature.css';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEraser } from '@fortawesome/free-solid-svg-icons';
library.add(faEraser);

interface ISignatureProps {
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

  clearSignature = () => {
    this.sigCanvas.clear();
  };

  trim = () => {
    const imgURL: string = this.sigCanvas.toDataURL('image/png');
    return imgURL;
  };

  render() {
    return (
      <div className="signatureContainer">
        <div className="signatureBorder">
          <div className="clearSignature">
            <div
              id="clearSignature"
              style={{
                width: '96px',
                height: '40px',
                borderRadius: '3px',
                border: 'solid 1px #d0d7df',
                display: 'flex',
                justifyContent: 'space-around'
              }}
              onClick={this.clearSignature}
            >
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <div style={{ marginRight: '5px' }}>
                  <FontAwesomeIcon icon="eraser" size="lg" color="#293f50" />
                </div>
                <p style={{ fontSize: '14px', color: '#293f50', fontWeight: 600, lineHeight: '40px', marginTop: 'auto' }}>清除</p>
              </div>
            </div>
          </div>
          <SignatureCanvas
            penColor="black"
            throttle={4}
            minDistance={0.1}
            velocityFilterWeight={0.5}
            canvasProps={{
              width: '598',
              height: '622',
              className: 'sigCanvas'
            }}
            backgroundColor="#fff"
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
