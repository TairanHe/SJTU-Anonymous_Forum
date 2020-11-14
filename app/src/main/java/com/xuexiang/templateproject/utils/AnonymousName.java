package com.xuexiang.templateproject.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnonymousName {


    static List<String> ABC_English_Name_List = new ArrayList<String>(Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve", "Forest", "George", "Harry", "Issac", "Justin", "Kevin", "Laura", "Mallory", "Neal", "Oscar", "Pat", "Quentin", "Rose", "Steve", "Trent", "Utopia", "Victor", "Walter", "Xavier", "Young", "Zoe"));
    static List<String> US_President_English_Name_List = new ArrayList<String>(Arrays.asList("Washington", "J.Adams", "Jefferson", "Madison", "Monroe", "J.Q.Adams", "Jackson", "Buren", "W.H.Harrison", "J.Tyler", "Polk", "Z.Tylor", "Fillmore", "Pierce", "Buchanan", "Lincoln", "A.Johnson", "Grant", "Hayes", "Garfield", "Arthur", "Cleveland", "B.Harrison", "McKinley", "T.T.Roosevelt","Taft", "Wilson", "Harding", "Coolidge", "Hoover", "F.D.Roosevelt", "Truman", "Eisenhower", "Kennedy", "L.B.Johnson", "Nixon", "Ford", "Carter", "Reagan", "G.H.W.Bush", "Clinton","G.W.Bush", "Obama", "Trump"));
    static Map<String, List<String>> namelist_dictionary = new HashMap<String, List<String>>(){
        {
            put("abc", ABC_English_Name_List);
            put("us_president", US_President_English_Name_List);
        }

    };

    public static String getname(String sequence, String type){
        if (namelist_dictionary.containsKey(type)){
            List<String> namelist = namelist_dictionary.get(type);
            int intsequence = Integer.parseInt(sequence);
            assert namelist != null;
            int length_namelist = namelist.size();
            if (intsequence < length_namelist){
                return namelist.get(intsequence);
            }
            else {
                int num = intsequence / length_namelist;
                String suffix = "." + num;
                return namelist.get(intsequence)+suffix;
            }
        }
        else {
            return "something wrong";
        }
    }


}
