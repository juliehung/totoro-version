import { KeyCode } from '../constant_keyCode';
import pages from '../pages';

export function handleKeyEvent(currentPage, keyEvent, func, data) {
  const currentPageObj = pages.find(p => p.page === currentPage);
  const nextPage = currentPageObj?.nextPage;
  const prevPage = currentPageObj?.prevPage;
  const validator = currentPageObj?.validator;
  const isLast = currentPageObj?.isLast;

  if (keyEvent.keyCode === KeyCode.down_arrow) {
    if (isLast) {
      func.changeFinishModalVisible(true);
      return;
    } else if (validator) {
      const validation = validator(data.patient, data.isPatientExist, data.existedPatientId);
      if (!validation) {
        func.valitationFail(currentPage);
        return;
      }
      func.validateSuccess(currentPage);
    }

    nextPage
      ? typeof nextPage === 'function'
        ? func.gotoPage(nextPage(data.patient))
        : func.gotoPage(nextPage)
      : func.goNextPage();
    return;
  } else if (keyEvent.keyCode === KeyCode.up_arrow) {
    prevPage
      ? typeof prevPage === 'function'
        ? func.gotoPage(prevPage(data.patient))
        : func.gotoPage(prevPage)
      : func.goPrevPage();
    return;
  }

  switch (currentPage) {
    case 3:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.c) {
        func.preChangeGender(keyEvent.key.toUpperCase());
      }
      break;
    case 5:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.e) {
        func.preChangeBloodType(keyEvent.key.toUpperCase());
      }
      break;
    case 8:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.m) {
        func.preChangeCareer(keyEvent.key.toUpperCase());
      }
      break;
    case 9:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.d) {
        func.preChangeMarriage(keyEvent.key.toUpperCase());
      }
      break;
    case 13:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.d) {
        func.preChangeEmergencyRelationship(keyEvent.key.toUpperCase());
      }
      break;
    case 14:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.p) {
        func.changeDisease(keyEvent.key.toUpperCase());
      }
      break;
    case 16:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.g) {
        func.changeAllergy(keyEvent.key.toUpperCase());
      }
      break;
    case 17:
      if (keyEvent.keyCode >= KeyCode.a && keyEvent.keyCode <= KeyCode.b) {
        func.preChangeDoDrug(keyEvent.key.toUpperCase());
      }
      break;
    case 18:
      if ([KeyCode.a, KeyCode.b].includes(keyEvent.keyCode)) {
        func.preChangeSmoking(keyEvent.key.toUpperCase());
      }
      break;
    case 19:
      if ([KeyCode.a, KeyCode.b].includes(keyEvent.keyCode)) {
        func.preChangePregnant(keyEvent.key.toUpperCase());
      }
      break;
    case 20:
      if ([KeyCode.a, KeyCode.f].includes(keyEvent.keyCode)) {
        func.changeOther(keyEvent.key.toUpperCase());
      }
      break;
    default:
      break;
  }
}
