package biz.gelicon.core.response.exceptions;

public class MemoryDatasetProcessException extends RuntimeException {
    public MemoryDatasetProcessException(String message, Exception ex) {
        super(message,ex);
    }
}
