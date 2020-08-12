import { Modal, Button } from 'antd';
import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import ReactToPrint from 'react-to-print';
import PrintAppList from './PrintAppList';
import styled from 'styled-components';
import { changePrintModalVisible, changePrintDate } from './actions';
import DatePicker from '../../component/DatePicker';

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
  align-items: center;
  & > span {
    font-size: 16px;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.65);
    margin-right: 25px;
  }
`;
//#endregion

class PrintModal extends React.Component {
  async componentDidUpdate(prevProps) {
    if (this.props.visible && !prevProps.visible) {
      this.props.changePrintDate(moment());
    }
  }

  onDateChange = d => {
    this.props.changePrintDate(d);
  };

  render() {
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
            <span>預約日期：</span>
            <DatePicker date={this.props.date} onDateChange={this.onDateChange} readOnly />
          </Container>
          <div
            style={{
              display: 'flex',
              justifyContent: 'flex-end',
              marginTop: '32px',
              marginRight: '32px',
            }}
          >
            <CancelButton size="large" onClick={this.props.changePrintModalVisible}>
              取消
            </CancelButton>

            <ReactToPrint
              trigger={() => (
                <Button size="large" type="primary" disabled={this.props.printButtonDisable}>
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
            isRoc={this.props.isRoc}
          />
        </div>
      </Modal>
    );
  }
}

const mapStateToProps = ({ appointmentPageReducer, homePageReducer }) => ({
  visible: appointmentPageReducer.print.visible,
  date: appointmentPageReducer.print.date,
  printButtonDisable: appointmentPageReducer.print.printButtonDisable,
  appointmentList: appointmentPageReducer.print.appData.appointmentList,
  doctorList: appointmentPageReducer.print.appData.doctorList,
  isRoc: homePageReducer.settings.isRoc,
});

const mapDispatchToProps = { changePrintModalVisible, changePrintDate };

export default connect(mapStateToProps, mapDispatchToProps)(PrintModal);
