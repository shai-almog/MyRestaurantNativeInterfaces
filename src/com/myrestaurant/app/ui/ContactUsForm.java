package com.myrestaurant.app.ui;

import com.codename1.googlemaps.MapContainer;
import com.codename1.io.Log;
import com.codename1.maps.Coord;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.myrestaurant.app.model.Restaurant;
import java.io.IOException;

public class ContactUsForm extends BaseForm {
    public ContactUsForm() {
        super("Contact Us");
    }
    
    @Override
    protected Container createContent() {
Restaurant r = Restaurant.getInstance();
Coord crd = new Coord(r.latitude.get(), r.longitude.get());
MapContainer map = new MapContainer("AIzaSyB6tFx4RS0BYngJOehhshYu6-Jn1IG0yc8"); 
map.addMarker(null, crd, r.name.get(), r.tagline.get(), null);
map.setCameraPosition(crd);

TextArea address = new TextArea(r.address.get());
address.setEditable(false);
address.setUIID("MapAddressText");

Button phone = new Button("", "ShoppingCart");
FontImage.setMaterialIcon(phone, FontImage.MATERIAL_CALL);
phone.addActionListener(e -> Display.getInstance().dial(r.phone.get()));
Button navigate = new Button("", "ShoppingCart");
FontImage.setMaterialIcon(navigate, FontImage.MATERIAL_NAVIGATION);
navigate.addActionListener(e -> Display.getInstance().openNativeNavigationApp(r.navigationAddress.get()));
        
        Container addressContainer = BorderLayout.center(address);
        addressContainer.add(BorderLayout.EAST, GridLayout.encloseIn(1, phone, navigate));
        addressContainer.setUIID("MapAddress");
        
        Container lp = LayeredLayout.encloseIn(map,
                BorderLayout.south(addressContainer));
        return lp;
    }

    @Override
    protected void onSearch(String searchString) {
    }
}
