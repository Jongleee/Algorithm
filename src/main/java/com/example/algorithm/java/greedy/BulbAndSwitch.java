package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BulbAndSwitch {
    static int n;
    static int ans = Integer.MAX_VALUE;
    static char[] lights;
    static char[] objLights;
    static char[] tempLights;
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        lights = br.readLine().toCharArray();
        objLights = br.readLine().toCharArray();
        
        tempLights = lights.clone();
        solve(0, false);
        
        tempLights = lights.clone();
        switchLight(0);
        switchLight(1);
        solve(1, true);
        
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
        br.close();
    }
    
    public static void solve(int count, boolean firstSwitched) {
        for (int i = 1; i < n; i++) {
            if (tempLights[i - 1] != objLights[i - 1]) {
                count++;
                push(i);
            }
        }
        
        if (tempLights[n - 1] == objLights[n - 1]) {
            ans = Math.min(ans, count);
        } else if (firstSwitched && count == 1) {
            ans = -1;
        }
    }
    
    public static void push(int i) {
        switchLight(i - 1);
        switchLight(i);
        if (i != n - 1) {
            switchLight(i + 1);
        }
    }
    
    public static void switchLight(int i) {
        tempLights[i] = (tempLights[i] == '0') ? '1' : '0';
    }
}
/*
3
000
010

3
 */