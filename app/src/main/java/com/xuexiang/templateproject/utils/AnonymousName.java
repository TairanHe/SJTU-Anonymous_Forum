package com.xuexiang.templateproject.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonymousName {

    static List<String> ABC_English_Name_List = new ArrayList<String>(Arrays.asList("Adam", "Bob", "Chris", "Dylan", "Eric", "Forest", "George", "Harry", "Isabella", "Jack", "Kevin", "Laura", "Morris", "Neal", "Olivia", "Parker", "Quentin", "Rose", "Steven", "Talor", "Utopia", "Victor", "Westbrook", "Xavier", "Young", "Zoe"));
    public static String getname(String sequence){

        System.out.println(ABC_English_Name_List.get(Integer.parseInt(sequence)-1));

        return ABC_English_Name_List.get(Integer.parseInt(sequence)-1);
    }
}
