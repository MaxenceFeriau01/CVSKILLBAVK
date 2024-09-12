package com.ensemble.entreprendre.features.core;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class StepService {

    public void removeKeyFromJsonNode(JsonNode node, String fieldToIgnore) {
        if (node != null) {
            ((ObjectNode) node).remove(fieldToIgnore);
        }
    }

    /**
     * Remove key from array. Used when we get embedded key in array of objects
     * 
     * @param jsonNode
     * @param fieldToIgnore
     */
    public void removeKeyFromArrayNode(JsonNode jsonNode, String fieldToIgnore) {
        if (jsonNode != null) {
            for (JsonNode node : jsonNode) {
                ((ObjectNode) node).remove(fieldToIgnore);
            }
        }

    }

}