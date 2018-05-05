package com.testtaskapp.repository;

import com.testtaskapp.BaseApplication;
import com.testtaskapp.entities.SelectedItem;
import com.testtaskapp.entities.SelectedItemDao;

public class SelectedRepository {
    private static SelectedItemDao getDao() {
        return BaseApplication.getDaoSession().getSelectedItemDao();
    }

    public static void save(SelectedItem item) {
        try {
            getDao().insertOrReplace(item);
        } catch (Exception ignored) {
        }
    }

    public static SelectedItem getById(long id) {
        return getDao().queryBuilder().where(SelectedItemDao.Properties.CollectionId.eq(id)).unique();
    }

}
