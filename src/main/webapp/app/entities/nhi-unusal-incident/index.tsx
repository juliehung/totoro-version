import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NHIUnusalIncident from './nhi-unusal-incident';
import NHIUnusalIncidentDetail from './nhi-unusal-incident-detail';
import NHIUnusalIncidentUpdate from './nhi-unusal-incident-update';
import NHIUnusalIncidentDeleteDialog from './nhi-unusal-incident-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NHIUnusalIncidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NHIUnusalIncidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NHIUnusalIncidentDetail} />
      <ErrorBoundaryRoute path={match.url} component={NHIUnusalIncident} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NHIUnusalIncidentDeleteDialog} />
  </>
);

export default Routes;
