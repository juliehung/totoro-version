import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './finding-type.reducer';
import { IFindingType } from 'app/shared/model/finding-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFindingTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FindingTypeDetail extends React.Component<IFindingTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { findingTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.findingType.detail.title">FindingType</Translate> [<b>{findingTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="major">
                <Translate contentKey="totoroApp.findingType.major">Major</Translate>
              </span>
            </dt>
            <dd>{findingTypeEntity.major}</dd>
            <dt>
              <span id="minor">
                <Translate contentKey="totoroApp.findingType.minor">Minor</Translate>
              </span>
            </dt>
            <dd>{findingTypeEntity.minor}</dd>
            <dt>
              <span id="display">
                <Translate contentKey="totoroApp.findingType.display">Display</Translate>
              </span>
            </dt>
            <dd>{findingTypeEntity.display ? 'true' : 'false'}</dd>
          </dl>
          <Button tag={Link} to="/entity/finding-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/finding-type/${findingTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ findingType }: IRootState) => ({
  findingTypeEntity: findingType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FindingTypeDetail);
