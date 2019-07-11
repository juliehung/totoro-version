import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-month-declaration-details.reducer';
import { INhiMonthDeclarationDetails } from 'app/shared/model/nhi-month-declaration-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiMonthDeclarationDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiMonthDeclarationDetailsDetail extends React.Component<INhiMonthDeclarationDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiMonthDeclarationDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.detail.title">NhiMonthDeclarationDetails</Translate> [
            <b>{nhiMonthDeclarationDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.type">Type</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.type}</dd>
            <dt>
              <span id="way">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.way">Way</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.way}</dd>
            <dt>
              <span id="caseTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.caseTotal">Case Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.caseTotal}</dd>
            <dt>
              <span id="pointTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.pointTotal">Point Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.pointTotal}</dd>
            <dt>
              <span id="outPatientPoint">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.outPatientPoint">Out Patient Point</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.outPatientPoint}</dd>
            <dt>
              <span id="preventiveCaseTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.preventiveCaseTotal">Preventive Case Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.preventiveCaseTotal}</dd>
            <dt>
              <span id="preventivePointTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.preventivePointTotal">Preventive Point Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.preventivePointTotal}</dd>
            <dt>
              <span id="generalCaseTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.generalCaseTotal">General Case Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.generalCaseTotal}</dd>
            <dt>
              <span id="generalPointTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.generalPointTotal">General Point Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.generalPointTotal}</dd>
            <dt>
              <span id="professionalCaseTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.professionalCaseTotal">Professional Case Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.professionalCaseTotal}</dd>
            <dt>
              <span id="professionalPointTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.professionalPointTotal">Professional Point Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.professionalPointTotal}</dd>
            <dt>
              <span id="partialCaseTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.partialCaseTotal">Partial Case Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.partialCaseTotal}</dd>
            <dt>
              <span id="partialPointTotal">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.partialPointTotal">Partial Point Total</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.partialPointTotal}</dd>
            <dt>
              <span id="file">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.file">File</Translate>
              </span>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.file}</dd>
            <dt>
              <span id="uploadTime">
                <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.uploadTime">Upload Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={nhiMonthDeclarationDetailsEntity.uploadTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.nhiMonthDeclaration">Nhi Month Declaration</Translate>
            </dt>
            <dd>{nhiMonthDeclarationDetailsEntity.nhiMonthDeclaration ? nhiMonthDeclarationDetailsEntity.nhiMonthDeclaration.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-month-declaration-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button
            tag={Link}
            to={`/entity/nhi-month-declaration-details/${nhiMonthDeclarationDetailsEntity.id}/edit`}
            replace
            color="primary"
          >
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

const mapStateToProps = ({ nhiMonthDeclarationDetails }: IRootState) => ({
  nhiMonthDeclarationDetailsEntity: nhiMonthDeclarationDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMonthDeclarationDetailsDetail);
