import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IPatientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IPatientState = IPaginationBaseState;

export class Patient extends React.Component<IPatientProps, IPatientState> {
  state: IPatientState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { patientList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="patient-heading">
          <Translate contentKey="totoroApp.patient.home.title">Patients</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.patient.home.createLabel">Create new Patient</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('name')}>
                  <Translate contentKey="totoroApp.patient.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('phone')}>
                  <Translate contentKey="totoroApp.patient.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('gender')}>
                  <Translate contentKey="totoroApp.patient.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('birth')}>
                  <Translate contentKey="totoroApp.patient.birth">Birth</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('nationalId')}>
                  <Translate contentKey="totoroApp.patient.nationalId">National Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('medicalId')}>
                  <Translate contentKey="totoroApp.patient.medicalId">Medical Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('address')}>
                  <Translate contentKey="totoroApp.patient.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('email')}>
                  <Translate contentKey="totoroApp.patient.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('blood')}>
                  <Translate contentKey="totoroApp.patient.blood">Blood</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('cardId')}>
                  <Translate contentKey="totoroApp.patient.cardId">Card Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('vip')}>
                  <Translate contentKey="totoroApp.patient.vip">Vip</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emergencyName')}>
                  <Translate contentKey="totoroApp.patient.emergencyName">Emergency Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emergencyPhone')}>
                  <Translate contentKey="totoroApp.patient.emergencyPhone">Emergency Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('deleteDate')}>
                  <Translate contentKey="totoroApp.patient.deleteDate">Delete Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('scaling')}>
                  <Translate contentKey="totoroApp.patient.scaling">Scaling</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('lineId')}>
                  <Translate contentKey="totoroApp.patient.lineId">Line Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fbId')}>
                  <Translate contentKey="totoroApp.patient.fbId">Fb Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('note')}>
                  <Translate contentKey="totoroApp.patient.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('clinicNote')}>
                  <Translate contentKey="totoroApp.patient.clinicNote">Clinic Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('writeIcTime')}>
                  <Translate contentKey="totoroApp.patient.writeIcTime">Write Ic Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('avatar')}>
                  <Translate contentKey="totoroApp.patient.avatar">Avatar</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('newPatient')}>
                  <Translate contentKey="totoroApp.patient.newPatient">New Patient</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emergencyAddress')}>
                  <Translate contentKey="totoroApp.patient.emergencyAddress">Emergency Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emergencyRelationship')}>
                  <Translate contentKey="totoroApp.patient.emergencyRelationship">Emergency Relationship</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('mainNoticeChannel')}>
                  <Translate contentKey="totoroApp.patient.mainNoticeChannel">Main Notice Channel</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('career')}>
                  <Translate contentKey="totoroApp.patient.career">Career</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('marriage')}>
                  <Translate contentKey="totoroApp.patient.marriage">Marriage</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('teethGraphPermanentSwitch')}>
                  <Translate contentKey="totoroApp.patient.teethGraphPermanentSwitch">Teeth Graph Permanent Switch</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.patient.questionnaire">Questionnaire</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.patient.introducer">Introducer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.patient.patientIdentity">Patient Identity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {patientList.map((patient, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${patient.id}`} color="link" size="sm">
                      {patient.id}
                    </Button>
                  </td>
                  <td>{patient.name}</td>
                  <td>{patient.phone}</td>
                  <td>
                    <Translate contentKey={`totoroApp.Gender.${patient.gender}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={patient.birth} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{patient.nationalId}</td>
                  <td>{patient.medicalId}</td>
                  <td>{patient.address}</td>
                  <td>{patient.email}</td>
                  <td>
                    <Translate contentKey={`totoroApp.Blood.${patient.blood}`} />
                  </td>
                  <td>{patient.cardId}</td>
                  <td>{patient.vip}</td>
                  <td>{patient.emergencyName}</td>
                  <td>{patient.emergencyPhone}</td>
                  <td>
                    <TextFormat type="date" value={patient.deleteDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={patient.scaling} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{patient.lineId}</td>
                  <td>{patient.fbId}</td>
                  <td>{patient.note}</td>
                  <td>{patient.clinicNote}</td>
                  <td>
                    <TextFormat type="date" value={patient.writeIcTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {patient.avatar ? (
                      <div>
                        <a onClick={openFile(patient.avatarContentType, patient.avatar)}>
                          <img src={`data:${patient.avatarContentType};base64,${patient.avatar}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {patient.avatarContentType}, {byteSize(patient.avatar)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{patient.newPatient ? 'true' : 'false'}</td>
                  <td>{patient.emergencyAddress}</td>
                  <td>{patient.emergencyRelationship}</td>
                  <td>{patient.mainNoticeChannel}</td>
                  <td>{patient.career}</td>
                  <td>{patient.marriage}</td>
                  <td>{patient.teethGraphPermanentSwitch}</td>
                  <td>
                    {patient.questionnaire ? <Link to={`questionnaire/${patient.questionnaire.id}`}>{patient.questionnaire.id}</Link> : ''}
                  </td>
                  <td>{patient.introducer ? <Link to={`patient/${patient.introducer.id}`}>{patient.introducer.id}</Link> : ''}</td>
                  <td>
                    {patient.patientIdentity ? (
                      <Link to={`patient-identity/${patient.patientIdentity.id}`}>{patient.patientIdentity.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {patient.questionnaire ? <Link to={`questionnaire/${patient.questionnaire.id}`}>{patient.questionnaire.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${patient.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${patient.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${patient.id}/delete`} color="danger" size="sm">
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
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ patient }: IRootState) => ({
  patientList: patient.entities,
  totalItems: patient.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Patient);
