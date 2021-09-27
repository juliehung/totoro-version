package io.dentall.totoro.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nhi_ic_card_operation_record")
public class NhiIcCardOperationRecord extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name = "nhiIcCardOperationRecordSeq",
        sequenceName = "nhi_ic_card_operation_record_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "nhiIcCardOperationRecordSeq"
    )
    private Long id;

    @Column(name = "nhi_date_time")
    private String nhiDateTime;

    @Column(name = "disposal_id")
    private Long disposalId;

    @Column(name = "eject_ic_card_record")
    private boolean ejectIcCardRecord = false;

    @Column(name = "nhi_response_code")
    private String nhiResponseCode;

    @Column(name = "nhi_response_message")
    private String nhiResponseMessage;

    @Column(name = "days")
    private String days;

    @Column(name = "delivery_prescription_note")
    private String deliveryPrescriptionNote;

    @Column(name = "medical_order_category")
    private String medicalOrderCategory;

    @Column(name = "medical_item_code")
    private String medicalItemCode;

    @Column(name = "medical_part")
    private String medicalPart;

    @Column(name = "total")
    private String total;

    @Column(name = "usage")
    private String usage;

    public boolean isEjectIcCardRecord() {
        return ejectIcCardRecord;
    }

    public void setEjectIcCardRecord(boolean ejectIcCardRecord) {
        this.ejectIcCardRecord = ejectIcCardRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNhiDateTime() {
        return nhiDateTime;
    }

    public void setNhiDateTime(String nhiDateTime) {
        this.nhiDateTime = nhiDateTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDeliveryPrescriptionNote() {
        return deliveryPrescriptionNote;
    }

    public void setDeliveryPrescriptionNote(String deliveryPrescriptionNote) {
        this.deliveryPrescriptionNote = deliveryPrescriptionNote;
    }

    public String getMedicalOrderCategory() {
        return medicalOrderCategory;
    }

    public void setMedicalOrderCategory(String medicalOrderCategory) {
        this.medicalOrderCategory = medicalOrderCategory;
    }

    public String getMedicalItemCode() {
        return medicalItemCode;
    }

    public void setMedicalItemCode(String medicalItemCode) {
        this.medicalItemCode = medicalItemCode;
    }

    public String getMedicalPart() {
        return medicalPart;
    }

    public void setMedicalPart(String medicalPart) {
        this.medicalPart = medicalPart;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public String getNhiResponseCode() {
        return nhiResponseCode;
    }

    public void setNhiResponseCode(String nhiResponseCode) {
        this.nhiResponseCode = nhiResponseCode;
    }

    public String getNhiResponseMessage() {
        return nhiResponseMessage;
    }

    public void setNhiResponseMessage(String nhiResponseMessage) {
        this.nhiResponseMessage = nhiResponseMessage;
    }
}
