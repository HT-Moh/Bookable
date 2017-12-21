package com.habbat.bookable.CustomBundlers;

import android.os.Bundle;

import com.habbat.bookable.models.AccessInfo;
import com.habbat.bookable.models.Epub;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by hackolos on 21.12.17.
 */

public class EpubBunder implements Bundler<Epub> {
    public void put(String key, Epub value, Bundle bundle) {
        bundle.putParcelable("Epub", Parcels.wrap(value));
    }
    public Epub get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable("AccessInfo"));
    }
}
