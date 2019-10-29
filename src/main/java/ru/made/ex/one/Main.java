package ru.made.ex.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        BufferedReader buffer = new BufferedReader(new FileReader(filePath));

        String nextString;
        while ((nextString = buffer.readLine()) != null) {

        }

    }
}
