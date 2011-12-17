package ru.brandanalyst.frontend.services.youtube;

public class YouTubeGrabberException extends Exception {
    public YouTubeGrabberException(String message) {
        super("YouTube grabber error: " + message);
    }

    public YouTubeGrabberException(String message, Throwable cause) {
        super("YouTube grabber error: " + message, cause);
    }

    public YouTubeGrabberException() {
        super("YouTube grabber error");
    }
}

