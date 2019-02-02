import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import {
  CardColumns,
  Card,
  CardBody,
  CardTitle,
  CardSubtitle,
  CardHeader,
  CardText,
  CardFooter,
  CardImg,
  Button,
  Col,
  Row
} from 'reactstrap';
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
import './patient-list.css';

export type IPatientListState = IPaginationBaseState;

export interface IPatientListProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export class PatientList extends React.Component<IPatientListProps, IPatientListState> {
  state: IPatientListState = {
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
        </h2>

        <CardColumns>
          {patientList.map((patient, i) => (
            <Link to={'/survey?medicalId=' + patient.medicalId + '&name=' + patient.name} key={`entity-${i}`}>
              <Card outline color="primary">
                <CardBody>
                  <CardTitle>{patient.name}</CardTitle>
                  <CardSubtitle>{patient.medicalId}</CardSubtitle>
                  <CardText>{patient.birth}</CardText>
                </CardBody>
              </Card>
            </Link>
          ))}
        </CardColumns>

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
)(PatientList);
