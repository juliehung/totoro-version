import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './drug.reducer';
import { IDrug } from 'app/shared/model/drug.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDrugProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Drug extends React.Component<IDrugProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { drugList, match } = this.props;
    return (
      <div>
        <h2 id="drug-heading">
          <Translate contentKey="totoroApp.drug.home.title">Drugs</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.drug.home.createLabel">Create new Drug</Translate>
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
                  <Translate contentKey="totoroApp.drug.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.chineseName">Chinese Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.unit">Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.frequency">Frequency</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.way">Way</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.nhiCode">Nhi Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.warning">Warning</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.days">Days</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.order">Order</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.createdBy">Created By</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.lastModifiedDate">Last Modified Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.drug.LastModifiedBy">Last Modified By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {drugList.map((drug, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${drug.id}`} color="link" size="sm">
                      {drug.id}
                    </Button>
                  </td>
                  <td>{drug.name}</td>
                  <td>{drug.chineseName}</td>
                  <td>{drug.unit}</td>
                  <td>{drug.price}</td>
                  <td>{drug.quantity}</td>
                  <td>{drug.frequency}</td>
                  <td>{drug.way}</td>
                  <td>{drug.nhiCode}</td>
                  <td>{drug.warning}</td>
                  <td>{drug.days}</td>
                  <td>{drug.order}</td>
                  <td>
                    <TextFormat type="date" value={drug.createdDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{drug.createdBy}</td>
                  <td>
                    <TextFormat type="date" value={drug.lastModifiedDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{drug.LastModifiedBy}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${drug.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${drug.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${drug.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ drug }: IRootState) => ({
  drugList: drug.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Drug);
