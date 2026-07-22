package project.mqtt.supprot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String toJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("JSON 직렬화 실패: {}", e.getMessage());
            return "json 직렬화 실패";
        }
    }

    public static String toPrettyJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.warn("[Utils] JSON 역직렬화 실패: {}", e.getMessage());
            return null;
        }
    }

    public static <T> T fromJsonToMapList(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            log.warn("[Utils] JSON(Map/List) 역직렬화 실패: {}", e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean replaceCowEntityAndManageNoByCowIdRecursive(
            Object node,
            String currentCowEntityId, String newCowEntityId,
            String newManageNo
    ) {
        boolean updated = false;

        if (node instanceof Map<?, ?> rawMap) {
            Map<String, Object> map = (Map<String, Object>) rawMap;

            if (map.containsKey("cowEntityId")) {
                Object cowIdVal = map.get("cowEntityId");

                if (cowIdVal instanceof String cowId
                    && currentCowEntityId != null
                    && cowId.equals(currentCowEntityId)) {

                    if (newManageNo != null && !newManageNo.isBlank()) {
                        map.put("manageNo", newManageNo);
                        updated = true;
                    }

                    if (newCowEntityId != null && !newCowEntityId.isBlank()) {
                        map.put("cowEntityId", newCowEntityId);
                        updated = true;
                    }
                }
            }

            for (Object value : map.values()) {
                if (value instanceof Map<?, ?> || value instanceof List<?>) {
                    updated |= replaceCowEntityAndManageNoByCowIdRecursive(
                            value,
                            currentCowEntityId, newCowEntityId, newManageNo
                    );
                }
            }
        }

        else if (node instanceof List<?> list) {
            for (Object item : list) {
                updated |= replaceCowEntityAndManageNoByCowIdRecursive(
                        item,
                        currentCowEntityId, newCowEntityId, newManageNo
                );
            }
        }

        return updated;
    }

}
