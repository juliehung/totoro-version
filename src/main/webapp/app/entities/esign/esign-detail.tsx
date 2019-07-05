import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './esign.reducer';
import { IEsign } from 'app/shared/model/esign.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEsignDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EsignDetail extends React.Component<IEsignDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { esignEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.esign.detail.title">Esign</Translate> [<b>{esignEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="patientId">
                <Translate contentKey="totoroApp.esign.patientId">Patient Id</Translate>
              </span>
            </dt>
            <dd>{esignEntity.patientId}</dd>
            <dt>
              <span id="lob">
                <Translate contentKey="totoroApp.esign.lob">Lob</Translate>
              </span>
            </dt>
            <dd>
              {esignEntity.lob ? (
                <div>
                  <a onClick={openFile(esignEntity.lobContentType, esignEntity.lob)}>
                    <img src={`data:${esignEntity.lobContentType};base64,${esignEntity.lob}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {esignEntity.lobContentType}, {byteSize(esignEntity.lob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="totoroApp.esign.createTime">Create Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={esignEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateTime">
                <Translate contentKey="totoroApp.esign.updateTime">Update Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={esignEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="sourceType">
                <Translate contentKey="totoroApp.esign.sourceType">Source Type</Translate>
              </span>
            </dt>
            <dd>{esignEntity.sourceType}</dd>
          </dl>
          <Button tag={Link} to="/entity/esign" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/esign/${esignEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ esign }: IRootState) => ({
  esignEntity: esign.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EsignDetail);
