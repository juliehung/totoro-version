package io.dentall.totoro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "nhi_tx")
public class NhiTx implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nhi_code")
    private String nhiCode;

    @Column(name = "nhi_mandarin")
    private String nhiMandarin;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "version")
    private Long version;

    public String getNhiMandarin() {
        return nhiMandarin;
    }

    public void setNhiMandarin(String nhiMandarin) {
        this.nhiMandarin = nhiMandarin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNhiCode() {
        return nhiCode;
    }

    public void setNhiCode(String nhiCode) {
        this.nhiCode = nhiCode;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
