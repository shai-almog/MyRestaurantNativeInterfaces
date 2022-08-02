package com.myrestaurant.app.ui;

import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.util.Resources;
import com.myrestaurant.app.model.Dish;
import com.myrestaurant.app.model.Order;
import com.myrestaurant.app.model.Restaurant;
import java.io.IOException;
import java.util.Map;


/**
 *
 */
public class MainMenuForm extends BaseForm {
    public MainMenuForm() {
        super("Menu");
    }

    @Override
    protected List<String> createCategoryList() {
        List<String> l = new List<String>(new DefaultListModel<>(
                Restaurant.getInstance().
                    menu.get().
                    categories.asList())) {
            @Override
            protected boolean shouldRenderSelection() {
                return true;
            }
        };
        ((DefaultListCellRenderer<String>)l.getRenderer()).setAlwaysRenderSelection(true);
        l.setIgnoreFocusComponentWhenUnfocused(false);
        l.setOrientation(List.HORIZONTAL);
        l.setFixedSelection(List.FIXED_CENTER);
        return l;
    }

    
    @Override
    protected Container createContent() {
        Container c = new Container(BoxLayout.y());
        for(Dish currentDish : Restaurant.getInstance().menu.get().dishes) {
            c.add(createDishContainer(currentDish));
        }
        c.setScrollableY(true);
        c.setScrollVisible(false);
        return c;
    }

    private Container createDishContainer(Dish dish) {
        TextArea ta = new TextArea(dish.description.get(), 3, 80);
        ta.setEditable(false);
        ta.setFocusable(false);
        ta.setGrowByContent(false);
        ta.setUIID("DishListBody");
        
        Button order = new Button("Order " + Restaurant.getInstance().formatCurrency(dish.price.get()), "AddToOrderButton");
        Button moreInfo = new Button("More Info", "MoreInfoButton");
        moreInfo.addActionListener(e -> new DishViewForm(dish).show());
        order.addActionListener(e -> {
            Order o = Restaurant.getInstance().cart.get();
            if(o.dishesQuantity.get(dish) != null) {
                o.dishesQuantity.put(dish, o.dishesQuantity.get(dish) + 1);
            } else {
                o.dishesQuantity.put(dish, 1);                
            }
        });
        
        Container dishContainer = BorderLayout.center(
                BoxLayout.encloseY(
                        new Label(dish.name.get(), "DishListTitle"),
                        ta
                        )
        );
        dishContainer.add(BorderLayout.EAST, new Label(dish.thumbnail.get()));
        dishContainer.add(BorderLayout.SOUTH, GridLayout.encloseIn(2, order, moreInfo));
        dishContainer.setUIID("DishListEntry");
        return dishContainer;
    }
    
    @Override
    protected void onSearch(String searchString) {
    }
    
}
