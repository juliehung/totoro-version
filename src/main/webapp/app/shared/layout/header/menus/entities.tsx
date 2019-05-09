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
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tag">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.tag" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/questionnaire">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.questionnaire" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/hospital">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.hospital" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/accounting">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.accounting" />
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
    <DropdownItem tag={Link} to="/entity/procedure-type">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.procedureType" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tooth">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.tooth" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatment" />
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
    <DropdownItem tag={Link} to="/entity/treatment-plan">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentPlan" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-task">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentTask" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/drug">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.drug" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/todo">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.todo" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/prescription">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.prescription" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/treatment-drug">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.treatmentDrug" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/disposal">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.disposal" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration-del">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.registrationDel" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/condition-type">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.conditionType" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-icd-9-cm">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiIcd9Cm" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-icd-10-cm">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiIcd10Cm" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-procedure">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiProcedure" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-procedure-type">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiProcedureType" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nhi-icd-10-pcs">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nhiIcd10Pcs" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
