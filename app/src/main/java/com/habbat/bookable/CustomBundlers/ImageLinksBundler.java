package com.habbat.bookable.CustomBundlers;

import android.os.Bundle;

import com.habbat.bookable.models.AccessInfo;
import com.habbat.bookable.models.ImageLinks;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by HT-Moh on 21.12.17.
 */

public class ImageLinksBundler implements Bundler<ImageLinks>
{
    public void put(String key, ImageLinks value, Bundle bundle) {
        bundle.putParcelable("AccessInfo", Parcels.wrap(value));
    }
    public ImageLinks get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable("AccessInfo"));
    }
}
