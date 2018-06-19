package com.example.android.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jordanhaynes on 6/5/18.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
