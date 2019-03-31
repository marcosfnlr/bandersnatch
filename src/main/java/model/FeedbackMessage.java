package model;

public class FeedbackMessage {
    private String message;
    private TypeFeedback type;

    public FeedbackMessage(String message, TypeFeedback type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getAlertClasses() {
        return "alert alert-" + type.getStyleClass() + " alert-dismissible fade show";
    }
}
