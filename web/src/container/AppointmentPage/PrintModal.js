import { Modal, Button, Radio, Select } from 'antd';
import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import ReactToPrint from 'react-to-print';
import PrintAppList from './PrintAppList';
import styled from 'styled-components';
import { changePrintModalVisible, changePrintDate, changePrintDoctor } from './actions';
import DatePicker from '../../component/DatePicker';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';

//#region
const CancelButton = styled(Button)`
  margin-right: 16px;
`;

const Header = styled.p`
  font-size: 24px;
  font-weight: 600;
  color: #444c63;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  & > span {
    font-size: 16px;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.65);
    margin-right: 25px;
  }
  .ant-radio-wrapper {
    display: block;
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
      <Modal
        // visible={true}
        visible={this.props.visible}
        footer={null}
        onCancel={this.props.changePrintModalVisible}
        centered
        cancelable
        closable={false}
        width={465}
      >
        <div>
          <Header>列印預約表</Header>
          <Container>
            <span>列印日期：</span>
            <DatePicker date={this.props.date} onDateChange={this.onDateChange} readOnly />
            <span>選擇醫師：</span>

            <Radio.Group onChange={e => this.setState({ selected: e.target.value })} value={this.state.selected}>
              <Radio value={'all'}>全院所</Radio>
              <Radio value={'target'}>指定醫師</Radio>
              <Select
                mode="multiple"
                allowClear
                placeholder="請選擇"
                onChange={this.onChangeDoctors}
                style={{ width: '100%' }}
                disabled={this.state.selected !== 'target'}
                value={this.props.selectedDoctors}
              >
                {this.props.doctors.map(d => (
                  <Option key={d.login} value={d.name}>
                    {d.name}
                  </Option>
                ))}
              </Select>
            </Radio.Group>
          </Container>
          <div
            style={{
              display: 'flex',
              justifyContent: 'flex-end',
              marginTop: '32px',
              marginRight: '32px',
            }}
          >
            <CancelButton
              size="large"
              onClick={() => {
                this.props.changePrintModalVisible();
                this.props.changePrintDoctor([]);
                this.setState({ selected: 'all' });
              }}
            >
              取消
            </CancelButton>
            <ReactToPrint
              trigger={() => (
                <Button
                  size="large"
                  type="primary"
                  disabled={this.state.selected === 'target' && this.props.selectedDoctors.length === 0}
                >
                  列印
                </Button>
              )}
              content={() => this.PrintAppListRef}
              pageStyle="@page { size: 12.5in 18in}"
            />
          </div>
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
      </Modal>
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
  success: appointmentPageReducer.print.success,
});

const mapDispatchToProps = {
  changePrintModalVisible,
  changePrintDate,
  changePrintDoctor,
};

export default connect(mapStateToProps, mapDispatchToProps)(PrintModal);
