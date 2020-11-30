package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.Image;

public class ImageVM {
    @JsonUnwrapped
    private Image image;

    private String url;

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
