package com.testtaskapp.repository;

import com.testtaskapp.BaseApplication;
import com.testtaskapp.entities.FeedItem;
import com.testtaskapp.entities.FeedItemDao;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.testtaskapp.utils.KeyNames.KEY_AUDIOBOOKS;
import static com.testtaskapp.utils.KeyNames.KEY_MOVIES;
import static com.testtaskapp.utils.KeyNames.KEY_PODCASTS;

public class FeedsRepository {
    private static AtomicLong sId = new AtomicLong();

    private static FeedItemDao getDao() {
        return BaseApplication.getDaoSession().getFeedItemDao();
    }

    public static void saveAll(List<FeedItem> items) {
        FeedItemDao dao = getDao();

        List<FeedItem> oldItemList;
        FeedItem old;
        for (FeedItem item : items) {
            oldItemList = dao.queryBuilder()
                    .where(FeedItemDao.Properties.Id.eq(item.getId())).list();
            if (!oldItemList.isEmpty()) {
                old = oldItemList.get(0);
                item.setDbId(old.getDbId());
                dao.update(item);
                sId.incrementAndGet();
            } else {
                item.setDbId(sId.getAndIncrement());
                insert(item, dao);
            }
        }
    }

    private static void insert(FeedItem item, FeedItemDao dao) {
        try {
            dao.insert(item);
        }catch (Throwable throwable) {
            item.setDbId(sId.incrementAndGet());
            insert(item, dao);
        }
    }

    public static void deleteAll() {
        getDao().deleteAll();
    }

    public static List<FeedItem> getAll() {
        return getDao().loadAll();
    }

    public static List<FeedItem> getByCategory(String category) {
        String condition = "";
        switch(category) {
            case KEY_AUDIOBOOKS:
                condition = "book";
                break;
            case KEY_MOVIES:
                condition = "movie";
                break;
            case KEY_PODCASTS:
                condition = "podcast";
                break;
        }
        return getDao().queryBuilder().where(FeedItemDao.Properties.Kind.eq(condition)).list();
    }

}
