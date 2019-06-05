package global.eska.ddk.api.client.exceptions;

public class DDKApplicationException extends Throwable {

    public DDKApplicationException(String message) {
        super(message);
    }

    public DDKApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DDKApplicationException(Throwable cause) {
        super(cause);
    }
}
