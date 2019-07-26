import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, CustomInput, Form, FormGroup, Input, Label, Row, Progress, Table } from 'reactstrap';
import DayPickerInput from 'react-day-picker/DayPickerInput';
import 'react-day-picker/lib/style.css';
import '../registration/registration.css';

// tslint:disable-next-line:no-unused-variable
import {
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from 'app/entities/appointment/appointment.reducer';
import { getRegistrationEntities } from 'app/entities/appointment/registration.action';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IAppointment } from 'app/shared/model/appointment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { Gender } from 'app/shared/model/patient.model';
import { isDate } from 'moment';

export interface IRegistrationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IRegistrationState extends IPaginationBaseState {
  selectedDoctor?: string;
  date: Date;
}

export const enum registrationActivePage {
  Home = 1
}

export class Registration extends React.Component<IRegistrationProps, IRegistrationState> {
  state: IRegistrationState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE),
    selectedDoctor: 'all',
    date: new Date(),
    sort: 'registration.arrivalTime'
  };

  componentDidMount() {
    this.getRegistrationEntities();
    this.props.getUsers();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  pushHistory = () => {
    this.props.history.push(
      `${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}&doctor=${
        this.state.selectedDoctor
      }`
    );
  };

  sortEntities(page?: number) {
    this.getRegistrationEntities(page);
    if (page) {
      this.setState({ activePage: page }, this.pushHistory);
    } else {
      this.pushHistory();
    }
  }

  handlePagination = activePage => {
    this.setState({ activePage }, () => this.sortEntities());
  };

  getRegistrationEntities = (page?: number) => {
    const targetPage = page ? page : this.state.activePage;
    const { itemsPerPage, sort, order, date, selectedDoctor } = this.state;
    this.props.getRegistrationEntities(targetPage - 1, itemsPerPage, `${sort},${order}`, date, selectedDoctor);
  };

  renderProfile = gender => {
    const wh = 88 / 2.5;
    const profileWH = 55 / 2.5;
    if (gender === Gender.MALE) {
      return <img src="content/images/man@2x.png" width={wh} height={wh} />;
    } else if (gender === Gender.FEMALE) {
      return <img src="content/images/woman@2x.png" width={wh} height={wh} />;
    } else {
      return (
        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: profileWH + 'px',
            width: wh + 'px',
            height: wh + 'px',
            backgroundColor: '#1768ac',
            marginRight: 'auto',
            marginLeft: 'auto'
          }}
        >
          <img src="content/images/logo.png" width={profileWH} height={profileWH} />
        </div>
      );
    }
  };

  handleSelectDoctor = (e: React.FormEvent<HTMLSelectElement>) => {
    this.setState({ selectedDoctor: e.currentTarget.value }, () => {
      this.sortEntities(registrationActivePage.Home);
    });
  };

  handleDate = (e: React.MouseEvent) => {
    const element = e.currentTarget;
    if (element.className.indexOf('addDay') !== -1) {
      this.setState(
        (prevState, props) => ({
          date: new Date(prevState.date.setDate(prevState.date.getDate() + 1))
        }),
        () => {
          this.sortEntities(registrationActivePage.Home);
        }
      );
    } else if (element.className.indexOf('minusDay') !== -1) {
      this.setState(
        (prevState, props) => ({
          date: new Date(prevState.date.setDate(prevState.date.getDate() - 1))
        }),
        () => {
          this.sortEntities(registrationActivePage.Home);
        }
      );
    }
  };

  handleInputDayChange = (day: Date) => {
    if (day.toLocaleDateString().match(/^\d{4}\/\d{1,2}\/\d{1,2}$/)) {
      this.setState({ date: day }, () => {
        this.sortEntities(registrationActivePage.Home);
      });
    }
  };

  render() {
    const { appointmentList, match, totalItems } = this.props;
    const { activePage, itemsPerPage } = this.state;
    const status = { PENDING: '等待中', FINISHED: '完成', IN_PROGRESS: '看診中' };
    return (
      <div>
        <div className="selectDateDoctorContainer">
          <p className="registrationCurrentDate">{this.state.date.toLocaleDateString()}</p>
          <div className="registrationDateInput">
            <span>日期：</span>
            <div className="triangleLeft minusDay" onClick={this.handleDate} />
            <DayPickerInput
              format="M/D/YYYY"
              value={this.state.date}
              onDayChange={this.handleInputDayChange}
              dayPickerProps={{
                todayButton: 'Today'
              }}
            />
            <div className="triangleRight addDay" onClick={this.handleDate} />
          </div>
          <Input
            type="select"
            name="doctorList"
            id="doctorList"
            value={this.state.selectedDoctor}
            onChange={this.handleSelectDoctor}
            style={{ width: '200px' }}
          >
            <option value="all">全診所醫師</option>
            {this.props.doctorList.length > 0
              ? this.props.doctorList.filter(user => user.login.match(/(cdd\d*)|(^d\d*)/)).map((doctor, i) => (
                  <option key={i} value={doctor.id}>
                    {doctor.firstName}
                  </option>
                ))
              : null}
          </Input>
        </div>
        <div className="table-responsive registrationTable">
          <Table responsive>
            <thead>
              <tr>
                <th>序位</th>
                <th>性別</th>
                <th>病患姓名</th>
                <th>病歷編號</th>
                <th>掛號時間</th>
                <th>主治醫師</th>
                <th>看診狀態</th>
              </tr>
            </thead>
            <tbody>
              {appointmentList.length > 0 ? (
                appointmentList.map((registration, i) => (
                  <tr key={`entity-${i}`} className="registrationTr">
                    <td>{i + 1 + itemsPerPage * (activePage - 1)}</td>
                    <td>{this.renderProfile(registration.patient.gender)}</td>
                    <td>{registration.patient.name}</td>
                    <td>{registration.patient.id}</td>
                    <td>
                      <TextFormat type="date" value={registration.registration.arrivalTime} format={'HH:mm'} />
                    </td>
                    <td id={registration.doctor.id}>{registration.doctor.user.firstName}</td>
                    <td className={`registration${registration.registration.status}`}>{status[registration.registration.status]}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={7}>今日沒有病患</td>
                </tr>
              )}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ appointment, userManagement }: IRootState) => ({
  appointmentList: appointment.entities,
  totalItems: appointment.totalItems,
  doctorList: userManagement.users
});

const mapDispatchToProps = {
  getRegistrationEntities,
  getUsers,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Registration);
