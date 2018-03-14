package jdk.suppressed.exception;


public class AutoClose implements AutoCloseable{
    public AutoClose() {
    }

    public void close() {
        throw new RuntimeException("SUPPRESSED");
    }
}
