package jdk.suppressed.exception;

public class Main {
    public static void main(String... args) {
        try (AutoClose ignored = new AutoClose()) {
            throw new RuntimeException("OVERRIDE");
        }
    }
}
