package com.xuexiang.templateproject.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xuexiang.templateproject.utils.HTR_RGBA;
import com.xuexiang.templateproject.utils.RandomGenerator;


public class AnonymousColor {

    public List<HTR_RGBA> DHC_v1_color_List;
    public List<HTR_RGBA> HTR_v1_color_List;
    public Map<String, List<HTR_RGBA>> colorlist_dictionary;
    public AnonymousColor(){
        DHC_v1_color_List = new ArrayList<HTR_RGBA>(Arrays.asList(
                new HTR_RGBA(137, 225, 174, 63),
                new HTR_RGBA(138, 211, 191, 63),
                new HTR_RGBA(142, 196, 202, 63),
                new HTR_RGBA(143, 177, 207, 63),
                new HTR_RGBA(137, 157, 209, 63),
                new HTR_RGBA(127, 134, 211, 63),
                new HTR_RGBA(122, 117, 211, 63),
                new HTR_RGBA(124, 103, 209, 63),
                new HTR_RGBA(104, 80, 208, 63)));
        HTR_v1_color_List = new ArrayList<HTR_RGBA>(Arrays.asList(
                new HTR_RGBA(137, 225, 174, 126),
                new HTR_RGBA(138, 211, 191, 126),
                new HTR_RGBA(142, 196, 202, 126),
                new HTR_RGBA(143, 177, 207, 126),
                new HTR_RGBA(137, 157, 209, 126),
                new HTR_RGBA(127, 134, 211, 126),
                new HTR_RGBA(122, 117, 211, 126),
                new HTR_RGBA(124, 103, 209, 126),
                new HTR_RGBA(104, 80, 208, 126)));
        colorlist_dictionary = new HashMap<String, List<HTR_RGBA>>(){
            {
                put("dhc_v1", DHC_v1_color_List);
                put("htr_v1", HTR_v1_color_List);
            }

        };
    }
    public List<HTR_RGBA>getcolorlist(String type, int seed){
        List<HTR_RGBA> colorlist = colorlist_dictionary.get(type);
        RandomGenerator rg = new RandomGenerator(seed);
        assert colorlist != null;
        for (int i = 1; i < colorlist.size(); i++){
            Collections.swap(colorlist, i, (int) (rg.next() % (i + 1)));
        }
        return colorlist;
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
