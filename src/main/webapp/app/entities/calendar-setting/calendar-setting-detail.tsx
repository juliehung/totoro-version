import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './calendar-setting.reducer';
import { ICalendarSetting } from 'app/shared/model/calendar-setting.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICalendarSettingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CalendarSettingDetail extends React.Component<ICalendarSettingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { calendarSettingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.calendarSetting.detail.title">CalendarSetting</Translate> [<b>{calendarSettingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="startTime">
                <Translate contentKey="totoroApp.calendarSetting.startTime">Start Time</Translate>
              </span>
            </dt>
            <dd>{calendarSettingEntity.startTime}</dd>
            <dt>
              <span id="endTime">
                <Translate contentKey="totoroApp.calendarSetting.endTime">End Time</Translate>
              </span>
            </dt>
            <dd>{calendarSettingEntity.endTime}</dd>
            <dt>
              <span id="timeInterval">
                <Translate contentKey="totoroApp.calendarSetting.timeInterval">Time Interval</Translate>
              </span>
            </dt>
            <dd>{calendarSettingEntity.timeInterval}</dd>
            <dt>
              <span id="weekday">
                <Translate contentKey="totoroApp.calendarSetting.weekday">Weekday</Translate>
              </span>
            </dt>
            <dd>{calendarSettingEntity.weekday}</dd>
            <dt>
              <span id="startDate">
                <Translate contentKey="totoroApp.calendarSetting.startDate">Start Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={calendarSettingEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="totoroApp.calendarSetting.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={calendarSettingEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/calendar-setting" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/calendar-setting/${calendarSettingEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ calendarSetting }: IRootState) => ({
  calendarSettingEntity: calendarSetting.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CalendarSettingDetail);
