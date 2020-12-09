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
import { ReactComponent as EmptyDataSvg } from '../../images/empty-data.svg';
import { convertPatientImagesToImageGallery } from './utils';

//#region
const Content = styled.div`
  max-height: 400px;
  height: 100%;
  min-height: 400px;
  padding: ${props => (props.noPadding ? 0 : '10px')};
  scrollbar-width: none;

  &::-webkit-scrollbar {
    display: none;
  }

  .image-gallery {
    height: 100%;
    padding-bottom: ${props => (props.isFullScreen ? '0' : '35px')};
  }
  .image-gallery-content,
  .image-gallery-slides,
  .patient-image-wrap {
    height: 100%;
  }
  .patient-image-wrap {
    > div {
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 0 10px;
    }
  }
  .image-gallery-content {
    ${props => props.isFullScreen && 'display: flex'};
    ${props => props.isFullScreen && 'flex-direction: column'};
    ${props => props.isFullScreen && 'padding-bottom: 20px'};
  }
  .image-gallery-slide-wrapper {
    height: ${props => (props.isFullScreen ? '92%' : 'inherit')};
    ${props => props.isFullScreen && 'flex: 0 0 92%'};
  }
  .image-gallery-swipe {
    width: ${props => (props.isFullScreen ? '90%' : '100%')};
    margin: 0 auto;
    height: inherit;
  }
  .image-gallery-thumbnails,
  .image-gallery-thumbnails-container {
    ${props => props.isFullScreen && 'display: flex'};
    ${props => props.isFullScreen && 'justify-content:: center'};
    ${props => props.isFullScreen && 'align-items: center'};
    height: 100%;
    margin: 0 auto;
    > button {
      height: ${props => (props.isFullScreen ? '100%' : '68px')};

      > div {
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;

        img {
          max-width: 100%;
          max-height: 100%;
        }
      }
    }
  }
  .image-gallery-thumbnails-wrapper {
    flex: 0 0 8%;
  }
  .image-gallery-slide img {
    max-width: 100% !important;
    max-height: 100% !important;
  }

  .spin-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
    height: ${props => (props.isFullScreen ? '820px' : '400px')} !important;
  }

  .image-gallery-custom-right-nav,
  .image-gallery-custom-left-nav {
    position: absolute;
    z-index: 4;
    top: 50%;
    transform: translate(0, -50%);
    background-color: transparent;
    border: ${props => (props.isFullScreen ? 'solid 1px #fff' : '0')};
    cursor: pointer;
    outline: none;
    ${props => props.isFullScreen && 'width: 80%'};
    ${props => props.isFullScreen && 'height: 33.33%'};

    svg {
      color: #fff;
    }
    &:hover {
      border: ${props => (props.isFullScreen ? 'solid 1px #337ab7' : '0')};
      svg {
        color: #337ab7;
      }
    }
  }
  .image-gallery-custom-left-nav {
    left: ${props => (props.isFullScreen ? '0' : '24px')};
    ${props => props.isFullScreen && 'border-left: 0'};
    &:hover {
      ${props => props.isFullScreen && 'border-left: 0'};
    }
  }
  .image-gallery-custom-right-nav {
    right: ${props => (props.isFullScreen ? '0' : '24px')};
    ${props => props.isFullScreen && 'border-right: 0'};
    &:hover {
      ${props => props.isFullScreen && 'border-right: 0'};
    }
  }
  .image-gallery-custom-left-nav-wrap,
  .image-gallery-custom-right-nav-wrap {
    ${props => props.isFullScreen && 'position: absolute'};
    ${props => props.isFullScreen && 'width: 100%'};
    ${props => props.isFullScreen && 'height: 100%'};
    ${props => props.isFullScreen && 'left: 0'};
    ${props => props.isFullScreen && 'top: 0'};
    ${props => props.isFullScreen && 'display: flex'};
  }
  .image-gallery-custom-left-nav-wrap {
    > div:nth-child(1) {
      ${props => props.isFullScreen && 'position: relative'};
      ${props => props.isFullScreen && 'flex: 1'};
      ${props => props.isFullScreen && 'height: 100%'};
      ${props => props.isFullScreen && 'display: flex'};
      ${props => props.isFullScreen && 'flex-direction: column'};
      ${props => props.isFullScreen && 'align-items: flex-start'};
    }
    > div:nth-child(2) {
      ${props => props.isFullScreen && 'width: 94%'};
    }
  }
  .image-gallery-custom-right-nav-wrap {
    > div:nth-child(1) {
      ${props => props.isFullScreen && 'width: 94%'};
    }
    > div:nth-child(2) {
      ${props => props.isFullScreen && 'position: relative'};
      ${props => props.isFullScreen && 'flex: 1'};
      ${props => props.isFullScreen && 'height: 100%'};
      ${props => props.isFullScreen && 'display: flex'};
      ${props => props.isFullScreen && 'flex-direction: column'};
      ${props => props.isFullScreen && 'align-items: flex-end'};
    }
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

    svg {
      ${props => props.isFullScreen && 'width: 36px'};
      ${props => props.isFullScreen && 'height: 36px'};
      color: #fff;
    }
    &:hover {
      transition: all 0.2s ease-out;
      svg {
        ${props => props.isFullScreen && 'color: #337ab7'};
        transform: scale(1.1);
      }
    }
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
  .upload-date-fullscreen-wrap {
    background: #222b45;
    position: absolute;
    z-index: 4;
    bottom: 8vh;
    left: 6vw;
    max-width: 416px;
    height: 64px;
    padding: 12px 40px;
    opacity: 0.35;
    border-radius: 16px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    > div {
      font-size: 32px;
      opacity: 0.8;
      line-height: 1.25;
      color: #fff;
      white-space: nowrap;
    }
  }

  .ant-empty {
    display: flex;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    .ant-empty-image {
      height: auto;
    }
    .ant-empty-description {
      span {
        font-size: 12px;
        line-height: 1.33;
        text-align: center;
        color: #8f9bb3;
      }
    }
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
              thumbnailPosition={isFullScreen ? 'bottom' : 'right'}
              showPlayButton={isFullScreen}
              disableKeyDown={true}
              onScreenChange={() => switchFullScreen(!isFullScreen)}
              renderLeftNav={(onClick, disabled) =>
                !loading ? (
                  <div className="image-gallery-custom-left-nav-wrap">
                    <div>
                      <button
                        className="image-gallery-custom-left-nav"
                        onClick={() => {
                          if (!disabled) {
                            updatePatientImagesIndex(currentIndex - 1);
                          }
                        }}
                      >
                        {isFullScreen ? (
                          <svg className="image-gallery-svg" viewBox="6 0 12 24" fill="none" stroke="currentColor">
                            <polyline points="15 18 9 12 15 6" />
                          </svg>
                        ) : (
                          <LeftArrowSvg />
                        )}
                      </button>
                    </div>
                    <div />
                  </div>
                ) : null
              }
              renderRightNav={(onClick, disabled) => {
                return !loading ? (
                  <div className="image-gallery-custom-right-nav-wrap">
                    <div />
                    <div>
                      <button
                        className={`image-gallery-custom-right-nav`}
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
                        {isFullScreen ? (
                          <svg className="image-gallery-svg" viewBox="6 0 12 24" fill="none" stroke="currentColor">
                            <polyline points="9 18 15 12 9 6" />
                          </svg>
                        ) : (
                          <RightArrowSvg />
                        )}
                      </button>
                    </div>
                  </div>
                ) : null;
              }}
              renderFullscreenButton={onClick =>
                !loading ? (
                  <button className="image-gallery-custom-fullscreen-button" onClick={() => onClick()}>
                    {isFullScreen ? (
                      <svg className="image-gallery-svg" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M8 3v3a2 2 0 0 1-2 2H3m18 0h-3a2 2 0 0 1-2-2V3m0 18v-3a2 2 0 0 1 2-2h3M3 16h3a2 2 0 0 1 2 2v3" />
                      </svg>
                    ) : (
                      <FullscreenExpandSvg />
                    )}
                  </button>
                ) : null
              }
              renderCustomControls={() =>
                isFullScreen && (
                  <div className="upload-date-fullscreen-wrap">
                    <div>上傳於 {patientImagesData?.currentImageDate}</div>
                  </div>
                )
              }
            />
            <div className="upload-date-wrap">上傳於 {patientImagesData?.currentImageDate}</div>
          </Fragment>
        ) : (
          <Empty image={<EmptyDataSvg />} description={<span>無資料，請至月申報新增</span>} />
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
