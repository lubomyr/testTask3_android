package com.testtaskapp;

import android.app.Application;

import com.testtaskapp.entities.DaoMaster;
import com.testtaskapp.entities.DaoSession;

import org.greenrobot.greendao.database.Database;

public class BaseApplication extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "app-db");
        Database db = helper.getWritableDb();
        if (daoSession == null)
            daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
