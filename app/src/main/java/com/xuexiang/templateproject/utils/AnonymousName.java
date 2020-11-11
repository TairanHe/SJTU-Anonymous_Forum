package com.xuexiang.templateproject.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonymousName {

    static List<String> ABC_English_Name_List = new ArrayList<String>(Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve", "Forest", "George", "Harry", "Issac", "Justin", "Kevin", "Laura", "Mallory", "Neal", "Oscar", "Pat", "Quentin", "Rose", "Steve", "Trent", "Utopia", "Victor", "Walter", "Xavier", "Young", "Zoe"));
    static List<String> US_President_English_Name_List = new ArrayList<String>(Arrays.asList("Washington", "J.Adams", "Jefferson", "Madison", "Monroe", "J.Q.Adams", "Jackson", "Buren", "W.H.Harrison", "J.Tyler", "Polk", "Z.Tylor", "Fillmore", "Pierce", "Buchanan", "Lincoln", "A.Johnson", "Grant", "Hayes", "Garfield", "Arthur", "Cleveland", "B.Harrison", "McKinley", "T.T.Roosevelt","Taft", "Wilson", "Harding", "Coolidge", "Hoover", "F.D.Roosevelt", "Truman", "Eisenhower", "Kennedy", "L.B.Johnson", "Nixon", "Ford", "Carter", "Reagan", "G.H.W.Bush", "Clinton","G.W.Bush", "Obama", "Trump"));
    public static String getname(String sequence, String type){
        if(type=="abc"){
            System.out.println(ABC_English_Name_List.get(Integer.parseInt(sequence)-1));
            return ABC_English_Name_List.get(Integer.parseInt(sequence)-1);
        }
        else if (type=="us_president"){
            System.out.println(US_President_English_Name_List.get(Integer.parseInt(sequence)-1));
            return US_President_English_Name_List.get(Integer.parseInt(sequence)-1);
        }

        else
            return "Something wrong";

    }
}
