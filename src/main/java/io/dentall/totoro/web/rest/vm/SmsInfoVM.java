package io.dentall.totoro.web.rest.vm;

/**
 * A VM representing a SmsInfo.
 */
public class SmsInfoVM {

    private int total;

    private int remaining;

    private boolean activated;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "SmsInfoVM{" +
            "total=" + total +
            ", remaining=" + remaining +
            ", activated='" + activated + '\'' +
            "}";
    }
}
