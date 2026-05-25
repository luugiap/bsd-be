package com.example.bds.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    STATUS_ERROR(9999, "Status Error"),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Username đã tồn tại"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email đã tồn tại"),
    USERNAME_INVALID(1003, "Username is invalid"),
    PASSWORD_INVALID(1004, "Password is invalid"),
    KEY_INVALID(1001, "Key is invalid"),
    USERNAME_NOT_EXISTS(1005, "Username not exists"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "Bạn không có quyền thực hiện hành động này"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Thông tin đăng nhập không hợp lệ"),
    ACCOUNT_NOT_VERIFIED(HttpStatus.FORBIDDEN, "Tài khoản chưa được kích hoạt, vui lòng kiểm tra email"),
    ACCOUNT_TEMPORARILY_LOCKED(HttpStatus.FORBIDDEN, "Tài khoản bị khóa tạm thời, thử lại sau 15 phút"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token không hợp lệ hoặc đã hết hạn"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Mã OTP không đúng, vui lòng thử lại"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Tài nguyên không tồn tại"),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "Vai trò không hợp lệ"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Yêu cầu không hợp lệ"),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "Bạn không có quyền truy cập tài nguyên này"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Người dùng đã tồn tại"),
    USER_NOT_ACTIVATED(HttpStatus.FORBIDDEN, "Tài khoản chưa được kích hoạt"),
    USER_DEACTIVATED(HttpStatus.FORBIDDEN, "Tài khoản đã bị vô hiệu hóa"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm"),
    PRODUCT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "Sản phẩm không khả dụng"),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "Sản phẩm đã hết hàng"),
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "Sản phẩm đã tồn tại"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng"),
    CANNOT_CREATE_ORDER(HttpStatus.BAD_REQUEST, "Không thể tạo đơn hàng"),
    CANNOT_UPDATE_ORDER(HttpStatus.BAD_REQUEST, "Không thể cập nhật đơn hàng"),
    CANNOT_CANCEL_ORDER(HttpStatus.BAD_REQUEST, "Không thể hủy đơn hàng"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy đánh giá"),
    ALREADY_REVIEWED(HttpStatus.CONFLICT, "Đã đánh giá sản phẩm này"),
    CANNOT_REVIEW(HttpStatus.BAD_REQUEST, "Không thể đánh giá sản phẩm"),
    TRANSPORTER_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy người vận chuyển"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Lỗi xác thực dữ liệu"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi máy chủ nội bộ");

    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, String message) {
        this.status = null;
        this.message = message;
    }

    public int getCode() {
        return 0; // Assuming the code is not available in the new enum structure
    }

    public String getMessage() {
        return message;
    }
}
