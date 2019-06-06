import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-extend-patient.reducer';
import { INhiExtendPatient } from 'app/shared/model/nhi-extend-patient.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiExtendPatientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiExtendPatient extends React.Component<INhiExtendPatientProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiExtendPatientList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-extend-patient-heading">
          <Translate contentKey="totoroApp.nhiExtendPatient.home.title">Nhi Extend Patients</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiExtendPatient.home.createLabel">Create new Nhi Extend Patient</Translate>
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
                  <Translate contentKey="totoroApp.nhiExtendPatient.cardNumber">Card Number</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiExtendPatient.cardAnnotation">Card Annotation</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiExtendPatient.cardValidDate">Card Valid Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiExtendPatient.cardIssueDate">Card Issue Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiExtendPatient.nhiIdentity">Nhi Identity</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiExtendPatient.availableTimes">Available Times</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiExtendPatientList.map((nhiExtendPatient, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiExtendPatient.id}`} color="link" size="sm">
                      {nhiExtendPatient.id}
                    </Button>
                  </td>
                  <td>{nhiExtendPatient.cardNumber}</td>
                  <td>{nhiExtendPatient.cardAnnotation}</td>
                  <td>
                    <TextFormat type="date" value={nhiExtendPatient.cardValidDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={nhiExtendPatient.cardIssueDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{nhiExtendPatient.nhiIdentity}</td>
                  <td>{nhiExtendPatient.availableTimes}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiExtendPatient.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiExtendPatient.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiExtendPatient.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiExtendPatient }: IRootState) => ({
  nhiExtendPatientList: nhiExtendPatient.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiExtendPatient);