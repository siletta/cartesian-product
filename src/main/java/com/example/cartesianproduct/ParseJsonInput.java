package com.example.cartesianproduct;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParseJsonInput
{
    private MultiMap<String,String> tempMap;

    ParseJsonInput() {
        tempMap = new MultiValueMap<>();
    }

    public void parseJson() {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        File jsonFile = new File("input.json");
        try {
            JsonNode rootNode = mapper.readTree(jsonFile);
            System.out.println("root node: " + rootNode);
            process("",rootNode);
            tempMap.forEach((k,v) -> System.out.println(k + ":" + v));

        }catch(Exception e) {
            System.err.println("Not possible to read from Json Object");
        }
    }

    private void anotherProcess(JsonNode rootNode) {
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
            System.out.println("Key is a " + field.getValue().getNodeType().name());
            System.out.println("Next node: " + field.getValue().iterator().next());
            //while(field.getValue().iterator().hasNext()) {
            if (field.getValue().iterator().next().getNodeType().name() == "STRING") {
                System.out.println(field.getValue().iterator().next() + " is a STRING");
            } else {
                System.out.println(field.getValue().iterator().next() + " is a OBJECT");
                //Iterator<JsonNode> it = ;
                while (field.getValue().iterator().hasNext()) {
                    JsonNode current = field.getValue().iterator().next();
                    try {
                        String key = current.traverse().nextFieldName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                //}
            }
        }
    }


    private void nonRecursive(JsonNode root) {
        for (JsonNode node:root) {
            while (node.iterator().hasNext()) {
                if(node.isArray()) {
                    node.iterator().next();
                    if ("OBJECT".equals(node.iterator().next().getNodeType().toString())) {
                        node.iterator().next().fields().forEachRemaining(entry -> System.out.println("key " + entry.getKey() + " value " + entry.getValue()));
                    }
                    //System.out.println("prova valori: " + node.fieldNames().next());
                }
                else if(node.isObject()) {
                    System.out.println("key: " + node.fields().next().getKey() + " value: " + node.fields().next().getValue());
                }
                else {
                    System.out.println("key: " + node.fields().next().getKey() + " value: " + node.fields().next().getValue());
                }
            }
        }
    }


    private void process(String prefix, JsonNode nextNode) {
        if (nextNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) nextNode;
            Iterator<JsonNode> node = arrayNode.elements();
            int index = 1;
            while (node.hasNext()) {
                //process(!prefix.isEmpty() ? prefix + "-" + index : String.valueOf(index), node.next());
                process(prefix, node.next());
                index += 1;
            }
        }
        else if (nextNode.isObject()) {
            //nextNode.fields().forEachRemaining(entry -> process(!prefix.isEmpty() ? prefix + "-" + entry.getKey() : entry.getKey(), entry.getValue()));
            nextNode.fields().forEachRemaining(entry -> process(entry.getKey(), entry.getValue()));
        }
        else if (nextNode.isTextual()) {
            System.out.println(prefix + ": " + nextNode.toString());
            tempMap.put(prefix, nextNode.toString());
        }
    }
}
