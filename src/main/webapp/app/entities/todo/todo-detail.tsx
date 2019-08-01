import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './todo.reducer';
import { ITodo } from 'app/shared/model/todo.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITodoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TodoDetail extends React.Component<ITodoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { todoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.todo.detail.title">Todo</Translate> [<b>{todoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="totoroApp.todo.status">Status</Translate>
              </span>
            </dt>
            <dd>{todoEntity.status}</dd>
            <dt>
              <span id="expectedDate">
                <Translate contentKey="totoroApp.todo.expectedDate">Expected Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={todoEntity.expectedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="requiredTreatmentTime">
                <Translate contentKey="totoroApp.todo.requiredTreatmentTime">Required Treatment Time</Translate>
              </span>
            </dt>
            <dd>{todoEntity.requiredTreatmentTime}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.todo.note">Note</Translate>
              </span>
            </dt>
            <dd>{todoEntity.note}</dd>
            <dt>
              <Translate contentKey="totoroApp.todo.patient">Patient</Translate>
            </dt>
            <dd>{todoEntity.patient ? todoEntity.patient.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.todo.treatmentProcedure">Treatment Procedure</Translate>
            </dt>
            <dd>
              {todoEntity.treatmentProcedures
                ? todoEntity.treatmentProcedures.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === todoEntity.treatmentProcedures.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/todo" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/todo/${todoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ todo }: IRootState) => ({
  todoEntity: todo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TodoDetail);
