package trackDeliveryApp.trackDeliveryApp.exceptionsHandler;

public class PhoneNumberAlreadyExistsException extends RuntimeException {
    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Phone number " + phoneNumber + " already exists.");
    }
}
