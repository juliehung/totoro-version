import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './calendar-setting.reducer';
import { ICalendarSetting } from 'app/shared/model/calendar-setting.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICalendarSettingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICalendarSettingUpdateState {
  isNew: boolean;
}

export class CalendarSettingUpdate extends React.Component<ICalendarSettingUpdateProps, ICalendarSettingUpdateState> {
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
      const { calendarSettingEntity } = this.props;
      const entity = {
        ...calendarSettingEntity,
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
    this.props.history.push('/entity/calendar-setting');
  };

  render() {
    const { calendarSettingEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.calendarSetting.home.createOrEditLabel">
              <Translate contentKey="totoroApp.calendarSetting.home.createOrEditLabel">Create or edit a CalendarSetting</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : calendarSettingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="calendar-setting-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startTimeLabel" for="startTime">
                    <Translate contentKey="totoroApp.calendarSetting.startTime">Start Time</Translate>
                  </Label>
                  <AvField
                    id="calendar-setting-startTime"
                    type="text"
                    name="startTime"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      pattern: {
                        value: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$' })
                      }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endTimeLabel" for="endTime">
                    <Translate contentKey="totoroApp.calendarSetting.endTime">End Time</Translate>
                  </Label>
                  <AvField
                    id="calendar-setting-endTime"
                    type="text"
                    name="endTime"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      pattern: {
                        value: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^([0-1][0-9]|2[0-3]):[0-5][0-9]$' })
                      }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="timeIntervalLabel">
                    <Translate contentKey="totoroApp.calendarSetting.timeInterval">Time Interval</Translate>
                  </Label>
                  <AvInput
                    id="calendar-setting-timeInterval"
                    type="select"
                    className="form-control"
                    name="timeInterval"
                    value={(!isNew && calendarSettingEntity.timeInterval) || 'MORNING'}
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
                  <Label id="weekdayLabel">
                    <Translate contentKey="totoroApp.calendarSetting.weekday">Weekday</Translate>
                  </Label>
                  <AvInput
                    id="calendar-setting-weekday"
                    type="select"
                    className="form-control"
                    name="weekday"
                    value={(!isNew && calendarSettingEntity.weekday) || 'SUN'}
                  >
                    <option value="SUN">
                      <Translate contentKey="totoroApp.WeekDay.SUN" />
                    </option>
                    <option value="MON">
                      <Translate contentKey="totoroApp.WeekDay.MON" />
                    </option>
                    <option value="TUE">
                      <Translate contentKey="totoroApp.WeekDay.TUE" />
                    </option>
                    <option value="WED">
                      <Translate contentKey="totoroApp.WeekDay.WED" />
                    </option>
                    <option value="THU">
                      <Translate contentKey="totoroApp.WeekDay.THU" />
                    </option>
                    <option value="FRI">
                      <Translate contentKey="totoroApp.WeekDay.FRI" />
                    </option>
                    <option value="SAT">
                      <Translate contentKey="totoroApp.WeekDay.SAT" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    <Translate contentKey="totoroApp.calendarSetting.startDate">Start Date</Translate>
                  </Label>
                  <AvField
                    id="calendar-setting-startDate"
                    type="date"
                    className="form-control"
                    name="startDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="endDate">
                    <Translate contentKey="totoroApp.calendarSetting.endDate">End Date</Translate>
                  </Label>
                  <AvField id="calendar-setting-endDate" type="date" className="form-control" name="endDate" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/calendar-setting" replace color="info">
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
  calendarSettingEntity: storeState.calendarSetting.entity,
  loading: storeState.calendarSetting.loading,
  updating: storeState.calendarSetting.updating,
  updateSuccess: storeState.calendarSetting.updateSuccess
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
)(CalendarSettingUpdate);
