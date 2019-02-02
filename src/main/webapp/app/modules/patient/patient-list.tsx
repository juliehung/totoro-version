import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import {
  CardColumns,
  CardDeck,
  CardGroup,
  Card,
  CardBody,
  CardTitle,
  CardHeader,
  CardText,
  CardFooter,
  CardImg,
  Button,
  Col,
  Row,
  Table
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
import { getEntities, reset } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import './patient-list.css';

interface IPatientListState extends IPaginationBaseState {
  isAuthenticated: boolean;
}

export interface IPatientListProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export class PatientList extends React.Component<IPatientListProps, IPatientListState> {
  state: IPatientListState = {
    isAuthenticated: this.props.isAuthenticated,
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    if (this.state.isAuthenticated) {
      this.getEntities();
    } else {
      this.props.reset();
    }
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

  checkAuthenticated = () => {
    if (this.state.isAuthenticated !== this.props.isAuthenticated) {
      this.setState({ isAuthenticated: this.props.isAuthenticated });
      this.getEntities();
    }
  };

  render() {
    const { patientList, match, totalItems } = this.props;
    {
      this.checkAuthenticated();
    }
    return (
      <div>
        <h2 id="patient-heading">
          <Translate contentKey="totoroApp.patient.home.title">Patients</Translate>
        </h2>

        <CardColumns>
          {patientList.map((patient, i) => (
            <Card key={`entity-${i}`} outline color="primary">
              <CardHeader tag="h3">{patient.name}</CardHeader>
              <CardBody>
                <CardTitle>{patient.medicalId}</CardTitle>
                <CardText>{patient.medicalId}</CardText>
              </CardBody>
              <CardFooter className="text-muted">{patient.questionnaire}</CardFooter>
            </Card>
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

const mapStateToProps = ({ patient, authentication }: IRootState) => ({
  patientList: patient.entities,
  totalItems: patient.totalItems,
  isAuthenticated: authentication.isAuthenticated
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PatientList);
