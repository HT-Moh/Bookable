package com.habbat.bookable.CustomBundlers;

import android.os.Bundle;

import com.habbat.bookable.models.Item;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by hackolos on 21.12.17.
 */

public class ItemBundler implements Bundler<Item> {
    public void put(String key, Item value, Bundle bundle) {
        bundle.putParcelable("ITEM", Parcels.wrap(value));
    }

    public Item get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable("ITEM"));
    }
}
