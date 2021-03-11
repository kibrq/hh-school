package ru.hh.school.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.entity.Employer;
import ru.hh.school.util.EmployerViews;
import ru.hh.school.util.Pagination;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployerOuterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerOuterService.class);
    private static final String BASE_EMPLOYER_URL = "https://api.hh.ru/employers/";

    ObjectMapper mapper = new ObjectMapper();

    public static class OuterAPIException extends Exception {
        public OuterAPIException(String message) {
            super("Error while getting information from hh.ru:" + message + ".");
        }
    }

    private static String generateQuery(String url, Map<String, String> params) {
        return url +
                params.entrySet().stream()
                        .map((e) -> e.getKey() + "=" + e.getValue())
                        .collect(Collectors.joining("&", "?", ""));
    }

    private JsonNode getJsonNode(String uri) throws OuterAPIException {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            assert false;
        }
        HttpResponse<String> response;
        try {
            response = HttpClient.newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new OuterAPIException("Cannot connect to hh api");
        } catch (InterruptedException e) {
            throw new OuterAPIException("Request was interrupted");
        }
        LOGGER.info(Integer.toString(response.statusCode()));
        JsonNode root;
        try {
            root = mapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new OuterAPIException("Response was corrupted");
        }
        return root;
    }

    public List<Employer> getShortEmployers(String query, Pagination pagination) throws OuterAPIException, JsonProcessingException {
        LOGGER.info(generateQuery(
                BASE_EMPLOYER_URL,
                Map.ofEntries(
                        Map.entry("text", query),
                        Map.entry("page", Integer.toString(pagination.page)),
                        Map.entry("per_page", Integer.toString(pagination.perPage))
                )
        ));
        JsonNode node = getJsonNode(BASE_EMPLOYER_URL);
        List<Employer> employers = new ArrayList<>();
        for (JsonNode child : node.get("items")) {
            employers.add(mapper
                    .readerWithView(EmployerViews.Short.class)
                    .treeToValue(child, Employer.class)
            );
        }
        return employers;
    }

    public Employer getFullEmployer(Long id) throws OuterAPIException, JsonProcessingException {
        return mapper
                .readerWithView(EmployerViews.Detailed.class)
                .treeToValue(getJsonNode(BASE_EMPLOYER_URL + id), Employer.class);
    }
}
