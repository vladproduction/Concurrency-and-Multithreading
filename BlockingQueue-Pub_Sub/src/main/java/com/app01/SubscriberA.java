package com.app01;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SubscriberA implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("SubscriberA-----start");
        Object source = evt.getSource();
        String propertyName = evt.getPropertyName();
        Object oldValue = evt.getOldValue();
        Object newValue = evt.getNewValue();

        System.out.println("source = " + source);
        System.out.println("propertyName = " + propertyName);
        System.out.println("oldValue = " + oldValue);
        System.out.println("newValue = " + newValue);
        System.out.println("SubscriberA-----finish");
    }
}
