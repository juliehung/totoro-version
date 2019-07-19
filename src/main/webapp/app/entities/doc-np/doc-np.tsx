import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './doc-np.reducer';
import { IDocNp } from 'app/shared/model/doc-np.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDocNpProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class DocNp extends React.Component<IDocNpProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { docNpList, match } = this.props;
    return (
      <div>
        <h2 id="doc-np-heading">
          <Translate contentKey="totoroApp.docNp.home.title">Doc Nps</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.docNp.home.createLabel">Create new Doc Np</Translate>
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
                  <Translate contentKey="totoroApp.docNp.patient">Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.docNp.patientId">Patient Id</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.docNp.esignId">Esign Id</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.docNp.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.docNp.createdBy">Created By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {docNpList.map((docNp, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${docNp.id}`} color="link" size="sm">
                      {docNp.id}
                    </Button>
                  </td>
                  <td>{docNp.patient}</td>
                  <td>{docNp.patientId}</td>
                  <td>{docNp.esignId}</td>
                  <td>
                    <TextFormat type="date" value={docNp.createdDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{docNp.createdBy}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${docNp.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${docNp.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${docNp.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ docNp }: IRootState) => ({
  docNpList: docNp.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DocNp);
