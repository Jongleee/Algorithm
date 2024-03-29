package com.example.algorithm.java.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataAnalysis {
    public int[][] solution(int[][] data, String ext, int val_ext, String sort_by) {
        int extIndex = getIndex(ext);
        int sortByIndex = getIndex(sort_by);

        List<int[]> filteredList = new ArrayList<>();
        for (int[] arr : data) {
            if (arr[extIndex] < val_ext) {
                filteredList.add(arr);
            }
        }

        Collections.sort(filteredList, (o1, o2) -> {
            return Integer.compare(o1[sortByIndex], o2[sortByIndex]);
        });

        int[][] filteredData = new int[filteredList.size()][];
        for (int i = 0; i < filteredList.size(); i++) {
            filteredData[i] = filteredList.get(i);
        }

        return filteredData;
    }

    private int getIndex(String key) {
        switch (key) {
            case "code":
                return 0;
            case "date":
                return 1;
            case "maximum":
                return 2;
            case "remain":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid key: " + key);
        }
    }

    // @Test
    // void 정답() {
    //     int[][][] data = { { { 1, 20300104, 100, 80 }, { 2, 20300804, 847, 37 }, { 3, 20300401, 10, 8 } } };
    //     String[] ext = { "date" };
    //     int[] val_ext = { 20300501 };
    //     String[] sort_by = { "remain" };

    //     int[][][] result = { { { 3, 20300401, 10, 8 }, { 1, 20300104, 100, 80 } } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(data[i], ext[i], val_ext[i], sort_by[i]));
    //     }
    // }
}
