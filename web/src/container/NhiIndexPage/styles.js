import styled, { createGlobalStyle } from 'styled-components';
import { Menu, Modal, Table, Tabs } from 'antd';

//#region
const GlobalStyle = createGlobalStyle`
  .CalendarDay__selected,
  .CalendarDay__selected:active,
  .CalendarDay__selected:hover {
    background: #3266ff !important;
    border: 1px transparent solid !important;
    color: #fff !important;
    font-weight:bold !important;
  }

  .CalendarDay__selected_span {
    background: rgba(50, 102, 255, 0.1) !important;
    border: 1px double rgba(50, 102, 255, 0.1) !important;
    color: #444 !important;
    font-weight:bold !important;
  }

  .CalendarDay__hovered_span,
  .CalendarDay__hovered_span:hover {
    background: rgba(50, 102, 255, 0.1) !important;
    border: 1px double rgba(50, 102, 255, 0.1) !important;
    color: #444 !important;
    font-weight:bold !important;
  }
`;
const ModalContainer = styled(Modal)`
  .ant-modal-body {
    padding: 0;
  }
  .cancel-modal-btn {
    width: 64px;
    height: 40px;
    margin: 0 18px 0 0;
    border: solid 1px rgba(255, 255, 255, 0.2);
    box-shadow: none;
    color: #8f9bb3;

    &:hover {
      color: #222b45;
    }
  }
  .submit-modal-btn {
    width: 102px;
    height: 40px;
    margin: 0 0 0 18px;
    border-radius: 20px;
    background-color: #3266ff;

    &:hover {
      background-color: #fff;
      color: #3266ff;
    }
  }
  .ant-btn-primary[disabled] {
    color: rgba(0, 0, 0, 0.25);
    border-color: #d9d9d9;
    background-color: #f5f5f5;
  }
`;
const ModalContentContainer = styled.div`
  background-image: linear-gradient(159deg, #f3f6fa 19%, #e4e9f2 77%);
  width: 100%;
  height: 600px;

  display: flex;
  align-items: center;

  > div:nth-child(1) {
    box-shadow: 8px 0 14px 0 rgba(200, 138, 138, 0.06);
    background-color: rgba(255, 255, 255, 0.95);
    width: 300px;
    margin-left: 28px;
    height: 100%;
    overflow: hidden;
    display: flex;
    flex-direction: column;

    .select-month-input-wrap {
      width: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      text-align: center;
      padding: 12px 24px;
      > div:nth-child(1) {
        font-size: 15px;
        font-weight: 600;
        line-height: 1.6;
        color: #222b45;
        text-align: left;
      }
      > div:nth-child(2) {
        flex: 1;
        display: flex;
        justify-content: flex-end;
        align-items: center;
        cursor: pointer;
        > div {
          width: 87%;
        }
      }
    }

    .search-input-container {
      padding: 6px 24px;

      .search-input-wrap {
        border-radius: 8px;
        border: solid 1px #e4e9f2;
        background: #f7f9fc;
        height: 40px;

        input::-webkit-input-placeholder {
          font-size: 15px;
          line-height: 1.33;
          color: #8f9bb3;
        }
        * {
          background: #f7f9fc;
          font-size: 15px;
          line-height: 1.33;
          color: #222b45;
        }
        .ant-input-suffix > span {
          display: flex;

          &::before {
            border-left: 0;
          }
        }
      }
    }

    .render-modal-nhi-data-container {
      flex: 1;
      overflow: hidden;

      .nhi-data-header-wrap {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 6px 24px;

        > div:nth-child(1) {
          width: 43px;
          .dropdown-click-btn-wrap {
            display: flex;
            justify-content: space-between;
            align-items: center;
            cursor: pointer;

            > div:nth-child(1) {
              width: 20px;
              height: 20px;
              border-radius: 3px;
              background-color: #3266ff;
              text-align: center;
              color: #ffffff;
              position: relative;

              &::before {
                position: absolute;
                background: #fff;
                content: '';
                height: 3px;
                width: 12px;
                left: 50%;
                top: 50%;
              }
              ${props =>
                !props.isDisposalCheckBoxVisible &&
                `&::after {
                  position: absolute;
                  width: 100%;
                  background: #fff;
                  content: '';
                  height: 3px;
                  width: 12px;
                  left: 50%;
                  top: 50%;
                  transition: all 0.3s ease-in-out;
                }`}
              &::before {
                transform: translate(-50%, -50%);
              }
              ${props =>
                !props.isDisposalCheckBoxVisible &&
                `&::after{
                    transform: translate(-50%, -50%) rotate(90deg);
                  }`}
            }
            > div:nth-child(2) {
              flex: 1;
              text-align: center;
              display: flex;
              justify-content: center;
              align-items: center;
              transform: ${props => (props.isDisposalCheckBoxVisible ? 'rotate(180deg)' : 'rotate(0deg)')};
              transition: all 0.3s ease-out;
            }
          }
        }
        > div:nth-child(2) {
          text-align: right;
          flex: 1;
        }
      }

      .render-nhi-data-list-container {
        overflow-y: auto;
        max-height: calc(100% - 40px);
        max-width: 95%;
        margin: 0 auto;
        .lazy-load-wrap {
          padding-left: 24px;
        }
        .render-each-nhi-data-wrap {
          .nhi-data-list-date-header {
            height: 29px;
            background: #9ba1c3;
            font-size: 15px;
            font-weight: 600;
            line-height: 1.6;
            color: #fff;
            padding: 3px 0 2px 14px;
          }
          > div:nth-child(2) {
            padding: 6px 0;
            .render-each-nhi-wrap {
              display: flex;
              justify-content: space-between;
              align-items: flex-start;
              padding: 10px 0;
              position: relative;
              cursor: pointer;

              > div:nth-child(1) {
                width: 20px;
                height: 20px;
                text-align: center;
                position: relative;
                padding-left: 24px;
              }

              > div:nth-child(2) {
                margin-left: 5px;
                padding-left: 24px;
                flex: 1;

                > div:nth-child(1) {
                  font-size: 15px;
                  font-weight: 600;
                  line-height: 1.6;
                  color: #222b45;
                }

                > div:nth-child(2) {
                  font-size: 12px;
                  line-height: 1.33;
                  color: #8f9bb3;
                }
              }

              > div:nth-child(3) {
                font-size: 10px;
                font-weight: bold;
                line-height: 1.2;
                text-align: right;
                color: #8f9bb3;
                padding-right: 24px;
              }

              &:not(:last-child)::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 1px;
                background: #edf1f7;
              }
            }
          }
        }
      }
    }
  }
  > div:nth-child(2) {
    flex: 1;
    padding: 80px 56px;
    height: 100%;

    .render-nhi-one-detail-wrap {
      .nhi-one-label {
        font-size: 13px;
        font-weight: 600;
        line-height: 1.85;
        color: #8f9bb3;
      }
      .nhi-one-info-label {
        font-size: 26px;
        font-weight: bold;
        line-height: 1.23;
        color: #222b45;
      }
      .nhi-one-name {
        font-size: 32px;
        font-weight: 600;
        color: #4a90e2;
      }
      .nhi-one-info-wrap {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        margin-top: 24px;

        > div:not(:first-child) {
          margin-left: 48px;
        }
      }
      .nhi-one-table-wrap {
        margin-top: 24px;
      }
    }
    .nhi-one-loading {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
`;
const MenuContainer = styled(Menu)`
  min-width: 136px;
  > li {
    padding: 12px 16px;
    font-size: 15px;
    line-height: 1.33;
    color: #222b45;
  }
`;
const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 26px 0;

  > div {
    width: 33.33%;
    flex: 1;

    &:nth-child(2) {
      text-align: center;
    }
    &:nth-last-child(1) {
      text-align: right;
      display: flex;
      justify-content: flex-end;
      align-items: center;

      > div {
        padding: 10px 15px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;

        > span {
          padding-left: 10px;
          font-size: 14px;
          font-weight: bold;
          line-height: 1.14;
        }
        &:nth-child(1) {
          > span {
            color: #8f9bb3;
          }
        }
        &:nth-child(2) {
          > span {
            color: #ffffff;
          }
          min-width: 142px;
          height: 40px;
          margin: 0 0 0 22px;
          border-radius: 34px;
          background: #3266ff;
          text-align: center;
        }
      }
    }
    &:not(:last-child) {
      font-size: 30px;
      font-weight: 600;
      line-height: 1.33;
      color: #222b45;
    }
  }
`;
const ChartContainer = styled.div`
  background: #fff;
  width: 100%;
  min-height: 330px;
  margin-bottom: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.1);
  .chart-header-wrap {
    height: 56px;
    font-size: 18px;
    font-weight: bold;
    line-height: 1.33;
    color: #222b45;
    border-bottom: solid 1px #edf1f7;
    padding: 20px;
  }
  .chart-content-wrap.spin-wrap {
    min-height: 300px;
    align-items: center;
  }
  .chart-content-wrap {
    padding: 20px;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    width: 100%;

    > div:nth-child(1) {
      .total-info-wrap {
        padding: 20px;
        > div:nth-child(1) {
          p:nth-child(1) {
            font-size: 36px;
            font-weight: bold;
            line-height: 1.33;
            color: #ffab00;
            margin: 0;
          }
        }
        > div:nth-child(2) {
          margin-top: 24px;
          display: flex;
          justify-content: space-between;
          align-items: center;

          > div > p:nth-child(1) {
            font-size: 22px;
            font-weight: bold;
            line-height: 1.45;
            color: #222b45;
            margin: 0;
          }
          > div:nth-child(2) {
            margin-left: 40px;
          }
        }

        .total-sub-text {
          font-size: 13px;
          font-weight: 600;
          line-height: 1.85;
          color: #8f9bb3;
        }
      }
    }
    > div:nth-child(2) {
      flex: 1;
      height: 100%;
      width: 80%;
      border-left: solid 1px #e5eced;
    }
  }
`;
const TooltipContainer = styled.div`
  border-radius: 16px;
  box-shadow: 0 5px 20px 0 rgba(0, 0, 0, 0.1);
  background: #ffffff;
  padding: 10px 20px 14px;
  > div {
    color: #222b45;
  }
  > div:nth-child(1) {
    font-size: 13px;
    font-weight: 600;
    line-height: 1.85;
  }
  > div:nth-child(2) {
    font-size: 12px;
    line-height: 1.33;
  }
`;
const TabsContainer = styled.div`
  margin: 0 auto;
  border-radius: 8px;
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.05);
  background: #fff;
  overflow: hidden;
  padding-bottom: 20px;
  max-height: 600px;

  .ant-tabs-tab {
    &:hover,
    &:focus,
    &:active,
    .ant-tabs-tab-btn:focus,
    .ant-tabs-tab-remove:focus,
    .ant-tabs-tab-btn:active {
      color: #1890ff !important;
    }
  }

  .ant-tabs-tab-active {
    color: #1890ff !important;

    .ant-tabs-tab-btn {
      color: #1890ff !important;
    }
  }
`;
const TabsWrap = styled(Tabs)`
  .ant-tabs-ink-bar {
    background: transparent;
  }

  .ant-tabs-ink-bar::after {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    left: 50%;
    transform: translateX(-50%);
    background: #1890ff;
  }
`;
const TableContainer = styled(Table)`
  padding: 0 24px 26px 24px;

  .ant-table-header {
    .ant-table-cell {
      background: #f7f9fc !important;
      font-size: 13px !important;
      font-weight: 600;
      line-height: 1.85;
      color: #222b45 !important;
    }
  }
  .ant-table-expanded-row {
    > .ant-table-cell {
      background: #e4eaff !important;
    }
  }
  .ant-table-cell {
    font-size: 13px !important;
    line-height: 1.38;
    color: #222b45 !important;
  }
`;
const TabBarExtraContentContainer = styled.div`
  font-size: 18px;
  font-weight: bold;
  line-height: 1.33;
  color: #222b45;
  padding: 24px;
  margin-right: 20px;
`;
const ExpandTableContainer = styled(Table)`
  padding: 0 24px 26px 24px;
  .ant-table-header {
    .ant-table-cell {
      background: #f7f9fc !important;
      font-size: 13px !important;
      font-weight: 600;
      line-height: 1.85;
      color: #222b45 !important;
    }
  }
  .ant-table-cell {
    background: #e4eaff !important;
  }
`;
//#endregion

export {
  GlobalStyle,
  ModalContainer,
  ModalContentContainer,
  MenuContainer,
  TitleContainer,
  ChartContainer,
  TooltipContainer,
  TabsContainer,
  TabsWrap,
  TableContainer,
  TabBarExtraContentContainer,
  ExpandTableContainer,
};
