package com.myrestaurant.app.model;

import com.codename1.properties.ListProperty;
import com.codename1.properties.MapProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;
import java.util.Date;

/**
 * An abstraction containing the dishes ordered
 */
public class Order implements PropertyBusinessObject {
    public final Property<String, Order> id = new Property<>("id");
    public final Property<Date, Order> date = new Property<>("date");
    public final Property<Boolean, Order> purchased = new Property<>("purchased");
    public final MapProperty<Dish, Integer, Order> dishesQuantity = new MapProperty<>("dishesQuantity");
    public final Property<String, Order> notes = new Property<>("notes");

    private final PropertyIndex idx = new PropertyIndex(this, "Order", 
            id, date, purchased, dishesQuantity, notes);

    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }
}
