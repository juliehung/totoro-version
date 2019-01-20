import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './calendar-setting.reducer';
import { ICalendarSetting } from 'app/shared/model/calendar-setting.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICalendarSettingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class CalendarSetting extends React.Component<ICalendarSettingProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { calendarSettingList, match } = this.props;
    return (
      <div>
        <h2 id="calendar-setting-heading">
          <Translate contentKey="totoroApp.calendarSetting.home.title">Calendar Settings</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.calendarSetting.home.createLabel">Create new Calendar Setting</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendarSetting.startTime">Start Time</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendarSetting.endTime">End Time</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendarSetting.timeInterval">Time Interval</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendarSetting.weekday">Weekday</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendarSetting.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendarSetting.endDate">End Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {calendarSettingList.map((calendarSetting, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${calendarSetting.id}`} color="link" size="sm">
                      {calendarSetting.id}
                    </Button>
                  </td>
                  <td>{calendarSetting.startTime}</td>
                  <td>{calendarSetting.endTime}</td>
                  <td>
                    <Translate contentKey={`totoroApp.TimeInterval.${calendarSetting.timeInterval}`} />
                  </td>
                  <td>
                    <Translate contentKey={`totoroApp.WeekDay.${calendarSetting.weekday}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={calendarSetting.startDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={calendarSetting.endDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${calendarSetting.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${calendarSetting.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${calendarSetting.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ calendarSetting }: IRootState) => ({
  calendarSettingList: calendarSetting.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CalendarSetting);
