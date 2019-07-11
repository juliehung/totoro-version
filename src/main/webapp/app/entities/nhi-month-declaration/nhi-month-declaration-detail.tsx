import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-month-declaration.reducer';
import { INhiMonthDeclaration } from 'app/shared/model/nhi-month-declaration.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiMonthDeclarationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiMonthDeclarationDetail extends React.Component<INhiMonthDeclarationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiMonthDeclarationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiMonthDeclaration.detail.title">NhiMonthDeclaration</Translate> [
            <b>{nhiMonthDeclarationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="yearMonth">
                <Translate contentKey="totoroApp.nhiMonthDeclaration.yearMonth">Year Month</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationEntity.yearMonth}</dd>
            <dt>
              <span id="institution">
                <Translate contentKey="totoroApp.nhiMonthDeclaration.institution">Institution</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationEntity.institution}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-month-declaration" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-month-declaration/${nhiMonthDeclarationEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiMonthDeclaration }: IRootState) => ({
  nhiMonthDeclarationEntity: nhiMonthDeclaration.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMonthDeclarationDetail);
