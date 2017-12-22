package com.habbat.bookable.CustomBundlers;

import android.os.Bundle;

import com.habbat.bookable.models.ImageLinks;
import com.habbat.bookable.models.IndustryIdentifier;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by HT-Moh on 21.12.17.
 */

public class IndustryIdentifierBundler implements Bundler<IndustryIdentifier> {

    public void put(String key, IndustryIdentifier value, Bundle bundle) {
        bundle.putParcelable("AccessInfo", Parcels.wrap(value));
    }
    public IndustryIdentifier get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable("AccessInfo"));
    }
}
