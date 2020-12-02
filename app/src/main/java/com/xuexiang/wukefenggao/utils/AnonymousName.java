package com.xuexiang.wukefenggao.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnonymousName {

    public List<String> ABC_English_Name_List;
    public List<String> US_President_English_Name_List;
    public List<String> Tarot_English_Name_List;
    public Map<String, List<String>> namelist_dictionary;

    public AnonymousName(){
        ABC_English_Name_List = new ArrayList<String>(Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve", "Forest", "George", "Harry", "Issac", "Justin", "Kevin", "Laura", "Mallory", "Neal", "Oscar", "Pat", "Quentin", "Rose", "Steve", "Trent", "Utopia", "Victor", "Walter", "Xavier", "Young", "Zoe"));
        US_President_English_Name_List = new ArrayList<String>(Arrays.asList("Washington", "J.Adams", "Jefferson", "Madison", "Monroe", "J.Q.Adams", "Jackson", "Buren", "W.H.Harrison", "J.Tyler", "Polk", "Z.Tylor", "Fillmore", "Pierce", "Buchanan", "Lincoln", "A.Johnson", "Grant", "Hayes", "Garfield", "Arthur", "Cleveland", "B.Harrison", "McKinley", "T.T.Roosevelt","Taft", "Wilson", "Harding", "Coolidge", "Hoover", "F.D.Roosevelt", "Truman", "Eisenhower", "Kennedy", "L.B.Johnson", "Nixon", "Ford", "Carter", "Reagan", "G.H.W.Bush", "Clinton","G.W.Bush", "Obama", "Trump"));
        Tarot_English_Name_List = new ArrayList<String>(Arrays.asList("The Fool", "The Magician", "The High Priestess", "The Empress", "The Emperor", "The Hierophant", "The Lovers", "The Chariot", "Justice", "The Hermit", "Wheel of Fortune", "Strength", "The Hanged Man", "Death", "Temperance", "The Devil", "The Tower", "The Star", "The Moon", "The Sun", "Judgement", "The World"));
        namelist_dictionary = new HashMap<String, List<String>>(){
            {
                put("abc", ABC_English_Name_List);
                put("us_president", US_President_English_Name_List);
                put("tarot", Tarot_English_Name_List);
            }

        };
    }
    public List<String>getnamelist(String type, int seed){
        List<String> namelist = namelist_dictionary.get(type);
        RandomGenerator rg = new RandomGenerator(seed);
        assert namelist != null;
        for (int i = 1; i < namelist.size(); i++){
            Collections.swap(namelist, i, (int) (rg.next() % (i + 1)));
        }
        return namelist;
    }

    public static String getname(List<String> namelist, int index){
        int size = namelist.size();
        if(index < size) {
            return namelist.get(index);
        }
        else {
            int suffix = index / size;
            int new_index = index % size;
            return namelist.get(new_index) + "." + suffix;
        }
    }
//    public String getname(String sequence, String type){
//        if (namelist_dictionary.containsKey(type)){
//            List<String> namelist = namelist_dictionary.get(type);
//            int intsequence = Integer.parseInt(sequence);
//            assert namelist != null;
//            int length_namelist = namelist.size();
//            if (intsequence < length_namelist){
//                return namelist.get(intsequence);
//            }
//            else {
//                int num = intsequence / length_namelist;
//                String suffix = "." + num;
//                return namelist.get(intsequence)+suffix;
//            }
//        }
//        else {
//            return "something wrong";
//        }
//    }
}
