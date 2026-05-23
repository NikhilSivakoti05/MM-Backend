package com.sgcore.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgcore.backend.model.FAQ;
import com.sgcore.backend.model.ClientFeedback;

import java.util.List;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<FAQ> parseFaqs(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<FAQ>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Invalid FAQ JSON");
        }
    }

    public static List<ClientFeedback> parseFeedbacks(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<ClientFeedback>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Invalid Feedback JSON");
        }
    }
}
