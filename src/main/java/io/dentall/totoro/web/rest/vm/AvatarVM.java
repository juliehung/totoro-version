package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.Avatar;

import java.io.Serializable;
import java.util.Base64;

public class AvatarVM implements Serializable {

    @JsonProperty
    private String contentType;

    @JsonProperty
    private String base64;

    public AvatarVM(Avatar avatarEntity) {
        this.contentType = avatarEntity.getAvatarContentType();
        this.base64 = Base64.getEncoder().encodeToString(avatarEntity.getAvatar());
    }

    @Override
    public String toString() {
        return "AvatarVM{" +
            "contentType='" + contentType + "'" +
            ", base64=" + base64 + "'" +
            "}";
    }
}
