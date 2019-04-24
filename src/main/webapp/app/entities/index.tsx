import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Patient from './patient';
import Appointment from './appointment';
import Registration from './registration';
import Tag from './tag';
import Questionnaire from './questionnaire';
import TreatmentTask from './treatment-task';
import TreatmentProcedure from './treatment-procedure';
import NHIProcedure from './nhi-procedure';
import NHICategory from './nhi-category';
import Hospital from './hospital';
import Accounting from './accounting';
import Calendar from './calendar';
import NHIUnusalIncident from './nhi-unusal-incident';
import NHIUnusalContent from './nhi-unusal-content';
import PatientIdentity from './patient-identity';
import CalendarSetting from './calendar-setting';
import Tooth from './tooth';
import Ledger from './ledger';
import Procedure from './procedure';
import ProcedureType from './procedure-type';
import Treatment from './treatment';
import TreatmentPlan from './treatment-plan';
import Drug from './drug';
import Todo from './todo';
import Prescription from './prescription';
import TreatmentDrug from './treatment-drug';
import Disposal from './disposal';
import RegistrationDel from './registration-del';
import ConditionType from './condition-type';
import NHIProcedureType from './nhi-procedure-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/patient`} component={Patient} />
      <ErrorBoundaryRoute path={`${match.url}/appointment`} component={Appointment} />
      <ErrorBoundaryRoute path={`${match.url}/registration`} component={Registration} />
      <ErrorBoundaryRoute path={`${match.url}/tag`} component={Tag} />
      <ErrorBoundaryRoute path={`${match.url}/questionnaire`} component={Questionnaire} />
      <ErrorBoundaryRoute path={`${match.url}/treatment-task`} component={TreatmentTask} />
      <ErrorBoundaryRoute path={`${match.url}/treatment-procedure`} component={TreatmentProcedure} />
      <ErrorBoundaryRoute path={`${match.url}/nhi-procedure`} component={NHIProcedure} />
      <ErrorBoundaryRoute path={`${match.url}/nhi-category`} component={NHICategory} />
      <ErrorBoundaryRoute path={`${match.url}/hospital`} component={Hospital} />
      <ErrorBoundaryRoute path={`${match.url}/accounting`} component={Accounting} />
      <ErrorBoundaryRoute path={`${match.url}/calendar`} component={Calendar} />
      <ErrorBoundaryRoute path={`${match.url}/nhi-unusal-incident`} component={NHIUnusalIncident} />
      <ErrorBoundaryRoute path={`${match.url}/nhi-unusal-content`} component={NHIUnusalContent} />
      <ErrorBoundaryRoute path={`${match.url}/patient-identity`} component={PatientIdentity} />
      <ErrorBoundaryRoute path={`${match.url}/calendar-setting`} component={CalendarSetting} />
      <ErrorBoundaryRoute path={`${match.url}/tooth`} component={Tooth} />
      <ErrorBoundaryRoute path={`${match.url}/ledger`} component={Ledger} />
      <ErrorBoundaryRoute path={`${match.url}/procedure`} component={Procedure} />
      <ErrorBoundaryRoute path={`${match.url}/procedure-type`} component={ProcedureType} />
      <ErrorBoundaryRoute path={`${match.url}/treatment`} component={Treatment} />
      <ErrorBoundaryRoute path={`${match.url}/treatment-plan`} component={TreatmentPlan} />
      <ErrorBoundaryRoute path={`${match.url}/drug`} component={Drug} />
      <ErrorBoundaryRoute path={`${match.url}/todo`} component={Todo} />
      <ErrorBoundaryRoute path={`${match.url}/prescription`} component={Prescription} />
      <ErrorBoundaryRoute path={`${match.url}/treatment-drug`} component={TreatmentDrug} />
      <ErrorBoundaryRoute path={`${match.url}/disposal`} component={Disposal} />
      <ErrorBoundaryRoute path={`${match.url}/registration-del`} component={RegistrationDel} />
      <ErrorBoundaryRoute path={`${match.url}/condition-type`} component={ConditionType} />
      <ErrorBoundaryRoute path={`${match.url}/nhi-procedure-type`} component={NHIProcedureType} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
