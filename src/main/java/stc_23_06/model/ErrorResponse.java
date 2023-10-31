package stc_23_06.model;

import java.util.Objects;

public class ErrorResponse {
    int statusCode;
    String message;

    public ErrorResponse(){}
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse that)) return false;
        return statusCode == that.statusCode && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}