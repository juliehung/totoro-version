import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './calendar.reducer';
import { ICalendar } from 'app/shared/model/calendar.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICalendarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CalendarDetail extends React.Component<ICalendarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { calendarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.calendar.detail.title">Calendar</Translate> [<b>{calendarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="totoroApp.calendar.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={calendarEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="intervalType">
                <Translate contentKey="totoroApp.calendar.intervalType">Interval Type</Translate>
              </span>
            </dt>
            <dd>{calendarEntity.intervalType}</dd>
            <dt>
              <span id="dateType">
                <Translate contentKey="totoroApp.calendar.dateType">Date Type</Translate>
              </span>
            </dt>
            <dd>{calendarEntity.dateType}</dd>
            <dt>
              <span id="start">
                <Translate contentKey="totoroApp.calendar.start">Start</Translate>
              </span>
            </dt>
            <dd>{calendarEntity.start}</dd>
            <dt>
              <span id="end">
                <Translate contentKey="totoroApp.calendar.end">End</Translate>
              </span>
            </dt>
            <dd>{calendarEntity.end}</dd>
          </dl>
          <Button tag={Link} to="/entity/calendar" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/calendar/${calendarEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ calendar }: IRootState) => ({
  calendarEntity: calendar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CalendarDetail);
