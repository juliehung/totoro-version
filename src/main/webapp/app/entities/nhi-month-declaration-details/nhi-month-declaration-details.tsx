import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-month-declaration-details.reducer';
import { INhiMonthDeclarationDetails } from 'app/shared/model/nhi-month-declaration-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiMonthDeclarationDetailsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiMonthDeclarationDetails extends React.Component<INhiMonthDeclarationDetailsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiMonthDeclarationDetailsList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-month-declaration-details-heading">
          <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.home.title">Nhi Month Declaration Details</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.home.createLabel">
              Create new Nhi Month Declaration Details
            </Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.way">Way</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.caseTotal">Case Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.pointTotal">Point Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.outPatientPoint">Out Patient Point</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.preventiveCaseTotal">Preventive Case Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.preventivePointTotal">Preventive Point Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.generalCaseTotal">General Case Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.generalPointTotal">General Point Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.professionalCaseTotal">Professional Case Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.professionalPointTotal">Professional Point Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.partialCaseTotal">Partial Case Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.partialPointTotal">Partial Point Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.file">File</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.uploadTime">Upload Time</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.localId">Local Id</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.nhiId">Nhi Id</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.nhiMonthDeclaration">Nhi Month Declaration</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiMonthDeclarationDetailsList.map((nhiMonthDeclarationDetails, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiMonthDeclarationDetails.id}`} color="link" size="sm">
                      {nhiMonthDeclarationDetails.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`totoroApp.NhiMonthDeclarationType.${nhiMonthDeclarationDetails.type}`} />
                  </td>
                  <td>{nhiMonthDeclarationDetails.way}</td>
                  <td>{nhiMonthDeclarationDetails.caseTotal}</td>
                  <td>{nhiMonthDeclarationDetails.pointTotal}</td>
                  <td>{nhiMonthDeclarationDetails.outPatientPoint}</td>
                  <td>{nhiMonthDeclarationDetails.preventiveCaseTotal}</td>
                  <td>{nhiMonthDeclarationDetails.preventivePointTotal}</td>
                  <td>{nhiMonthDeclarationDetails.generalCaseTotal}</td>
                  <td>{nhiMonthDeclarationDetails.generalPointTotal}</td>
                  <td>{nhiMonthDeclarationDetails.professionalCaseTotal}</td>
                  <td>{nhiMonthDeclarationDetails.professionalPointTotal}</td>
                  <td>{nhiMonthDeclarationDetails.partialCaseTotal}</td>
                  <td>{nhiMonthDeclarationDetails.partialPointTotal}</td>
                  <td>{nhiMonthDeclarationDetails.file}</td>
                  <td>
                    <TextFormat type="date" value={nhiMonthDeclarationDetails.uploadTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{nhiMonthDeclarationDetails.localId}</td>
                  <td>{nhiMonthDeclarationDetails.nhiId}</td>
                  <td>
                    {nhiMonthDeclarationDetails.nhiMonthDeclaration ? (
                      <Link to={`nhi-month-declaration/${nhiMonthDeclarationDetails.nhiMonthDeclaration.id}`}>
                        {nhiMonthDeclarationDetails.nhiMonthDeclaration.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiMonthDeclarationDetails.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiMonthDeclarationDetails.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiMonthDeclarationDetails.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ nhiMonthDeclarationDetails }: IRootState) => ({
  nhiMonthDeclarationDetailsList: nhiMonthDeclarationDetails.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMonthDeclarationDetails);
