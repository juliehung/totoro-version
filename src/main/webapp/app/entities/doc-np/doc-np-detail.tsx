import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './doc-np.reducer';
import { IDocNp } from 'app/shared/model/doc-np.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDocNpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DocNpDetail extends React.Component<IDocNpDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { docNpEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.docNp.detail.title">DocNp</Translate> [<b>{docNpEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="patient">
                <Translate contentKey="totoroApp.docNp.patient">Patient</Translate>
              </span>
            </dt>
            <dd>{docNpEntity.patient}</dd>
            <dt>
              <span id="patientId">
                <Translate contentKey="totoroApp.docNp.patientId">Patient Id</Translate>
              </span>
            </dt>
            <dd>{docNpEntity.patientId}</dd>
            <dt>
              <span id="esignId">
                <Translate contentKey="totoroApp.docNp.esignId">Esign Id</Translate>
              </span>
            </dt>
            <dd>{docNpEntity.esignId}</dd>
            <dt>
              <span id="createdDate">
                <Translate contentKey="totoroApp.docNp.createdDate">Created Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={docNpEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="totoroApp.docNp.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{docNpEntity.createdBy}</dd>
          </dl>
          <Button tag={Link} to="/entity/doc-np" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/doc-np/${docNpEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ docNp }: IRootState) => ({
  docNpEntity: docNp.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DocNpDetail);
