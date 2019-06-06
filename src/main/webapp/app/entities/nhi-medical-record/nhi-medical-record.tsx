import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-medical-record.reducer';
import { INhiMedicalRecord } from 'app/shared/model/nhi-medical-record.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiMedicalRecordProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiMedicalRecord extends React.Component<INhiMedicalRecordProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiMedicalRecordList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-medical-record-heading">
          <Translate contentKey="totoroApp.nhiMedicalRecord.home.title">Nhi Medical Records</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiMedicalRecord.home.createLabel">Create new Nhi Medical Record</Translate>
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
                  <Translate contentKey="totoroApp.nhiMedicalRecord.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.nhiCategory">Nhi Category</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.nhiCode">Nhi Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.part">Part</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.usage">Usage</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.total">Total</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiMedicalRecord.nhiExtendPatient">Nhi Extend Patient</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiMedicalRecordList.map((nhiMedicalRecord, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiMedicalRecord.id}`} color="link" size="sm">
                      {nhiMedicalRecord.id}
                    </Button>
                  </td>
                  <td>{nhiMedicalRecord.date}</td>
                  <td>{nhiMedicalRecord.nhiCategory}</td>
                  <td>{nhiMedicalRecord.nhiCode}</td>
                  <td>{nhiMedicalRecord.part}</td>
                  <td>{nhiMedicalRecord.usage}</td>
                  <td>{nhiMedicalRecord.total}</td>
                  <td>{nhiMedicalRecord.note}</td>
                  <td>
                    {nhiMedicalRecord.nhiExtendPatient ? (
                      <Link to={`nhi-extend-patient/${nhiMedicalRecord.nhiExtendPatient.id}`}>{nhiMedicalRecord.nhiExtendPatient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiMedicalRecord.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiMedicalRecord.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiMedicalRecord.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiMedicalRecord }: IRootState) => ({
  nhiMedicalRecordList: nhiMedicalRecord.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMedicalRecord);
