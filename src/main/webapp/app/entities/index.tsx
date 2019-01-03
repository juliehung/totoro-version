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
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
