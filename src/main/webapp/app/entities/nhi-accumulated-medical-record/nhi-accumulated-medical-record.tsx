import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
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
import { getEntities } from './nhi-accumulated-medical-record.reducer';
import { INhiAccumulatedMedicalRecord } from 'app/shared/model/nhi-accumulated-medical-record.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface INhiAccumulatedMedicalRecordProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type INhiAccumulatedMedicalRecordState = IPaginationBaseState;

export class NhiAccumulatedMedicalRecord extends React.Component<INhiAccumulatedMedicalRecordProps, INhiAccumulatedMedicalRecordState> {
  state: INhiAccumulatedMedicalRecordState = {
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
    const { nhiAccumulatedMedicalRecordList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="nhi-accumulated-medical-record-heading">
          <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.home.title">Nhi Accumulated Medical Records</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.home.createLabel">
              Create new Nhi Accumulated Medical Record
            </Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('medicalCategory')}>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.medicalCategory">Medical Category</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('newbornMedicalTreatmentNote')}>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.newbornMedicalTreatmentNote">
                    Newborn Medical Treatment Note
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('date')}>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('cardFillingNote')}>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.cardFillingNote">Card Filling Note</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('seqNumber')}>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.seqNumber">Seq Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('medicalInstitutionCode')}>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.medicalInstitutionCode">Medical Institution Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.patient">Patient</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiAccumulatedMedicalRecordList.map((nhiAccumulatedMedicalRecord, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiAccumulatedMedicalRecord.id}`} color="link" size="sm">
                      {nhiAccumulatedMedicalRecord.id}
                    </Button>
                  </td>
                  <td>{nhiAccumulatedMedicalRecord.medicalCategory}</td>
                  <td>{nhiAccumulatedMedicalRecord.newbornMedicalTreatmentNote}</td>
                  <td>
                    <TextFormat type="date" value={nhiAccumulatedMedicalRecord.date} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{nhiAccumulatedMedicalRecord.cardFillingNote}</td>
                  <td>{nhiAccumulatedMedicalRecord.seqNumber}</td>
                  <td>{nhiAccumulatedMedicalRecord.medicalInstitutionCode}</td>
                  <td>
                    {nhiAccumulatedMedicalRecord.patient ? (
                      <Link to={`patient/${nhiAccumulatedMedicalRecord.patient.id}`}>{nhiAccumulatedMedicalRecord.patient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiAccumulatedMedicalRecord.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiAccumulatedMedicalRecord.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiAccumulatedMedicalRecord.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiAccumulatedMedicalRecord }: IRootState) => ({
  nhiAccumulatedMedicalRecordList: nhiAccumulatedMedicalRecord.entities,
  totalItems: nhiAccumulatedMedicalRecord.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiAccumulatedMedicalRecord);
