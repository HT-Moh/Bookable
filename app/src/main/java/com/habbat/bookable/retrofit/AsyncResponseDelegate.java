package com.habbat.bookable.retrofit;

import com.habbat.bookable.models.Volumes;

/**
 * Created by hackolos on 18.12.17.
 */

public interface AsyncResponseDelegate {
    void handleResponse(Volumes response);
    void handleError(Throwable error);
}
