import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TreatmentDrug from './treatment-drug';
import TreatmentDrugDetail from './treatment-drug-detail';
import TreatmentDrugUpdate from './treatment-drug-update';
import TreatmentDrugDeleteDialog from './treatment-drug-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TreatmentDrugUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TreatmentDrugUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TreatmentDrugDetail} />
      <ErrorBoundaryRoute path={match.url} component={TreatmentDrug} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TreatmentDrugDeleteDialog} />
  </>
);

export default Routes;
