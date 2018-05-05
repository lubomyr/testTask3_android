package com.testtaskapp.repository;

import com.testtaskapp.BaseApplication;
import com.testtaskapp.entities.FeedItem;
import com.testtaskapp.entities.FeedItemDao;

import java.util.List;

import static com.testtaskapp.utils.KeyNames.KEY_AUDIOBOOKS;
import static com.testtaskapp.utils.KeyNames.KEY_MOVIES;
import static com.testtaskapp.utils.KeyNames.KEY_PODCASTS;

public class FeedsRepository {
    private static FeedItemDao getDao() {
        return BaseApplication.getDaoSession().getFeedItemDao();
    }

    public static void saveAll(List<FeedItem> items) {
        getDao().insertOrReplaceInTx(items);
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
