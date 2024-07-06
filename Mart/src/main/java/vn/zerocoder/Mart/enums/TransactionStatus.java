package vn.zerocoder.Mart.enums;

public enum TransactionStatus {
    // Chờ xử lý
    PENDING,
    // Đang xử lý
    PROCESSING,
    // Đã hoàn thành
    COMPLETED,
    // Đã hủy
    CANCELLED,
    // Hoàn tiền
    REFUNDED
}
