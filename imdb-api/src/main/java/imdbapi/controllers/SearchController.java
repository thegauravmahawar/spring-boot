package imdbapi.controllers;

import imdbapi.models.ApiResponseSuccess;
import imdbapi.models.PaginatedResponse;
import imdbapi.models.TitleSearch;
import imdbapi.services.TitleSearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {

    private final TitleSearchService titleSearchService;

    public SearchController(TitleSearchService titleSearchService) {
        this.titleSearchService = titleSearchService;
    }

    @GetMapping(value = "/title-search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseSuccess searchTitle(@RequestParam(name = "q", required = false, defaultValue = "") String query,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "false") boolean refresh,
                                          HttpServletRequest request) {
        List<TitleSearch> data = titleSearchService.search(query, refresh);
        PaginatedResponse response = PaginatedResponse.slice(data, page, 10);
        return new ApiResponseSuccess(response);
    }
}
