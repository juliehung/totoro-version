package io.dentall.totoro.business.service.report.dto;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class SubjectMonthlyOwnExpenseVo {

    private long subjectId;

    private String subjectName;

    private List<Summary> summaryList;

    public SubjectMonthlyOwnExpenseVo(long subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public SubjectMonthlyOwnExpenseVo(OwnExpenseVo vo) {
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
        SubjectMonthlyOwnExpenseVo that = (SubjectMonthlyOwnExpenseVo) o;
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

        private long procedureId;

        private String procedureName;

        private String procedureMinorType;

        private int procedureCount;

        private int patientCount;

        private int toothCount;

        public Summary(OwnExpenseVo vo) {
            this.disposalMonth = YearMonth.from(vo.getDisposalDate());
            this.procedureId = vo.getProcedureId();
            this.procedureName = vo.getProcedureName();
            this.procedureMinorType = vo.getProcedureMinorType();
        }

        public YearMonth getDisposalMonth() {
            return disposalMonth;
        }

        public void setDisposalMonth(YearMonth disposalMonth) {
            this.disposalMonth = disposalMonth;
        }

        public long getProcedureId() {
            return procedureId;
        }

        public void setProcedureId(long procedureId) {
            this.procedureId = procedureId;
        }

        public String getProcedureName() {
            return procedureName;
        }

        public void setProcedureName(String procedureName) {
            this.procedureName = procedureName;
        }

        public String getProcedureMinorType() {
            return procedureMinorType;
        }

        public void setProcedureMinorType(String procedureMinorType) {
            this.procedureMinorType = procedureMinorType;
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

        public int getToothCount() {
            return toothCount;
        }

        public void setToothCount(int toothCount) {
            this.toothCount = toothCount;
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
