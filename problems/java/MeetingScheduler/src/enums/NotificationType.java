package enums;

public enum NotificationType {
    INVITE("Invite", "Meeting Invitation"),
    CANCEL("Cancel", "Meeting Cancelled");

    private final String label;
    private final String subjectPrefix;

    NotificationType(String label, String subjectPrefix) {
        this.label = label;
        this.subjectPrefix = subjectPrefix;
    }

    public String getLabel() {
        return label;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }
}
