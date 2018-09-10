package com.example.cartesianproduct;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParseJsonInput
{
    public void parseJson() {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        File jsonFile = new File("input.json");
        try {
            JsonNode rootNode = mapper.readTree(jsonFile);
            System.out.println("root node: " + rootNode);
            //anotherProcess(rootNode);
            process("",rootNode);
            /*Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
            while (fieldsIterator.hasNext()) {
                Map.Entry<String,JsonNode> field = fieldsIterator.next();
                System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
                System.out.println ("Node type: " + field.getValue().getNodeType().name());
                for (int i = 0; i < field.getValue().size(); i++) {
                    System.out.println("i: " + i + " Value: " + field.getValue().get(i).asText());
                    System.out.println("field.getValue().get(i).isObject()? " + field.getValue().get(i).isObject());
                    field.getValue().get(i);
                }
                System.out.println("Is text?: " + field.getValue().elements().next().isTextual());
                System.out.println("Node type " + field.getValue().elements().next().getNodeType().name());

            }*/
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


    private static void process(String prefix, JsonNode currentNode) {
        if (currentNode.isArray()) {
            System.out.println(prefix + " Is an array");
            ArrayNode arrayNode = (ArrayNode) currentNode;
            Iterator<JsonNode> node = arrayNode.elements();
            int index = 1;
            currentNode
            while (node.hasNext()) {
                process(!prefix.isEmpty() ? prefix + "-" + index : String.valueOf(index), node.next());
                index += 1;
            }
        }
        else if (currentNode.isObject()) {
            System.out.println(prefix + " Is an object");
            currentNode.fields().forEachRemaining(entry -> process(!prefix.isEmpty() ? prefix + "-" + entry.getKey() : entry.getKey(), entry.getValue()));
        }
        else if (currentNode.isTextual()) {
            List<JsonNode> parents = new ArrayList<JsonNode>();
            System.out.println("Parent: " + currentNode.findParents(currentNode.toString()));
            System.out.println("String " + prefix + ": " + currentNode.toString());
        }
    }
}
