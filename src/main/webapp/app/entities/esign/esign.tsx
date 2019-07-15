import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './esign.reducer';
import { IEsign } from 'app/shared/model/esign.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEsignProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Esign extends React.Component<IEsignProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { esignList, match } = this.props;
    return (
      <div>
        <h2 id="esign-heading">
          <Translate contentKey="totoroApp.esign.home.title">Esigns</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.esign.home.createLabel">Create new Esign</Translate>
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
                  <Translate contentKey="totoroApp.esign.patientId">Patient Id</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.esign.lob">Lob</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.esign.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.esign.createdBy">Created By</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.esign.sourceType">Source Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {esignList.map((esign, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${esign.id}`} color="link" size="sm">
                      {esign.id}
                    </Button>
                  </td>
                  <td>{esign.patientId}</td>
                  <td>
                    {esign.lob ? (
                      <div>
                        <a onClick={openFile(esign.lobContentType, esign.lob)}>
                          <img src={`data:${esign.lobContentType};base64,${esign.lob}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {esign.lobContentType}, {byteSize(esign.lob)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <TextFormat type="date" value={esign.createdDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{esign.createdBy}</td>
                  <td>
                    <Translate contentKey={`totoroApp.SourceType.${esign.sourceType}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${esign.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${esign.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${esign.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ esign }: IRootState) => ({
  esignList: esign.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Esign);
