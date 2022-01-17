package io.dentall.totoro.preference;

public class UserSetting {

    public enum DisplayType {
        List, Column
    }

    private PatientDashboard patientDashboard = new PatientDashboard();

    public PatientDashboard getPatientDashboard() {
        return patientDashboard;
    }

    public void setPatientDashboard(PatientDashboard patientDashboard) {
        this.patientDashboard = patientDashboard;
    }

    // 病患中心
    public static class PatientDashboard {

        private DocumentManagement documentManagement = new DocumentManagement();

        public DocumentManagement getDocumentManagement() {
            return documentManagement;
        }

        public void setDocumentManagement(DocumentManagement documentManagement) {
            this.documentManagement = documentManagement;
        }

        public static class DocumentManagement {

            private DisplayType displayType = DisplayType.List;

            public DisplayType getDisplayType() {
                return displayType;
            }

            public void setDisplayType(DisplayType displayType) {
                this.displayType = displayType;
            }

            @Override
            public String toString() {
                return "DocumentManagement{" +
                    "displayType=" + displayType +
                    '}';
            }
        }

        @Override
        public String toString() {
            return "PatientDashboard{" +
                "documentManagement=" + documentManagement +
                '}';
        }
    }

    @Override
    public String toString() {
        return "UserSetting{" +
            "patientDashboard=" + patientDashboard +
            '}';
    }
}
