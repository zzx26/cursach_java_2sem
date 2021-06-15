package com.company;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;

public class Main {

    private static ArrayList<City> clone_set(ArrayList<City> set) {
        ArrayList<City> clone = new ArrayList<City>();
        for (City i: set) clone.add(i);
        return clone;
    }

    static void intersection(ArrayList<City> set_1, ArrayList<City> set_2, ArrayList<City> set_3) {
        set_3.clear();
        for (City i: set_1){
            for (City j: set_2) {
                if (i.name.equals(j.name)) {
                    set_3.add(i);
                }
            }
        }
    }

    static void union(ArrayList<City> set_1, ArrayList<City> set_2, ArrayList<City> set_3) {
        set_3.clear();
        for (City i: set_1){
            set_3.add(i);
        }
        for (City i: set_2){
            Boolean contains = false;
            for (City j: set_3) {
                if (i.name.equals(j.name)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                set_3.add(i);
            }
        }
    }

    static void difference(ArrayList<City> set_1, ArrayList<City> set_2, ArrayList<City> set_3) {
        set_3.clear();
        intersection(set_1, set_2, set_3);
        ArrayList<City> set_4 = set_3;
        set_3.clear();
        for (City i: set_1) {
            Boolean contains = false;
            for (City j: set_4) {
                if (i.name.equals(j.name)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                set_3.add(i);
            }
        }
    }

    static void symmetric_diff(ArrayList<City> set_1, ArrayList<City> set_2, ArrayList<City> set_3) {
        set_3.clear();
        difference(set_1, set_2, set_3);
        ArrayList<City> set_4 = clone_set(set_3);
        set_3.clear();
        difference(set_2, set_1, set_3);
        ArrayList<City> set_5 = clone_set(set_3);
        set_3.clear();
        union(set_4, set_5, set_3);
    }

    public static void display_set(ArrayList<City> set) {
        for (City i: set) {
            System.out.print(i.name + " ");
        }
        System.out.println(" ");
    }


    public static void main(String[] args) throws IOException {
        ArrayList<City> set_1 = new ArrayList<>();
        ArrayList<City> set_2 = new ArrayList<>();
        ArrayList<City> set_3 = new ArrayList<>();
        String path = "Cities.txt";
        String output_path = "Output.txt";

        System.out.println("Importing data to sets from file");
        FileReader read_file = new FileReader(path);
        BufferedReader buffer = new BufferedReader(read_file);
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Select intersection quality");
        System.out.println("1 - major");
        System.out.println("2 - minor");
        System.out.println("3 - none");
        int choice = Integer.parseInt(rd.readLine());
        double a;
        switch (choice) {
            case 1:
                a = 0.37;
                break;
            case 2:
                a = 0.45;
                break;
            case 3:
                a = 0.5;
                break;
            default:
                a = 0.4;
                break;
        }

        String line;
        while((line = buffer.readLine()) != null)
        {
            double destiny = Math.random();
            City p = new City();
            p.read_city(line);
            if (destiny <= a) {
                set_1.add(p);
            }
            else if (destiny <= 2*a) {
                set_2.add(p);
            }
            else {
                set_1.add(p);
                set_2.add(p);
            }
        }

        System.out.print("set_1 is: ");
        display_set(set_1);
        System.out.println(" ");
        System.out.print("set_2 is: ");
        display_set(set_2);
        System.out.println(" ");
        boolean sort = false;
        while (!sort) {
            System.out.println("Choose action");
            System.out.println("1 - intersection");
            System.out.println("2 - union");
            System.out.println("3 - difference A/B");
            System.out.println("4 - difference B/A");
            System.out.println("5 - symmetric difference");
            System.out.println("6 - write to file");
            System.out.println("7 - exit");

            int menu = Integer.parseInt(rd.readLine());
            switch (menu) {
                case 1: {
                    intersection(set_1, set_2, set_3);
                    System.out.print("intersection is: ");
                    display_set(set_3);
                    break;
                }
                case 2: {
                    union(set_1, set_2, set_3);
                    System.out.print("union is: ");
                    display_set(set_3);
                    break;
                }
                case 3:
                {
                    difference(set_1, set_2, set_3);
                    System.out.print("difference A/B is: ");
                    display_set(set_3);
                    break;
                }
                case 4:
                {
                    difference(set_2, set_1, set_3);
                    System.out.print("difference B/A is: ");
                    display_set(set_3);
                    break;
                }
                case 5:
                {
                    symmetric_diff(set_1, set_2, set_3);
                    System.out.print("symmetric difference is: ");
                    display_set(set_3);
                    break;
                }
                case 6:
                {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(output_path));
                    for (City i: set_3){
                        writer.write(i.write_city() + "\n");
                    }
                    writer.close();
                    break;
                }
                case 7:
                {
                    sort = true;
                    System.out.println("Exiting...");
                    break;
                }
            }
        }
    }

}


class City {
    String name, country;
    Boolean is_capital,  over_million;

    //считывает строку и заносит данные в поля класса
    public void read_city(String s) {
        String[] parameters = s.split(" ");
        name = parameters[0];
        country = parameters[1];
        is_capital = parameters[2].equals("y");
        over_million = parameters[3].equals("y");
    }

    public String write_city() {
        String s = MessageFormat.format("city: {0}; country: {1}; is capital: {2}; over million population: {3}",
                name, country, Boolean.toString(is_capital), Boolean.toString(over_million));
        return s;
    }

    public void display_city() {
        String s = MessageFormat.format("city: {0}; country: {1}; is capital: {2}; over million population: {3}",
                name, country, Boolean.toString(is_capital), Boolean.toString(over_million));
        System.out.println(s);
    }
}