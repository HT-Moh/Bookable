package com.habbat.bookable.CustomBundlers;

import android.os.Bundle;

import com.habbat.bookable.models.Item;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import icepick.Bundler;

/**
 * Created by hackolos on 21.12.17.
 */

public class ListItemBunder implements Bundler<List<Item>> {
    private int size = 0;
    public void put(String key, List<Item> value, Bundle bundle) {
        if (value!=null && value.size()>0){
            for(int i=0; i<value.size(); i++){
                Item item = value.get(i);
                bundle.putParcelable("ITEM"+i, Parcels.wrap(value));
            }
            size = value.size();
        }
    }
    public List<Item> get(String key, Bundle bundle) {
        List<Item> items = new ArrayList<>();
        if (size>0){
            for(int i=0; i<size; i++){
                items.add(Parcels.unwrap(bundle.getParcelable("ITEM")));
            }
        }
        return items;
    }
}
