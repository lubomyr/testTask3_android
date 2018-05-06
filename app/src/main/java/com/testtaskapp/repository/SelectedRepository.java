package com.testtaskapp.repository;

import com.testtaskapp.BaseApplication;
import com.testtaskapp.entities.SelectedItem;
import com.testtaskapp.entities.SelectedItemDao;

public class SelectedRepository {
    private static SelectedItemDao getDao() {
        return BaseApplication.getDaoSession().getSelectedItemDao();
    }

    public static void save(SelectedItem item) {
        SelectedItemDao dao = getDao();
        try {
            dao.insertOrReplace(item);
        } catch (Throwable ignored) {
        }
    }

    public static SelectedItem getById(long id) {
        SelectedItem collectionItem = getDao().queryBuilder().where(SelectedItemDao.Properties.CollectionId.eq(id)).unique();
        SelectedItem trackItem = getDao().queryBuilder().where(SelectedItemDao.Properties.TrackId.eq(id)).unique();
        return (collectionItem != null) ? collectionItem : trackItem;
    }

}
