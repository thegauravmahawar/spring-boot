package imdbapi.services;

import imdbapi.models.TitleSearch;
import imdbapi.repositories.TitleRepository;
import imdbapi.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static imdbapi.utils.RedisUtils.TITLE_SEARCH_KEY;

@Service
public class TitleSearchService {

    private final TitleRepository titleRepository;
    private final RedisUtils redisUtils;

    public TitleSearchService(TitleRepository titleRepository, RedisUtils redisUtils) {
        this.titleRepository = titleRepository;
        this.redisUtils = redisUtils;
    }

    @Transactional(readOnly = true)
    public List<TitleSearch> search(String query, boolean refresh) {
        boolean dataAvailableInCache = redisUtils.hasKey(TITLE_SEARCH_KEY);
        boolean requireRefresh = refresh || !dataAvailableInCache; //when refresh is true or data is not in cache; do db call.
        List<TitleSearch> data;
        if (requireRefresh) {
            redisUtils.delete(TITLE_SEARCH_KEY);
            data = titleRepository.getAllDataForSearch();
            redisUtils.put(TITLE_SEARCH_KEY, data);
            return data;
        } else {
            data = redisUtils.get(TITLE_SEARCH_KEY, List.class);
        }
        data = filterDataByQuery(data, query);
        return data;
    }

    public static List<TitleSearch> filterDataByQuery(List<TitleSearch> data, String query) {
        return data.stream()
                .filter(filterItemsByQuery(query))
                .collect(Collectors.toList());
    }

    private static Predicate<? super TitleSearch> filterItemsByQuery(String query) {
        if (StringUtils.isBlank(query)) {
            return d -> true;
        }
        return d -> d.getName().toLowerCase().contains(query);
    }
}
