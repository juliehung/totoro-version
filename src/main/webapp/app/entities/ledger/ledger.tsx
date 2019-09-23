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
import { getEntities } from './ledger.reducer';
import { ILedger } from 'app/shared/model/ledger.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ILedgerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ILedgerState = IPaginationBaseState;

export class Ledger extends React.Component<ILedgerProps, ILedgerState> {
  state: ILedgerState = {
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
    const { ledgerList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="ledger-heading">
          <Translate contentKey="totoroApp.ledger.home.title">Ledgers</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.ledger.home.createLabel">Create new Ledger</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('amount')}>
                  <Translate contentKey="totoroApp.ledger.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('charge')}>
                  <Translate contentKey="totoroApp.ledger.charge">Charge</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('arrears')}>
                  <Translate contentKey="totoroApp.ledger.arrears">Arrears</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('note')}>
                  <Translate contentKey="totoroApp.ledger.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('doctor')}>
                  <Translate contentKey="totoroApp.ledger.doctor">Doctor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('gid')}>
                  <Translate contentKey="totoroApp.ledger.gid">Gid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('displayName')}>
                  <Translate contentKey="totoroApp.ledger.displayName">Display Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('patientId')}>
                  <Translate contentKey="totoroApp.ledger.patientId">Patient Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('type')}>
                  <Translate contentKey="totoroApp.ledger.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('project_code')}>
                  <Translate contentKey="totoroApp.ledger.project_code">Project Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('date')}>
                  <Translate contentKey="totoroApp.ledger.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdDate')}>
                  <Translate contentKey="totoroApp.ledger.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdBy')}>
                  <Translate contentKey="totoroApp.ledger.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('lastModifiedDate')}>
                  <Translate contentKey="totoroApp.ledger.lastModifiedDate">Last Modified Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('lastModifiedBy')}>
                  <Translate contentKey="totoroApp.ledger.lastModifiedBy">Last Modified By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.ledger.treatmentPlan">Treatment Plan</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ledgerList.map((ledger, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${ledger.id}`} color="link" size="sm">
                      {ledger.id}
                    </Button>
                  </td>
                  <td>{ledger.amount}</td>
                  <td>{ledger.charge}</td>
                  <td>{ledger.arrears}</td>
                  <td>{ledger.note}</td>
                  <td>{ledger.doctor}</td>
                  <td>{ledger.gid}</td>
                  <td>{ledger.displayName}</td>
                  <td>{ledger.patientId}</td>
                  <td>{ledger.type}</td>
                  <td>{ledger.project_code}</td>
                  <td>
                    <TextFormat type="date" value={ledger.date} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={ledger.createdDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{ledger.createdBy}</td>
                  <td>
                    <TextFormat type="date" value={ledger.lastModifiedDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{ledger.lastModifiedBy}</td>
                  <td>
                    {ledger.treatmentPlan ? <Link to={`treatment-plan/${ledger.treatmentPlan.id}`}>{ledger.treatmentPlan.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ledger.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ledger.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ledger.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ ledger }: IRootState) => ({
  ledgerList: ledger.entities,
  totalItems: ledger.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Ledger);
