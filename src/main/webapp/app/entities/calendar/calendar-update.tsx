import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './calendar.reducer';
import { ICalendar } from 'app/shared/model/calendar.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICalendarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICalendarUpdateState {
  isNew: boolean;
}

export class CalendarUpdate extends React.Component<ICalendarUpdateProps, ICalendarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { calendarEntity } = this.props;
      const entity = {
        ...calendarEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/calendar');
  };

  render() {
    const { calendarEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.calendar.home.createOrEditLabel">
              <Translate contentKey="totoroApp.calendar.home.createOrEditLabel">Create or edit a Calendar</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : calendarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="calendar-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="totoroApp.calendar.date">Date</Translate>
                  </Label>
                  <AvField
                    id="calendar-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="timeIntervalLabel">
                    <Translate contentKey="totoroApp.calendar.timeInterval">Time Interval</Translate>
                  </Label>
                  <AvInput
                    id="calendar-timeInterval"
                    type="select"
                    className="form-control"
                    name="timeInterval"
                    value={(!isNew && calendarEntity.timeInterval) || 'MORNING'}
                  >
                    <option value="MORNING">
                      <Translate contentKey="totoroApp.TimeInterval.MORNING" />
                    </option>
                    <option value="NOON">
                      <Translate contentKey="totoroApp.TimeInterval.NOON" />
                    </option>
                    <option value="EVENING">
                      <Translate contentKey="totoroApp.TimeInterval.EVENING" />
                    </option>
                    <option value="NIGHT">
                      <Translate contentKey="totoroApp.TimeInterval.NIGHT" />
                    </option>
                    <option value="ALL">
                      <Translate contentKey="totoroApp.TimeInterval.ALL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="timeTypeLabel">
                    <Translate contentKey="totoroApp.calendar.timeType">Time Type</Translate>
                  </Label>
                  <AvInput
                    id="calendar-timeType"
                    type="select"
                    className="form-control"
                    name="timeType"
                    value={(!isNew && calendarEntity.timeType) || 'WORK_TIME'}
                  >
                    <option value="WORK_TIME">
                      <Translate contentKey="totoroApp.TimeType.WORK_TIME" />
                    </option>
                    <option value="HOLIDAY">
                      <Translate contentKey="totoroApp.TimeType.HOLIDAY" />
                    </option>
                    <option value="NHI_POINT_EXCLUDE">
                      <Translate contentKey="totoroApp.TimeType.NHI_POINT_EXCLUDE" />
                    </option>
                    <option value="OTHER">
                      <Translate contentKey="totoroApp.TimeType.OTHER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="startTimeLabel" for="startTime">
                    <Translate contentKey="totoroApp.calendar.startTime">Start Time</Translate>
                  </Label>
                  <AvField
                    id="calendar-startTime"
                    type="text"
                    name="startTime"
                    validate={{
                      pattern: {
                        value: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$' })
                      }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endTimeLabel" for="endTime">
                    <Translate contentKey="totoroApp.calendar.endTime">End Time</Translate>
                  </Label>
                  <AvField
                    id="calendar-endTime"
                    type="text"
                    name="endTime"
                    validate={{
                      pattern: {
                        value: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$' })
                      }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/calendar" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  calendarEntity: storeState.calendar.entity,
  loading: storeState.calendar.loading,
  updating: storeState.calendar.updating,
  updateSuccess: storeState.calendar.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CalendarUpdate);
