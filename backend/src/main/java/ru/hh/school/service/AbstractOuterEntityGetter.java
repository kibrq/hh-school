package ru.hh.school.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public abstract class AbstractOuterEntityGetter {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractOuterEntityGetter.class);

    ObjectMapper mapper = new ObjectMapper();

    public static class OuterAPIException extends Exception {
        public OuterAPIException(String message) {
            super("Error while getting information from hh.ru:" + message + ".");
        }
    }


    protected abstract String baseURL();

    protected String generateQuery(List<String> hierarchy, Map<String, String> params) {
        return baseURL() +
                hierarchy.stream().collect(Collectors.joining("/", "/", "")) +
                params.entrySet().stream()
                        .map((e) -> e.getKey() + "=" + e.getValue())
                        .collect(Collectors.joining("&", "?", ""));
    }


    protected JsonNode getJsonNode(String uri) throws OuterAPIException {
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

    protected <T> List<T> getEntities(String query, Pagination pagination, Class<T> entity, Class<?> jsonView) throws OuterAPIException, JsonProcessingException {
        JsonNode node = getJsonNode(
                generateQuery(List.of(), Map.ofEntries(
                        Map.entry("text", query),
                        Map.entry("page", Integer.toString(pagination.page)),
                        Map.entry("per_page", Integer.toString(pagination.perPage))
                ))
        );
        List<T> entities = new ArrayList<>();
        for (JsonNode child : node.get("items")) {
            entities.add(mapper
                    .readerWithView(jsonView)
                    .treeToValue(child, entity)
            );
        }
        return entities;
    }

    protected <T> T getEntity(Long id, Class<T> entity, Class<?> jsonView) throws OuterAPIException, JsonProcessingException {
        JsonNode node = getJsonNode(
                generateQuery(List.of(id.toString()), Map.of())
        );
        return mapper.readerWithView(jsonView).treeToValue(node, entity);
    }
}
