package javaconcepts.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Maptest {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> linkedMap = new LinkedHashMap<>();
        Map<Integer, String> treeMap = new TreeMap<>();
        Map<Integer, String> treeMapCustom = new TreeMap<>((k1, k2) -> k2.compareTo(k1));

        addData(map);
        addData(linkedMap);
        addData(treeMap);
        addData(treeMapCustom);

        System.out.println("hashMap: unordered unsorted \n");
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("linkedhashMap: ordered unsorted \n");
        linkedMap.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("treeMap: ordered sorted \n");
        treeMap.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("treeMap custom: ordered customsorted \n");
        treeMapCustom.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    private static void addData(Map<Integer, String> map) {
        map.put(8, "Jesus");
        map.put(-2, "Anjan");
        map.put(12, "Aditi");
        map.put(1, "Adarsh");
        map.put(89, "Jetha");
    }
}
