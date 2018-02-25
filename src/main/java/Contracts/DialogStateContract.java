package Contracts;

public interface DialogStateContract {
    String ELICIT_INTENT = "ElicitIntent";
    String CONFIRM_INTENT = "ConfirmIntent";
    String ELICIT_SLOT = "ElicitSlot";
    String FULFILLED = "Fulfilled";
    String READY_FOR_FULFILLMENT = "ReadyForFulfillment";
    String FAILED = "Failed";
}
