import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiIcd10Cm from './nhi-icd-10-cm';
import NhiIcd10CmDetail from './nhi-icd-10-cm-detail';
import NhiIcd10CmUpdate from './nhi-icd-10-cm-update';
import NhiIcd10CmDeleteDialog from './nhi-icd-10-cm-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiIcd10CmUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiIcd10CmUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiIcd10CmDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiIcd10Cm} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiIcd10CmDeleteDialog} />
  </>
);

export default Routes;
