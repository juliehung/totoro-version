package io.dentall.totoro.business.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.ImageRelation;

import java.io.Serializable;

public class ImageRelationPathVM implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Image image;

    public ImageRelationPathVM(ImageRelation imageRelation) {
        this.id = imageRelation.getId();
        this.image = imageRelation.getImage();
    }

    @Override
    public String toString() {
        return "ImageRelationPathVM{" +
            "id=" + id +
            ", image=" + image + "'" +
            "}";
    }
}
