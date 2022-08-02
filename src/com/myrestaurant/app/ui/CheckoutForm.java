package com.myrestaurant.app.ui;

import com.codename1.io.Log;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.animations.Transition;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.myrestaurant.app.model.Dish;
import com.myrestaurant.app.model.Restaurant;
import com.myrestaurant.app.payment.Purchase;
import java.io.IOException;
import java.util.Map;

public class CheckoutForm extends Form {
    final static String[] PICKER_STRINGS = new String[10];
    
    private Label totalPrice;
    
    static {
        PICKER_STRINGS[0] = "Delete";
        for(int iter = 1 ; iter < 10 ; iter++) {
            PICKER_STRINGS[iter] = iter + "x";
        }
    }
    private Transition oldTransition;
    private CheckoutForm(Form previous) {
        super(new BorderLayout());
        Button x = new Button("", "Title");
        FontImage.setMaterialIcon(x, FontImage.MATERIAL_CLOSE);
        x.addActionListener(e -> {
            previous.showBack();
            previous.setTransitionOutAnimator(oldTransition);
        });
        
        add(BorderLayout.NORTH, 
                BorderLayout.centerAbsoluteEastWest(new Label("Checkout", "Title"), null, x)
                );
        Button buy = new Button("Order & Pay", "CheckoutButton");
        add(BorderLayout.SOUTH, buy);
        buy.addActionListener(e -> {
            new Purchase().startOrder();
        });

        Container itemsContainer = new Container(BoxLayout.y());
        Container totalsContainer = new Container(BoxLayout.y());
        
        double totalPriceValue = 0;
        for(Map.Entry<Dish, Integer> currentDish : Restaurant.getInstance().cart.get().dishesQuantity) {
            itemsContainer.add(createShoppingCartContainer(currentDish.getKey(), 
                    currentDish.getValue()));
            totalPriceValue += (currentDish.getKey().price.get() * currentDish.getValue());
        }
        itemsContainer.setUIID("PaymentDialogTop");
        totalsContainer.setUIID("PaymentDialogBottom");
        totalPrice = new Label("Total: " + Restaurant.getInstance().formatCurrency(totalPriceValue), "PriceTotal");
        totalsContainer.add(totalPrice);
        Container checkout = BoxLayout.encloseY(itemsContainer, totalsContainer);
        checkout.setScrollableY(true);
        add(BorderLayout.CENTER, checkout);
    }
    
    private void updatePrice() {
        double totalPriceValue = 0;
        for(Map.Entry<Dish, Integer> currentDish : Restaurant.getInstance().cart.get().dishesQuantity) {
            totalPriceValue += (currentDish.getKey().price.get() * currentDish.getValue());
        }
        totalPrice.setText("Total: " + Restaurant.getInstance().formatCurrency(totalPriceValue));
        totalPrice.getParent().revalidate();
    }
    
    private Container createShoppingCartContainer(Dish di, int quantity) {
        Picker quantityButton = new Picker();
        quantityButton.setUIID("QuantityPicker");
        quantityButton.setType(Display.PICKER_TYPE_STRINGS);
        quantityButton.setStrings(PICKER_STRINGS);
        quantityButton.setSelectedString(quantity + "x");

        Container dishContainer = BoxLayout.encloseX(
                FlowLayout.encloseMiddle(quantityButton),
                new Label(di.thumbnail.get(), "UnmarginedLabel"),
                FlowLayout.encloseMiddle(
                        BoxLayout.encloseY(
                            new Label(di.name.get(), "DishCheckoutTitle"),
                            new Label(Restaurant.getInstance().formatCurrency(di.price.get()), "CheckoutPrice")
                        )
                )
        );

        quantityButton.addActionListener(e -> {
            if(quantityButton.getSelectedString().equals(PICKER_STRINGS[0])) {
                Display.getInstance().callSerially(() -> {
                    dishContainer.setX(Display.getInstance().getDisplayWidth());
                    Container p = dishContainer.getParent();
                    p.animateUnlayoutAndWait(250, 255);
                    dishContainer.remove();
                    p.animateLayoutAndWait(200);
                    updatePrice();
                });
            } else {
                
                updatePrice();
            }
        });
        
        return dishContainer;
    }
    
    public static void showCheckOut() {
        Form existingForm = Display.getInstance().getCurrent();
        CheckoutForm f = new CheckoutForm(existingForm);
        f.oldTransition = existingForm.getTransitionOutAnimator();;
        Image background = Image.createImage(existingForm.getWidth(), existingForm.getHeight());
        Graphics g = background.getGraphics();
        existingForm.paintComponent(g, true);
        g.setAlpha(150);
        g.setColor(0);
        g.fillRect(0, 0, background.getWidth(), background.getHeight());
        background = Display.getInstance().gaussianBlurImage(background, 10);
        f.getUnselectedStyle().setBgImage(background);
        f.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        f.setTransitionOutAnimator(CommonTransitions.createUncover(CommonTransitions.SLIDE_VERTICAL, true, 200));

        existingForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
        existingForm.setTransitionOutAnimator(CommonTransitions.createCover(CommonTransitions.SLIDE_VERTICAL, false, 200));
        f.show();
    }
    
}
