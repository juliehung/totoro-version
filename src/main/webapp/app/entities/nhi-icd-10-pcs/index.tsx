import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiIcd10Pcs from './nhi-icd-10-pcs';
import NhiIcd10PcsDetail from './nhi-icd-10-pcs-detail';
import NhiIcd10PcsUpdate from './nhi-icd-10-pcs-update';
import NhiIcd10PcsDeleteDialog from './nhi-icd-10-pcs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiIcd10PcsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiIcd10PcsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiIcd10PcsDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiIcd10Pcs} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiIcd10PcsDeleteDialog} />
  </>
);

export default Routes;
