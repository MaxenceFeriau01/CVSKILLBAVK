package com.ensemble.entreprendre.domain.enumeration;

public enum SearchSubjectEnum {
    INTERNSHIP_SUBJECT, WORK_STUDY_SUBJECT;

    public String getValue() {
        String value;
        switch (this.toString()) {
            case "INTERNSHIP_SUBJECT":
                value = "INTERNSHIP_SUBJECT";
                break;
            case "WORK_STUDY_SUBJECT":
                value = "WORK_STUDY_SUBJECT";
                break;
            default:
                value = "UNTYPED_SUBJECT";
                break;
        }
        return value;
    }
}
