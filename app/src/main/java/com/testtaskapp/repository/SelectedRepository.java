package com.testtaskapp.repository;

import com.testtaskapp.BaseApplication;
import com.testtaskapp.entities.SelectedItem;
import com.testtaskapp.entities.SelectedItemDao;

import java.util.concurrent.atomic.AtomicLong;

public class SelectedRepository {
    private static AtomicLong sId = new AtomicLong();

    private static SelectedItemDao getDao() {
        return BaseApplication.getDaoSession().getSelectedItemDao();
    }

    public static void save(SelectedItem item) {
        SelectedItemDao dao = getDao();
        try {
            dao.insert(item);
        } catch (Throwable throwable) {
            item.setId(sId.incrementAndGet());
            save(item);
        }
    }

    public static SelectedItem getById(long id) {
        SelectedItem collectionItem = getDao().queryBuilder().where(SelectedItemDao.Properties.CollectionId.eq(id)).unique();
        SelectedItem trackItem = getDao().queryBuilder().where(SelectedItemDao.Properties.TrackId.eq(id)).unique();
        return (collectionItem != null) ? collectionItem : trackItem;
    }

}
