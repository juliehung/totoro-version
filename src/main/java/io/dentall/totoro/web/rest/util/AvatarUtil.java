package io.dentall.totoro.web.rest.util;

import io.dentall.totoro.domain.Avatar;
import io.dentall.totoro.web.rest.vm.AvatarVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AvatarUtil {

    public static ResponseEntity<AvatarVM> responseAvatarVM(Avatar avatarEntity) {
        if (avatarEntity.getAvatar() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AvatarVM(avatarEntity), HttpStatus.OK);
        }
    }
}
