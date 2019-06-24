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
import { getEntities } from './accounting.reducer';
import { IAccounting } from 'app/shared/model/accounting.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IAccountingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IAccountingState = IPaginationBaseState;

export class Accounting extends React.Component<IAccountingProps, IAccountingState> {
  state: IAccountingState = {
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
    const { accountingList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="accounting-heading">
          <Translate contentKey="totoroApp.accounting.home.title">Accountings</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.accounting.home.createLabel">Create new Accounting</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('registrationFee')}>
                  <Translate contentKey="totoroApp.accounting.registrationFee">Registration Fee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partialBurden')}>
                  <Translate contentKey="totoroApp.accounting.partialBurden">Partial Burden</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('deposit')}>
                  <Translate contentKey="totoroApp.accounting.deposit">Deposit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('ownExpense')}>
                  <Translate contentKey="totoroApp.accounting.ownExpense">Own Expense</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('other')}>
                  <Translate contentKey="totoroApp.accounting.other">Other</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('patientIdentity')}>
                  <Translate contentKey="totoroApp.accounting.patientIdentity">Patient Identity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('discountReason')}>
                  <Translate contentKey="totoroApp.accounting.discountReason">Discount Reason</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('discount')}>
                  <Translate contentKey="totoroApp.accounting.discount">Discount</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('withdrawal')}>
                  <Translate contentKey="totoroApp.accounting.withdrawal">Withdrawal</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('transactionTime')}>
                  <Translate contentKey="totoroApp.accounting.transactionTime">Transaction Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('staff')}>
                  <Translate contentKey="totoroApp.accounting.staff">Staff</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.accounting.hospital">Hospital</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {accountingList.map((accounting, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${accounting.id}`} color="link" size="sm">
                      {accounting.id}
                    </Button>
                  </td>
                  <td>{accounting.registrationFee}</td>
                  <td>{accounting.partialBurden}</td>
                  <td>{accounting.deposit}</td>
                  <td>{accounting.ownExpense}</td>
                  <td>{accounting.other}</td>
                  <td>{accounting.patientIdentity}</td>
                  <td>{accounting.discountReason}</td>
                  <td>{accounting.discount}</td>
                  <td>{accounting.withdrawal}</td>
                  <td>
                    <TextFormat type="date" value={accounting.transactionTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{accounting.staff}</td>
                  <td>{accounting.hospital ? <Link to={`hospital/${accounting.hospital.id}`}>{accounting.hospital.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${accounting.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accounting.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accounting.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ accounting }: IRootState) => ({
  accountingList: accounting.entities,
  totalItems: accounting.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Accounting);
