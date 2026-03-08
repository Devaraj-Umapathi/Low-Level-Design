package exceptions;

public class InvalidTicketException extends RuntimeException {

    public InvalidTicketException(String ticketId) {
        super("Invalid or unknown ticket: " + ticketId);
    }

    public InvalidTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
