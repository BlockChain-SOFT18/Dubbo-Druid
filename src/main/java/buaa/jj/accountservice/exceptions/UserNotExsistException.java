package buaa.jj.accountservice.exceptions;

public class UserNotExsistException extends AccountServiceException {
    public UserNotExsistException() {
        super();
    }

    public UserNotExsistException(String message) {
        super(message);
    }
}
