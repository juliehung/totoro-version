import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './questionnaire.reducer';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IQuestionnaireProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IQuestionnaireState = IPaginationBaseState;

export class Questionnaire extends React.Component<IQuestionnaireProps, IQuestionnaireState> {
  state: IQuestionnaireState = {
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
    const { questionnaireList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="questionnaire-heading">
          <Translate contentKey="totoroApp.questionnaire.home.title">Questionnaires</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.questionnaire.home.createLabel">Create new Questionnaire</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('hypertension')}>
                  <Translate contentKey="totoroApp.questionnaire.hypertension">Hypertension</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('heartDiseases')}>
                  <Translate contentKey="totoroApp.questionnaire.heartDiseases">Heart Diseases</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('kidneyDiseases')}>
                  <Translate contentKey="totoroApp.questionnaire.kidneyDiseases">Kidney Diseases</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('bloodDiseases')}>
                  <Translate contentKey="totoroApp.questionnaire.bloodDiseases">Blood Diseases</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('liverDiseases')}>
                  <Translate contentKey="totoroApp.questionnaire.liverDiseases">Liver Diseases</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('hepatitisType')}>
                  <Translate contentKey="totoroApp.questionnaire.hepatitisType">Hepatitis Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('gastrointestinalDiseases')}>
                  <Translate contentKey="totoroApp.questionnaire.gastrointestinalDiseases">Gastrointestinal Diseases</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('receivingMedication')}>
                  <Translate contentKey="totoroApp.questionnaire.receivingMedication">Receiving Medication</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('anyAllergySensitivity')}>
                  <Translate contentKey="totoroApp.questionnaire.anyAllergySensitivity">Any Allergy Sensitivity</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('glycemicAC')}>
                  <Translate contentKey="totoroApp.questionnaire.glycemicAC">Glycemic AC</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('glycemicPC')}>
                  <Translate contentKey="totoroApp.questionnaire.glycemicPC">Glycemic PC</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('smokeNumberADay')}>
                  <Translate contentKey="totoroApp.questionnaire.smokeNumberADay">Smoke Number A Day</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('productionYear')}>
                  <Translate contentKey="totoroApp.questionnaire.productionYear">Production Year</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('productionMonth')}>
                  <Translate contentKey="totoroApp.questionnaire.productionMonth">Production Month</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('other')}>
                  <Translate contentKey="totoroApp.questionnaire.other">Other</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('difficultExtractionOrContinuousBleeding')}>
                  <Translate contentKey="totoroApp.questionnaire.difficultExtractionOrContinuousBleeding">
                    Difficult Extraction Or Continuous Bleeding
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('nauseaOrDizziness')}>
                  <Translate contentKey="totoroApp.questionnaire.nauseaOrDizziness">Nausea Or Dizziness</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('adverseReactionsToAnestheticInjections')}>
                  <Translate contentKey="totoroApp.questionnaire.adverseReactionsToAnestheticInjections">
                    Adverse Reactions To Anesthetic Injections
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('otherInTreatment')}>
                  <Translate contentKey="totoroApp.questionnaire.otherInTreatment">Other In Treatment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {questionnaireList.map((questionnaire, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${questionnaire.id}`} color="link" size="sm">
                      {questionnaire.id}
                    </Button>
                  </td>
                  <td>{questionnaire.hypertension}</td>
                  <td>{questionnaire.heartDiseases}</td>
                  <td>{questionnaire.kidneyDiseases}</td>
                  <td>{questionnaire.bloodDiseases}</td>
                  <td>{questionnaire.liverDiseases}</td>
                  <td>
                    <Translate contentKey={`totoroApp.Hepatitis.${questionnaire.hepatitisType}`} />
                  </td>
                  <td>{questionnaire.gastrointestinalDiseases}</td>
                  <td>{questionnaire.receivingMedication}</td>
                  <td>{questionnaire.anyAllergySensitivity}</td>
                  <td>{questionnaire.glycemicAC}</td>
                  <td>{questionnaire.glycemicPC}</td>
                  <td>{questionnaire.smokeNumberADay}</td>
                  <td>{questionnaire.productionYear}</td>
                  <td>
                    <Translate contentKey={`totoroApp.Month.${questionnaire.productionMonth}`} />
                  </td>
                  <td>{questionnaire.other}</td>
                  <td>{questionnaire.difficultExtractionOrContinuousBleeding ? 'true' : 'false'}</td>
                  <td>{questionnaire.nauseaOrDizziness ? 'true' : 'false'}</td>
                  <td>{questionnaire.adverseReactionsToAnestheticInjections ? 'true' : 'false'}</td>
                  <td>{questionnaire.otherInTreatment}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${questionnaire.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${questionnaire.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${questionnaire.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ questionnaire }: IRootState) => ({
  questionnaireList: questionnaire.entities,
  totalItems: questionnaire.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Questionnaire);
