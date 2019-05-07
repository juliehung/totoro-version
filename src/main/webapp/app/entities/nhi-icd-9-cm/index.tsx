import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiIcd9Cm from './nhi-icd-9-cm';
import NhiIcd9CmDetail from './nhi-icd-9-cm-detail';
import NhiIcd9CmUpdate from './nhi-icd-9-cm-update';
import NhiIcd9CmDeleteDialog from './nhi-icd-9-cm-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiIcd9CmUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiIcd9CmUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiIcd9CmDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiIcd9Cm} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiIcd9CmDeleteDialog} />
  </>
);

export default Routes;
