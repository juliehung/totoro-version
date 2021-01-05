import { Modal, Button, Radio, Select } from 'antd';
import React, { Fragment } from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import ReactToPrint from 'react-to-print';
import PrintAppList from './PrintAppList';
import styled, { createGlobalStyle } from 'styled-components';
import { changePrintModalVisible, changePrintDate, changePrintDoctor } from './actions';
import DatePicker from '../../component/DatePicker';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { CalendarOutlined } from '@ant-design/icons';

//#region
export const GlobalStyle = createGlobalStyle`
  .print-doctor-selector-dropdown {
    border-radius: 8px;
  }
  .ant-select-item-option-selected:not(.ant-select-item-option-disabled) {
    background-color:#e4eaff;
    font-weight: normal;
  }
`;

const StyleModal = styled(Modal)`
  .ant-modal-body {
    padding: 40px;
  }
  .ant-modal-content {
    border-radius: 10px;
  }
`;

const Header = styled.p`
  font-size: 24px;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  & > div {
    display: flex;
    @media (max-width: 580px) {
      flex-wrap: wrap;
    }
    &:nth-child(1) {
      align-items: center;
    }
    &:nth-child(2) {
      align-items: start;
      margin-top: 31px;
    }
  }

  .print-label {
    font-size: 15px;
    font-weight: 600;
    color: #222b45;
    margin-right: 25px;
  }
  .print-modal {
    flex-grow: 4;
    .SingleDatePicker {
      width: 100%;
    }
    .SingleDatePickerInput__withBorder {
      border: 1px solid #c5cee0;
      border-radius: 8px;
      width: 100%;
      display: flex;

      .DateInput.DateInput_1 {
        width: auto;
        flex-grow: 8;
        margin-left: 2px;
        border-radius: 8px;
      }
      .DateInput_input {
        font-size: 15px;
        height: 100%;
        border-radius: 8px;
      }
      .SingleDatePickerInput_calendarIcon {
        flex-grow: 1;
        & > span > svg {
          fill: #c5cee0;
        }
      }
    }
  }
  .ant-radio-group {
    flex-grow: 4;
    .ant-select-multiple {
      margin-top: 4px;
      width: calc(100% - 24px);
      margin-left: 24px;
      .ant-select-selector {
        border: 1px solid #c5cee0;
        border-radius: 8px;
        .ant-select-selection-placeholder {
          color: #8f9bb3;
          opacity: 0.48;
          font-size: 15px;
        }
      }
      &.ant-select-disabled .ant-select-selector {
        background: rgba(143, 155, 179, 0.15);
      }
      .ant-select-selection-item-remove {
        color: #8f9bb3;
      }
      .ant-select-selection-item {
        border-radius: 16px;
        border: 1px solid #c5cee0;
        background: rgba(143, 155, 179, 0.15);
      }
    }
  }
  .ant-radio-wrapper {
    display: block;
    font-size: 15px;
    &:nth-child(1) {
      margin-bottom: 10px;
    }
  }
  .ant-select-focused:not(.ant-select-disabled).ant-select-multiple .ant-select-selector {
    border-color: #5c8aff;
  }
`;

const ButtonWrap = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-top: 25px;
`;

const StyledButton = styled(Button)`
  width: 87px;
  height: 40px;
  text-align: center;
  border-radius: 20px;
  font-weight: 600;
  &.print {
    color: #ffffff;
    &.ant-btn-primary[disabled] {
      background: rgba(143, 155, 179, 0.15);
      border: 1px solid #c5cee0;
      color: #8f9bb3;
      span {
        opacity: 0.48;
      }
    }
  }
  &.cancel {
    color: #8f9bb3;
    border: none;
  }
`;
//#endregion

class PrintModal extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: 'all',
      printButtonDisable: false,
    };
  }
  async componentDidUpdate(prevProps, prevState) {
    if (this.props.visible && !prevProps.visible) {
      this.props.changePrintDate(moment());
    }
    if (this.state.selected !== prevState.selected) {
      this.props.changePrintDoctor([]);
    }
  }

  onDateChange = d => this.props.changePrintDate(d);

  onChangeDoctors = doctors => this.props.changePrintDoctor(doctors);

  render() {
    const { Option } = Select;
    return (
      <Fragment>
        <GlobalStyle />
        <StyleModal
          visible={this.props.visible}
          footer={null}
          onCancel={this.props.changePrintModalVisible}
          centered
          cancelable
          closable={false}
          width={524}
        >
          <div>
            <Header>列印預約表</Header>
            <Container>
              <div>
                <span className="print-label">列印日期：</span>
                <DatePicker
                  className="print-modal"
                  date={this.props.date}
                  onDateChange={this.onDateChange}
                  customInputIcon={<CalendarOutlined />}
                  inputIconPosition={'after'}
                  readOnly
                />
              </div>
              <div>
                <span className="print-label">選擇醫師：</span>
                <Radio.Group onChange={e => this.setState({ selected: e.target.value })} value={this.state.selected}>
                  <Radio value={'all'}>全院所</Radio>
                  <Radio value={'target'}>指定醫師</Radio>
                  <Select
                    size={'large'}
                    mode={'multiple'}
                    allowClear
                    placeholder="請選擇"
                    onChange={this.onChangeDoctors}
                    disabled={this.state.selected !== 'target'}
                    value={this.props.selectedDoctors}
                    dropdownClassName={'print-doctor-selector-dropdown'}
                  >
                    {this.props.doctors.map(d => (
                      <Option key={d.login} value={d.name}>
                        {d.name}
                      </Option>
                    ))}
                  </Select>
                </Radio.Group>
              </div>
            </Container>
            <ButtonWrap>
              <StyledButton
                className="cancel"
                onClick={() => {
                  this.props.changePrintModalVisible();
                  this.props.changePrintDoctor([]);
                  this.setState({ selected: 'all' });
                }}
              >
                取消
              </StyledButton>
              <ReactToPrint
                trigger={() => (
                  <StyledButton
                    className="print"
                    type="primary"
                    disabled={this.state.selected === 'target' && this.props.selectedDoctors.length === 0}
                  >
                    列印
                  </StyledButton>
                )}
                content={() => this.PrintAppListRef}
                pageStyle="@page { size: 12.5in 18in}"
              />
            </ButtonWrap>
          </div>
          <div style={{ display: 'none' }}>
            <PrintAppList
              ref={el => (this.PrintAppListRef = el)}
              appointmentList={this.props.appointmentList}
              doctorList={this.props.doctorList}
              date={this.props.date}
              clinicName={this.props.clinicName}
              selectedDoctors={this.props.selectedDoctors}
            />
          </div>
        </StyleModal>
      </Fragment>
    );
  }
}

const mapStateToProps = ({ homePageReducer, appointmentPageReducer }) => ({
  visible: appointmentPageReducer.print.visible,
  date: appointmentPageReducer.print.date,
  selectedDoctors: appointmentPageReducer.print.doctor,
  appointmentList: appointmentPageReducer.print.appData.appointmentList,
  doctorList: appointmentPageReducer.print.appData.doctorList,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
});

const mapDispatchToProps = {
  changePrintModalVisible,
  changePrintDate,
  changePrintDoctor,
};

export default connect(mapStateToProps, mapDispatchToProps)(PrintModal);
