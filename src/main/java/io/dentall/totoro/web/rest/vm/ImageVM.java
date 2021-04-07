package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.Image;

import java.time.Instant;

public class ImageVM {
    @JsonUnwrapped
    private Image image;

    private String url;

    private Long imageRelationId;

    private Instant disposalDate;

    public Long getImageRelationId() {
        return imageRelationId;
    }

    public void setImageRelationId(Long imageRelationId) {
        this.imageRelationId = imageRelationId;
    }

    public Instant getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Instant disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
