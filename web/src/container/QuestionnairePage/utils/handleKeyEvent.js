import { KeyCode } from '../constant_keyCode';

export function handleKeyEvent(page, keyEvent, func) {
  switch (page) {
    case 1:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      }
      break;
    case 2:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 3:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      } else if (keyEvent.keyCode === KeyCode.a || keyEvent.keyCode === KeyCode.b || keyEvent.keyCode === KeyCode.c) {
        func.preChangeGender(keyEvent.key.toUpperCase());
      }
      break;
    case 4:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 5:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      } else if (
        keyEvent.keyCode === KeyCode.a ||
        keyEvent.keyCode === KeyCode.b ||
        keyEvent.keyCode === KeyCode.c ||
        keyEvent.keyCode === KeyCode.d ||
        keyEvent.keyCode === KeyCode.e
      ) {
        func.preChangeBloodType(keyEvent.key.toUpperCase());
      }
      break;
    case 6:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 7:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 8:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
        case KeyCode.c:
        case KeyCode.d:
        case KeyCode.e:
        case KeyCode.f:
        case KeyCode.g:
        case KeyCode.h:
        case KeyCode.i:
        case KeyCode.j:
        case KeyCode.k:
        case KeyCode.l:
        case KeyCode.m:
          func.preChangeCareer(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    case 9:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
        case KeyCode.c:
        case KeyCode.d:
          func.preChangeMarriage(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    case 10:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 11:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 12:
      if (keyEvent.keyCode === KeyCode.down_arrow) {
        func.nextPage();
      } else if (keyEvent.keyCode === KeyCode.up_arrow) {
        func.prevPage();
      }
      break;
    case 13:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
        case KeyCode.c:
        case KeyCode.d:
          func.preChangeEmergencyRelationship(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    case 14:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
        case KeyCode.c:
        case KeyCode.d:
        case KeyCode.e:
        case KeyCode.f:
        case KeyCode.g:
        case KeyCode.h:
        case KeyCode.i:
        case KeyCode.j:
        case KeyCode.k:
        case KeyCode.l:
        case KeyCode.m:
        case KeyCode.n:
        case KeyCode.o:
        case KeyCode.p:
          func.changeDisease(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    case 15:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
        case KeyCode.c:
        case KeyCode.d:
        case KeyCode.e:
        case KeyCode.f:
        case KeyCode.g:
          func.changeAllergy(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    case 16:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
          func.preChangeDoDrug(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    case 17:
      switch (keyEvent.keyCode) {
        case KeyCode.down_arrow:
          func.nextPage();
          break;
        case KeyCode.up_arrow:
          func.prevPage();
          break;
        case KeyCode.a:
        case KeyCode.b:
          func.preChangePregnant(keyEvent.key.toUpperCase());
          break;
        default:
          break;
      }
      break;
    default:
      break;
  }
}
