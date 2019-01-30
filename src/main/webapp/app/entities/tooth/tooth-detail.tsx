import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tooth.reducer';
import { ITooth } from 'app/shared/model/tooth.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IToothDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ToothDetail extends React.Component<IToothDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { toothEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.tooth.detail.title">Tooth</Translate> [<b>{toothEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="position">
                <Translate contentKey="totoroApp.tooth.position">Position</Translate>
              </span>
            </dt>
            <dd>{toothEntity.position}</dd>
            <dt>
              <span id="before">
                <Translate contentKey="totoroApp.tooth.before">Before</Translate>
              </span>
            </dt>
            <dd>{toothEntity.before}</dd>
            <dt>
              <span id="planned">
                <Translate contentKey="totoroApp.tooth.planned">Planned</Translate>
              </span>
            </dt>
            <dd>{toothEntity.planned}</dd>
            <dt>
              <span id="after">
                <Translate contentKey="totoroApp.tooth.after">After</Translate>
              </span>
            </dt>
            <dd>{toothEntity.after}</dd>
            <dt>
              <Translate contentKey="totoroApp.tooth.treatmentTask">Treatment Task</Translate>
            </dt>
            <dd>{toothEntity.treatmentTask ? toothEntity.treatmentTask.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.tooth.treatmentProcedure">Treatment Procedure</Translate>
            </dt>
            <dd>{toothEntity.treatmentProcedure ? toothEntity.treatmentProcedure.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/tooth" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/tooth/${toothEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ tooth }: IRootState) => ({
  toothEntity: tooth.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ToothDetail);
