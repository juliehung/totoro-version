import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/patient">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tag">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.tag" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/questionnaire">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.questionnaire" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-task">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentTask" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-procedure">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiProcedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-category">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiCategory" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-procedure">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentProcedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/hospital">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.hospital" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/accounting">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.accounting" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/accounting">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.accounting" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/accounting">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.accounting" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.calendar" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.calendar" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-unusal-incident">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiUnusalIncident" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-unusal-content">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiUnusalContent" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/patient-identity">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.patientIdentity" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/patient">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.calendar" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar-setting">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.calendarSetting" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar-setting">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.calendarSetting" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tooth">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.tooth" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ledger">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.ledger" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/procedure">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.procedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-procedure">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentProcedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/procedure-type">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.procedureType" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-task">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentTask" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-procedure">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentProcedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tooth">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.tooth" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-task">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentTask" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-procedure">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentProcedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-procedure">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentProcedure" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
