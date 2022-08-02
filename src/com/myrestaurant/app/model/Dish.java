package com.myrestaurant.app.model;

import com.codename1.io.Log;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Image;
import com.myrestaurant.app.ui.MaskManager;
import java.io.IOException;

/**
 * Abstraction of a generic dish not of an order 
 */
public class Dish implements PropertyBusinessObject {
    public final Property<String, Dish> id = new Property<>("id");
    public final Property<Double, Dish> price = new Property<>("price");
    public final Property<String, Dish> name = new Property<>("name");
    public final Property<String, Dish> description = new Property<>("description");
    public final Property<String, Dish> category = new Property<>("category");
    public final Property<String, Dish> imageName = new Property<>("imageName");
    public final Property<Image, Dish> thumbnail = new Property<Image, Dish>("thumbnail") {
        @Override
        public Image get() {
            Image i = super.get(); 
            if(i == null) {
                try {
                    i = MaskManager.maskWithRoundRect(Image.createImage("/chocolate-cake.jpg"));
                } catch(IOException err) {
                    Log.e(err);
                }
                
                super.set(i);
            }
            return i;
        }
    };

    public final Property<Image, Dish> fullSize = new Property<Image, Dish>("fullSize") {
        @Override
        public Image get() {
            Image i = super.get(); 
            if(i == null) {
                try {
                    i = Image.createImage("/chocolate-cake.jpg");
                } catch(IOException err) {
                    Log.e(err);
                }
                
                super.set(i);
            }
            return i;
        }
    };
    private final PropertyIndex idx = new PropertyIndex(this, "Dish", 
            id, price, name, description, category, imageName, thumbnail, fullSize);

    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }

    @Override
    public boolean equals(Object obj) {
        return name.get().equals(((Dish)obj).name.get());
    }

    @Override
    public int hashCode() {
        return name.get().hashCode();
    }
}
