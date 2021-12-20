package io.dentall.totoro.business.service.report.dto;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class SubjectMonthlyNhiVo {

    private long subjectId;

    private String subjectName;

    private List<Summary> summaryList;

    public SubjectMonthlyNhiVo(long subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public SubjectMonthlyNhiVo(NhiVo vo) {
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
        SubjectMonthlyNhiVo that = (SubjectMonthlyNhiVo) o;
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

        private String doctorName;

        private long procedureId;

        private String procedureCode;

        private int procedureCount;

        private long procedurePoint;

        private int patientCount;

        public Summary(NhiVo vo) {
            this.disposalMonth = YearMonth.from(vo.getDisposalDate());
            this.doctorName = vo.getDoctorName();
            this.procedureId = vo.getProcedureId();
            this.procedureCode = vo.getProcedureCode();
        }

        public YearMonth getDisposalMonth() {
            return disposalMonth;
        }

        public void setDisposalMonth(YearMonth disposalMonth) {
            this.disposalMonth = disposalMonth;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public long getProcedureId() {
            return procedureId;
        }

        public void setProcedureId(long procedureId) {
            this.procedureId = procedureId;
        }

        public String getProcedureCode() {
            return procedureCode;
        }

        public void setProcedureCode(String procedureCode) {
            this.procedureCode = procedureCode;
        }

        public int getProcedureCount() {
            return procedureCount;
        }

        public void setProcedureCount(int procedureCount) {
            this.procedureCount = procedureCount;
        }

        public long getProcedurePoint() {
            return procedurePoint;
        }

        public void setProcedurePoint(long procedurePoint) {
            this.procedurePoint = procedurePoint;
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
            return procedureId == summary.procedureId && Objects.equals(disposalMonth, summary.disposalMonth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(disposalMonth, procedureId);
        }
    }

}
