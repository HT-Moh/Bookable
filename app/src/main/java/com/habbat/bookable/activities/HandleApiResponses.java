package com.habbat.bookable.activities;

/**
 * Bookable: Google book API
 * Created by Mohamed Habbat on 01.02.18.
 * www.habbat.ch Habbat C&D
 */

public interface HandleApiResponses {
    void handleResponse(Object response);
    void handleError(Throwable error);
}
