package asryab.com.mvvmproject.models;

import java.util.HashMap;
import java.util.Map;

public final class MoreLoadingInfo {

    public static final int DEFAULT_PAGE_COUNT          = 5;
    public static final int DEFAULT_PAGE_COUNT_OTHER    = 20;

    public int count = DEFAULT_PAGE_COUNT;
    public int page = 1;

    public MoreLoadingInfo() {
    }

    public MoreLoadingInfo(int count) {
        this.count = count;
    }

    public Map<String, Integer> prepareQuery() {
        final Map<String, Integer> queries = new HashMap<>();
        queries.put("count", count);
        queries.put("page", page);
        return queries;
    }

    public void increasePage() {
        page++;
    }

    public void errorLoadMore() {
        if (page > 1)
            page--;
    }

    public void finishPage() {
        page = -1;
    }

    public void resetPage() {
        page = 1;
    }

}
