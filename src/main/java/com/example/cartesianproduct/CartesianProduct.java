package com.example.cartesianproduct;
import java.util.Map;
import java.util.List;

public class CartesianProduct {

    private List<List<Map<String, String>>> array;

    public CartesianProduct(List<List<Map<String, String>>> array) {
        this.array = array;
    }

    public void print() {
        array.forEach(maps -> {
            maps.forEach((map) -> {
                map.forEach((k, v) -> {
                    System.out.println("key: " + k + ", value: " + v);
                });
            });
        });
    }

    public static void main( String[] args )
    {
        ParseJsonInput pi = new ParseJsonInput();
        pi.parseJson();
    }

    public void printList() {
        array.forEach(maps -> {System.out.println(maps);});
    }

}

