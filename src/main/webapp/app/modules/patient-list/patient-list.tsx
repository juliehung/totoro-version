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
import { getEntities } from 'app/entities/patient/patient.reducer';
import { Gender, IPatient } from 'app/shared/model/patient.model';
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
    const renderProfile = gender => {
      if (gender === Gender.MALE) {
        return <img src="content/images/man@2x.png" width={58} height={58} />;
      } else if (gender === Gender.FEMALE) {
        return <img src="content/images/woman@2x.png" width={58} height={58} />;
      } else {
        return (
          <div
            style={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              borderRadius: '36px',
              width: '58px',
              height: '58px',
              backgroundColor: '#1768ac'
            }}
          >
            <img src="content/images/logo.png" width={36} height={36} />
          </div>
        );
      }
    };
    return (
      <div>
        <h2 id="patient-heading">{/*<Translate contentKey="totoroApp.patient.home.title">Patients</Translate>*/}</h2>
        <Row>
          {patientList.map((patient, i) => (
            <Col md="6" lg="4" key={`p-${i}`}>
              <Link to={'/survey?pid=' + patient.id} key={`p-${i}`}>
                <div style={{ height: '171px', marginBottom: '23px' }}>
                  <Card outline color="primary" style={{ height: '100%' }}>
                    <div className="cardBody" style={{ paddingTop: '33px', paddingLeft: '38px', flex: '1 1 auto' }}>
                      <div style={{ display: 'flex' }}>
                        {renderProfile(patient.gender)}
                        <div style={{ marginLeft: '16px', marginTop: '-7px' }}>
                          <div>
                            <CardTitle style={{ fontSize: '24px', fontWeight: 600, lineHeight: 1.62 }}>{patient.name}</CardTitle>
                            <CardSubtitle style={{ lineHeight: 1.61 }}>{`病歷編號 ${patient.medicalId}`}</CardSubtitle>
                          </div>
                          <div>
                            <div style={{ marginTop: '16px', color: '#c2cbd5' }}>
                              <CardSubtitle style={{ lineHeight: 1.61 }}>{`新增日期 ${patient.lastModifiedDate
                                .slice(0, 16)
                                .split('T')
                                .join(' ')}`}</CardSubtitle>
                            </div>
                          </div>
                        </div>
                      </div>
                      <CardText style={{ minHeight: '23px' }}>
                        <small>{patient.birth}</small>
                      </CardText>
                    </div>
                  </Card>
                </div>
              </Link>
            </Col>
          ))}
        </Row>
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
