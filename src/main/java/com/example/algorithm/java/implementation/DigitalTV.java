package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitalTV {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int channelCount = Integer.parseInt(br.readLine());
        String[] channels = new String[channelCount + 1];
        int kbs1Position = 0;
        int kbs2Position = 0;
        StringBuilder buttonSequence = new StringBuilder();
        boolean isKbs1Found = false;

        for (int i = 1; i <= channelCount; i++) {
            String channelName = br.readLine();
            channels[i] = channelName;

            if (channelName.equals("KBS1")) {
                isKbs1Found = true;
                kbs1Position = i;
            }
            if (channelName.equals("KBS2")) {
                kbs2Position = i;
            }
            if (!isKbs1Found)
                buttonSequence.append("1");
        }

        channels[0] = "";
        String temp = channels[0];

        for (int i = 1; i <= kbs1Position; i++) {
            String currentChannel = channels[i];
            channels[i] = temp;
            temp = currentChannel;

            if (i != kbs1Position)
                buttonSequence.append("4");

            if (i != 2 && channels[i].equals("KBS2")) {
                kbs2Position = i;
            }
        }

        channels[1] = "KBS1";
        for (int i = 1; i < kbs2Position; i++) {
            buttonSequence.append("1");
        }
        for (int i = 2; i < kbs2Position; i++) {
            buttonSequence.append("4");
        }
        System.out.println(buttonSequence);
    }
}

/*
4
ABC1
ABC02
KBS2
KBS1

11144411144


4
ABC1
ABC02
KBS2
KBS1

11144411144
*/