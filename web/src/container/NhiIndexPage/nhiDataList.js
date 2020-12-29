import React from 'react';
import { Checkbox } from 'antd';
import LazyLoad from 'react-lazyload';
import styled from 'styled-components';

const EachWrap = styled.div`
  ${props =>
    props.isSelected &&
    `
   background-image: linear-gradient(283deg, #0a54c5, #3266ff);
   box-shadow: 0 15px 30px 0 rgba(48, 101, 252, 0.11);
   > div {
    * {
      color: #fff !important;
    }
   }
  `};
`;
const CheckboxWrap = styled(Checkbox)`
  .ant-checkbox-checked .ant-checkbox-inner {
    ${props =>
      props.isSelected &&
      `
       background-color: #fff;
       border-color: #fff;
       color: #222b45;

       &::after {
        border-color: #222b45;
       }
    `}
  }
`;

const NhiDataListRender = ({
  validNhiData,
  checkedModalData,
  updateCheckedModalData,
  onNhiDataOneSelect = () => {},
  nhiOne,
  currentNhiOne,
  updateCurrentNhiOne,
}) => {
  return Object.entries(validNhiData).map(([key, list], index) => (
    <div key={`validNhiData-${index}`} className="render-each-nhi-data-wrap">
      <div className="nhi-data-list-date-header">{key}</div>
      <div>
        {list.map(({ disposalId, nhiExtendDisposal }, index) => (
          <LazyLoad key={`render-each-nhi-wrap-${index}`}>
            <EachWrap
              key={`render-each-nhi-wrap-${index}`}
              className="render-each-nhi-wrap"
              isSelected={currentNhiOne === disposalId}
            >
              <div>
                <div>
                  <CheckboxWrap
                    checked={checkedModalData.find(id => disposalId === id)}
                    isSelected={currentNhiOne === disposalId}
                    onChange={() => {
                      checkedModalData.find(id => disposalId === id)
                        ? updateCheckedModalData(checkedModalData.filter(id => disposalId !== id))
                        : updateCheckedModalData(oldArray => [...oldArray, disposalId]);
                    }}
                  />
                </div>
              </div>
              <div
                onClick={() => {
                  updateCurrentNhiOne(disposalId);
                  if (nhiOne?.id !== disposalId) {
                    onNhiDataOneSelect(disposalId);
                  }
                }}
              >
                <div>{nhiExtendDisposal?.patientName}</div>
                <div>
                  {disposalId}, {nhiExtendDisposal?.doctorName}
                </div>
              </div>
              <div
                onClick={() => {
                  updateCurrentNhiOne(disposalId);
                  if (nhiOne?.id !== disposalId) {
                    onNhiDataOneSelect(disposalId);
                  }
                }}
              >
                <div>
                  {nhiExtendDisposal?.a13 &&
                    nhiExtendDisposal?.a13.length !== 0 &&
                    `${parseInt(nhiExtendDisposal?.a13.slice(0, 3))}/${parseInt(
                      nhiExtendDisposal?.a13.slice(3, 5),
                    )}/${parseInt(nhiExtendDisposal?.a13.slice(5))}`}
                </div>
              </div>
            </EachWrap>
          </LazyLoad>
        ))}
      </div>
    </div>
  ));
};

export default NhiDataListRender;
