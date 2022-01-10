package io.dentall.totoro.thumbnails;

public class ThumbnailsParam {
    private int width;
    private int height;

    public ThumbnailsParam() {
    }

    public ThumbnailsParam(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public ThumbnailsParam setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ThumbnailsParam setHeight(int height) {
        this.height = height;
        return this;
    }

    public String toAppendix() {
        return width + "x" + height;
    }

}
