package com.mycompany.app.model;

public class Request {
    private final int id;
    private final String studentName;
    private final String bookTitle;
    private final String type;     // borrow / return
    private final String status;   // requested / borrowed / denied

    public Request(int id, String studentName, String bookTitle, String type, String status) {
        this.id = id;
        this.studentName = studentName;
        this.bookTitle = bookTitle;
        this.type = type;
        this.status = status;
    }

    public int getId() { return id; }
    public String getStudentName() { return studentName; }
    public String getBookTitle() { return bookTitle; }
    public String getType() { return type; }
    public String getStatus() { return status; }

    private String getStatusEmoji() {
        String s = (status == null) ? "" : status.toLowerCase();
        return switch (s) {
            case "requested" -> "🕓 Pending";
            case "borrowed" -> "✅ Approved";
            case "denied" -> "❌ Denied";
            case "returned" -> "📦 Returned";
            default -> "ℹ️ " + status;
        };
    }

    @Override
    public String toString() {
        return String.format("%s – %s requested to %s \"%s\"",
                getStatusEmoji(), studentName, type, bookTitle);
    }
}
