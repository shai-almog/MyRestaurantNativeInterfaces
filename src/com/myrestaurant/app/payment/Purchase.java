package com.myrestaurant.app.payment;

import com.codename1.system.NativeLookup;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;

/**
 * It's not good practice to call the native interface directly, this class 
 * hides some of the low level implementation details if any.
 */
public class Purchase {
    boolean flag;
    public void startOrder() {
        BraintreeNative bn = NativeLookup.create(BraintreeNative.class);
        String token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJiYjJmZmQyN2E2OTIyODk0YTMwMTJmZTcwMTEzNWY1ZmVlMjBkY2FiYjUwMDk5MjExYWJmZTFiN2JlMzFjM2I0fGNyZWF0ZWRfYXQ9MjAxNy0wMy0yOFQyMDoyMTozMC4xNzM3NDc4MDUrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJjb2luYmFzZUVuYWJsZWQiOmZhbHNlLCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";
        if(bn != null && bn.isSupported()) {
            bn.showChargeUI(token);
        } else {
            // default to using the JavaScript gateway...
            Form buy = new Form(new BorderLayout());
            buy.getToolbar().setUIID("DarkToolbar");
            Form previous = Display.getInstance().getCurrent();
            buy.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_CANCEL, e -> previous.showBack());
            BrowserComponent cmp = new BrowserComponent();
            buy.add(BorderLayout.CENTER, cmp);
            cmp.setPage("<html><head>" +
                "    <style type=\"text/css\">\n" +
                "        html, body {\n" +
                "            height: 100%;\n" +
                "            padding: 0;\n" +
                "            margin: 0;     \n" +
                "        }\n" +
                "        \n" +
                "        /* Dummy CSS to fix bug in JavaFX webview that caused gibberish display */\n" +
                "        .gm-style-mtc > div, .gm-style > div, .gm-style-cc > div, .gm-style {font-family:sans-serif !important;}\n" +
                "    </style>" +
                "</head><body>" +
                "\n<form id=\"checkout\" method=\"post\" action=\"/checkout\">\n" +
                "  <div id=\"payment-form\"></div>\n" +
                "  <input type=\"submit\" value=\"Pay\">\n" +
                "</form>" +
                "<script src=\"https://js.braintreegateway.com/js/braintree-2.31.0.min.js\"></script>\n" +
                "<script>\n" +
                "var clientToken = \"" + token + "\";\n" +
                "braintree.setup(clientToken, \"dropin\", {\n" +
                "  container: \"payment-form\"\n" +
                "});\n" +
                "</script></body></html>", null);
            buy.show();
        }

        // this code is here to prevent the iOS VM from stripping out that class 
        // it might assume incorrectly that it's unused but it's invoked from native 
        // code... flag should always be false...
        if(flag) {
            BraintreePaymentCallback.onPurchaseCancel();
            BraintreePaymentCallback.onPurchaseFail(null);
            BraintreePaymentCallback.onPurchaseSuccess(null);
        }
    }
}
