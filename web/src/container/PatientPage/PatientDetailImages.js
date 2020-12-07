import React, { Fragment, useState } from 'react';
import { connect } from 'react-redux';
import { Empty, Spin } from 'antd';
import styled from 'styled-components';
import ImageGallery from 'react-image-gallery';
import { Container, Header } from './component';
import { changePageSizeToGetPatientImages, updatePatientImagesIndex } from './actions';
import { ReactComponent as LeftArrowSvg } from '../../images/icon-arrow-circle-left.svg';
import { ReactComponent as RightArrowSvg } from '../../images/icon-arrow-circle-right.svg';
import { ReactComponent as FullscreenExpandSvg } from '../../images/icon-fullscreen-expand.svg';
import { convertPatientImagesToImageGallery } from './utils';

//#region
const Content = styled.div`
  max-height: 560px;
  height: 100%;
  min-height: 560px;
  padding: ${props => (props.noPadding ? 0 : '10px')};
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }

  .spin-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
    height: ${props => (props.isFullScreen ? '820px' : '560px')} !important;
  }

  .image-gallery-slide img {
    max-width: 100% !important;
    max-height: ${props => (props.isFullScreen ? '820px' : '520px')} !important;
    padding-bottom: 25px;
  }
  .image-gallery-custom-right-nav,
  .image-gallery-custom-left-nav {
    position: absolute;
    z-index: 4;
    top: 50%;
    transform: translate(0, -50%);
    background-color: transparent;
    border: 0;
    cursor: pointer;
    outline: none;
  }
  .image-gallery-custom-left-nav {
    left: 24px;
  }
  .image-gallery-custom-right-nav {
    right: 24px;
  }
  .image-gallery-custom-fullscreen-button {
    position: absolute;
    z-index: 4;
    right: 33px;
    bottom: 35px;
    background-color: transparent;
    border: 0;
    cursor: pointer;
    outline: none;
  }

  .upload-date-wrap {
    position: absolute;
    z-index: 4;
    bottom: 28px;
    left: 22px;
    color: #222b45;
    font-size: 15px;
    line-height: 1.33;
  }
`;
//#endregion

function PatientDetailImages(props) {
  const {
    id,
    patientImagesData,
    page,
    size,
    currentIndex,
    loading,
    changePageSizeToGetPatientImages,
    updatePatientImagesIndex,
    isFetchEmpty,
  } = props;
  const [isFullScreen, switchFullScreen] = useState(false);

  return (
    <Container>
      <Header>
        <div>
          <span>影像</span>
        </div>
      </Header>
      <Content isFullScreen={isFullScreen}>
        {loading ? (
          <Spin className="spin-wrap" />
        ) : patientImagesData?.patientImages && patientImagesData?.patientImages.length > 0 ? (
          <Fragment>
            <ImageGallery
              items={patientImagesData?.patientImages}
              startIndex={currentIndex}
              infinite={false}
              lazyLoad={true}
              thumbnailPosition={'right'}
              showPlayButton={false}
              disableThumbnailScroll={true}
              disableKeyDown={true}
              onScreenChange={() => switchFullScreen(!isFullScreen)}
              renderLeftNav={(onClick, disabled) =>
                !loading ? (
                  <button
                    className="image-gallery-custom-left-nav"
                    onClick={() => {
                      if (!disabled) {
                        updatePatientImagesIndex(currentIndex - 1);
                      }
                    }}
                  >
                    <LeftArrowSvg />
                  </button>
                ) : null
              }
              renderRightNav={(onClick, disabled) => {
                return !loading ? (
                  <button
                    className="image-gallery-custom-right-nav"
                    onClick={() => {
                      if (!disabled) {
                        updatePatientImagesIndex(currentIndex + 1);
                      } else {
                        if (!isFetchEmpty) {
                          changePageSizeToGetPatientImages(id, page + 1, size, currentIndex + 1);
                        }
                      }
                    }}
                  >
                    <RightArrowSvg />
                  </button>
                ) : null;
              }}
              renderFullscreenButton={onClick =>
                !loading ? (
                  <button className="image-gallery-custom-fullscreen-button" onClick={() => onClick()}>
                    <FullscreenExpandSvg />
                  </button>
                ) : null
              }
            />
            <div className="upload-date-wrap">上傳於 {patientImagesData?.currentImageDate}</div>
          </Fragment>
        ) : (
          <Empty description="沒有資料" />
        )}
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  patientImagesData: convertPatientImagesToImageGallery(
    patientPageReducer.patientImages.patientImages,
    patientPageReducer.patientImages.currentIndex,
  ),
  loading: patientPageReducer.patientImages.loading,
  page: patientPageReducer.patientImages.page,
  size: patientPageReducer.patientImages.size,
  currentIndex: patientPageReducer.patientImages.currentIndex,
  isFetchEmpty: patientPageReducer.patientImages.isFetchEmpty,
});

const mapDispatchToProps = { changePageSizeToGetPatientImages, updatePatientImagesIndex };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailImages);
