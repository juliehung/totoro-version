import React from 'react';
import Name from './Name';
import Gender from './Gender';
import Disease from './Disease';
import Birth from './Birth';
import NationalId from './NationalId';
import BloodType from './BloodType';
import Phone from './Phone';
import Address from './Address';
import Career from './Career';
import Marriage from './Marriage';
import Introducer from './Introducer';
import EmergencyContactName from './EmergencyContactName';
import EmergencyContactPhone from './EmergencyContactPhone';
import EmergencyContactRelationship from './EmergencyContactRelationship';
import Allergy from './Allergy';
import DoDrugQ from './DoDrugQ';
import DoDrugA from './DoDrugA';
import PregnantQ from './PregnantQ';
import PregnantA from './PregnantA';
import SmokingQ from './SmokingQ';
import SmokingA from './SmokingA';
import Other from './Other';
import BloodDisease from './BloodDisease';
import * as validators from '../utils/validators';

const pages = [
  {
    key: 'Name',
    page: 1,
    component: <Name />,
    validator: validators.nameValidator,
  },
  {
    key: 'Birth',
    page: 2,
    component: <Birth />,
    validator: validators.birthValidator,
  },
  {
    key: 'Gender',
    page: 3,
    component: <Gender />,
  },
  {
    key: 'NationalId',
    page: 4,
    component: <NationalId />,
    validator: validators.nationalIdValidator,
  },
  {
    key: 'BloodType',
    page: 5,
    component: <BloodType />,
  },
  {
    key: 'Phone',
    page: 6,
    component: <Phone />,
    validator: validators.phoneValidator,
  },
  { key: 'Address', page: 7, component: <Address /> },
  {
    key: 'Career',
    page: 8,
    component: <Career />,
  },
  {
    key: 'Marriage',
    page: 9,
    component: <Marriage />,
  },
  {
    key: 'Introducer',
    page: 10,
    component: <Introducer />,
  },
  {
    key: 'EmergencyContactName',
    page: 11,
    component: <EmergencyContactName />,
  },
  {
    key: 'EmergencyContactPhone',
    page: 12,
    component: <EmergencyContactPhone />,
  },
  {
    key: 'EmergencyContactRelationship',
    page: 13,
    component: <EmergencyContactRelationship />,
  },
  {
    key: 'Disease',
    page: 14,
    component: <Disease />,
  },
  {
    key: 'BloodDisease',
    page: 15,
    component: <BloodDisease />,
  },
  {
    key: 'Allergy',
    page: 16,
    component: <Allergy />,
  },
  {
    key: 'DoDrugQ',
    page: 17,
    component: <DoDrugQ />,
    nextPage: patient => (patient?.doDrug === 'A' ? 23 : 18),
  },
  {
    key: 'SmokingQ',
    page: 18,
    component: <SmokingQ />,
    nextPage: patient => (patient?.smoking === 'A' ? 24 : 19),
  },
  {
    key: 'PregnantQ',
    page: 19,
    component: <PregnantQ />,
    nextPage: patient => (patient?.pregnant === 'A' ? 25 : 20),
  },
  {
    key: 'Other',
    page: 20,
    component: <Other />,
    isLast: true,
  },
  {
    key: 'DoDrugA',
    page: 23,
    component: <DoDrugA />,
    validator: validators.drugValidator,
    nextPage: 18,
    prevPage: 17,
  },
  {
    key: 'SmokingA',
    page: 24,
    component: <SmokingA />,
    validator: validators.smokingAmountValidator,
    nextPage: 19,
    prevPage: 18,
  },
  {
    key: 'PregnantA',
    page: 25,
    component: <PregnantA />,
    validator: validators.pregnantDateValidator,
    nextPage: 20,
    prevPage: 19,
  },
];

export default pages;
