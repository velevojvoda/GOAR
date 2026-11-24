package com.example.goar.data.model;

import java.time.LocalDateTime;

public class Report {
    private String id;
    private ReportTargetType targetType;
    private String targetId;
    private String reason;
    private User reporter;
    private LocalDateTime createdAt;
    private ReportStatus status;
    private String rejectionReason;

    public Report(String id, ReportTargetType targetType, String targetId, String reason,
                  User reporter, LocalDateTime createdAt) {
        this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reason = reason;
        this.reporter = reporter;
        this.createdAt = createdAt;
        this.status = ReportStatus.NEW;
    }

    public boolean canBeWithdrawn() { return status == ReportStatus.NEW; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ReportTargetType getTargetType() { return targetType; }
    public void setTargetType(ReportTargetType targetType) { this.targetType = targetType; }

    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public ReportStatus getStatus() { return status; }
    public void setStatus(ReportStatus status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}
