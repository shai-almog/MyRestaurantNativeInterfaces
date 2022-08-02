package com.myrestaurant.app.payment;

/**
 * This class receives callback from native off of the EDT!
 */
public class BraintreePaymentCallback {
    public static void onPurchaseSuccess(String nonce) {
    }

    public static void onPurchaseFail(String errorMessage) {
    }

    public static void onPurchaseCancel() {
    }
}
