package com.habbat.bookable.CustomBundlers;

import android.os.Bundle;

import com.habbat.bookable.models.AccessInfo;
import com.habbat.bookable.models.Item;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by HT-Moh on 21.12.17.
 */

public class AccessInfoBunder implements Bundler<AccessInfo>
{
        public void put(String key, AccessInfo value, Bundle bundle) {


            bundle.putParcelable("AccessInfo", Parcels.wrap(value));
        }

        public AccessInfo get(String key, Bundle bundle) {
            return Parcels.unwrap(bundle.getParcelable("AccessInfo"));
        }
}
