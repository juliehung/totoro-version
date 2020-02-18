package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.ImageRelation;

import java.io.Serializable;

public class ImageRelationPathVM implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String path;

    public ImageRelationPathVM(ImageRelation imageRelation) {
        this.id = imageRelation.getId();
        this.path = imageRelation.getImage().getFilePath() + imageRelation.getImage().getFileName();
    }

    @Override
    public String toString() {
        return "ImageRelationPathVM{" +
            "id=" + id +
            ", path=" + path + "'" +
            "}";
    }
}
