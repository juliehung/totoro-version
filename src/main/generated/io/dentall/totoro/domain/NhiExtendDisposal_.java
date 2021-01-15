package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NhiExtendDisposal.class)
public abstract class NhiExtendDisposal_ {

	public static volatile SingularAttribute<NhiExtendDisposal, String> a11;
	public static volatile SingularAttribute<NhiExtendDisposal, LocalDate> date;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a32;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a54;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a13;
	public static volatile SingularAttribute<NhiExtendDisposal, Boolean> checkedAuditing;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a12;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a15;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a14;
	public static volatile SingularAttribute<NhiExtendDisposal, Long> patientId;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a17;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a16;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a19;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a18;
	public static volatile SingularAttribute<NhiExtendDisposal, NhiExtendDisposalUploadStatus> uploadStatus;
	public static volatile SingularAttribute<NhiExtendDisposal, Boolean> checkedMonthDeclaration;
	public static volatile SetAttribute<NhiExtendDisposal, NhiDayUploadDetails> nhiDayUploadDetails;
	public static volatile SetAttribute<NhiExtendDisposal, NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures;
	public static volatile SingularAttribute<NhiExtendDisposal, Disposal> disposal;
	public static volatile SetAttribute<NhiExtendDisposal, NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs;
	public static volatile SingularAttribute<NhiExtendDisposal, String> examinationCode;
	public static volatile SingularAttribute<NhiExtendDisposal, Long> id;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a42;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a41;
	public static volatile SingularAttribute<NhiExtendDisposal, LocalDate> replenishmentDate;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a22;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a44;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a43;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a23;
	public static volatile SingularAttribute<NhiExtendDisposal, String> serialNumber;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a26;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a25;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a27;
	public static volatile SingularAttribute<NhiExtendDisposal, Integer> examinationPoint;
	public static volatile SingularAttribute<NhiExtendDisposal, String> patientIdentity;
	public static volatile SingularAttribute<NhiExtendDisposal, String> referralHospitalCode;
	public static volatile SingularAttribute<NhiExtendDisposal, String> category;
	public static volatile SingularAttribute<NhiExtendDisposal, String> a31;

}

