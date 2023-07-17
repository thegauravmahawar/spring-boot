package imdbapi.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginatedResponse<T> {

    private int page;
    private int count;
    private int totalPages;
    private long totalItems;
    private List<T> results;

    public PaginatedResponse(int page, int count, int totalPages, long totalItems, List<T> results) {
        this.page = page;
        this.count = count;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.results = results;
    }

    public static <T> PaginatedResponse<T> slice(List<T> options, int page, int count) {
        int totalItems = options.size();
        if (totalItems == 0) {
            return new PaginatedResponse<>(0, 0, 0, 0, new ArrayList<>());
        }
        count = count > 0 ? count : totalItems;
        int totalPages = totalItems / count + (totalItems % count == 0 ? 0 : 1);
        page = page > 0 ? Math.min(page, totalPages) : 1;
        int pageStartAt = Math.min((page - 1) * count, totalItems - 1);
        int pageEndAt = Math.min(pageStartAt + count, totalItems);
        List<T> subList = options.subList(pageStartAt, pageEndAt);
        return new PaginatedResponse<>(page, count, totalPages, totalItems, subList);
    }
}
