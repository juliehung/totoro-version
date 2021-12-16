package io.dentall.totoro.business.service.report.dto;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class SubjectMonthlyDrugVo {

    private long subjectId;

    private String subjectName;

    private List<Summary> summaryList;

    public SubjectMonthlyDrugVo(long subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public SubjectMonthlyDrugVo(DrugVo vo) {
        this.subjectId = vo.getDoctorId();
        this.subjectName = vo.getDoctorName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubjectMonthlyDrugVo that = (SubjectMonthlyDrugVo) o;
        return subjectId == that.subjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Summary> getSummaryList() {
        return summaryList;
    }

    public void setSummaryList(List<Summary> summaryList) {
        this.summaryList = summaryList;
    }

    public static class Summary {

        private YearMonth disposalMonth;

        private long drugId;

        private String drugName;

        private String drugNhiCode;

        private int procedureCount;

        private int patientCount;

        public Summary(DrugVo vo) {
            this.disposalMonth = YearMonth.from(vo.getDisposalDate());
            this.drugId = vo.getDrugId();
            this.drugName = vo.getDrugName();
            this.drugNhiCode = vo.getDrugNhiCode();
        }

        public YearMonth getDisposalMonth() {
            return disposalMonth;
        }

        public void setDisposalMonth(YearMonth disposalMonth) {
            this.disposalMonth = disposalMonth;
        }

        public long getDrugId() {
            return drugId;
        }

        public void setDrugId(long drugId) {
            this.drugId = drugId;
        }

        public String getDrugName() {
            return drugName;
        }

        public void setDrugName(String drugName) {
            this.drugName = drugName;
        }

        public String getDrugNhiCode() {
            return drugNhiCode;
        }

        public void setDrugNhiCode(String drugNhiCode) {
            this.drugNhiCode = drugNhiCode;
        }

        public int getProcedureCount() {
            return procedureCount;
        }

        public void setProcedureCount(int procedureCount) {
            this.procedureCount = procedureCount;
        }

        public int getPatientCount() {
            return patientCount;
        }

        public void setPatientCount(int patientCount) {
            this.patientCount = patientCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Summary summary = (Summary) o;
            return drugId == summary.drugId && Objects.equals(disposalMonth, summary.disposalMonth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(disposalMonth, drugId);
        }
    }

}
