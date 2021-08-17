package io.dentall.totoro.business.service.nhi.metric.meta;

public class MetaConfig {

    private boolean use00121CPoint = false;

    private boolean useOriginPoint = false;

    private Exclude exclude;


    public boolean isUse00121CPoint() {
        return use00121CPoint;
    }

    public MetaConfig setUse00121CPoint(boolean use00121CPoint) {
        this.use00121CPoint = use00121CPoint;
        return this;
    }

    public boolean isUseOriginPoint() {
        return useOriginPoint;
    }

    public MetaConfig setUseOriginPoint(boolean useOriginPoint) {
        this.useOriginPoint = useOriginPoint;
        return this;
    }

    public Exclude getExclude() {
        return exclude;
    }

    public MetaConfig setExclude(Exclude exclude) {
        this.exclude = exclude;
        return this;
    }
}
