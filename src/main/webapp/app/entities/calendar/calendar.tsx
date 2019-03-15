import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './calendar.reducer';
import { ICalendar } from 'app/shared/model/calendar.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICalendarProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Calendar extends React.Component<ICalendarProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { calendarList, match } = this.props;
    return (
      <div>
        <h2 id="calendar-heading">
          <Translate contentKey="totoroApp.calendar.home.title">Calendars</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.calendar.home.createLabel">Create new Calendar</Translate>
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
                  <Translate contentKey="totoroApp.calendar.start">Start</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendar.end">End</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendar.timeType">Time Type</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendar.timeInterval">Time Interval</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.calendar.note">Note</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {calendarList.map((calendar, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${calendar.id}`} color="link" size="sm">
                      {calendar.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={calendar.start} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={calendar.end} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <Translate contentKey={`totoroApp.TimeType.${calendar.timeType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`totoroApp.TimeInterval.${calendar.timeInterval}`} />
                  </td>
                  <td>{calendar.note}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${calendar.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${calendar.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${calendar.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ calendar }: IRootState) => ({
  calendarList: calendar.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Calendar);
