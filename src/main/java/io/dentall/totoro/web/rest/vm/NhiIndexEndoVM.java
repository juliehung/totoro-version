package io.dentall.totoro.web.rest.vm;

import java.math.BigDecimal;

public class NhiIndexEndoVM {

    private Long did;

    private Long preOperationNumber;

    private Long postOperationNumber;

    private BigDecimal completedRate;

    public NhiIndexEndoVM did(Long did) {
        this.did = did;
        return this;
    }

    public NhiIndexEndoVM completedRate(BigDecimal completedRate) {
        this.completedRate = completedRate;
        return this;
    }

    public NhiIndexEndoVM preOperationNumber(Long preOperationNumber) {
        this.preOperationNumber = preOperationNumber;
        return this;
    }

    public NhiIndexEndoVM postOperationNumber(Long postOperationNumber) {
        this.postOperationNumber = postOperationNumber;
        return this;
    }

    public BigDecimal getCompletedRate() {
        return completedRate;
    }

    public void setCompletedRate(BigDecimal completedRate) {
        this.completedRate = completedRate;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public Long getPreOperationNumber() {
        return preOperationNumber;
    }

    public void setPreOperationNumber(Long preOperationNumber) {
        this.preOperationNumber = preOperationNumber;
    }

    public Long getPostOperationNumber() {
        return postOperationNumber;
    }

    public void setPostOperationNumber(Long postOperationNumber) {
        this.postOperationNumber = postOperationNumber;
    }
}
