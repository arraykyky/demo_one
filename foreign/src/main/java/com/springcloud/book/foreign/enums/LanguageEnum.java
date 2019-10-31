package com.springcloud.book.foreign.enums;

import java.util.HashMap;
import java.util.Map;

public enum LanguageEnum {
    ;
    public static String getLangCH(String key){
        Map<String,String> lanMap = new HashMap<>();
        lanMap.put("afr","挪威语");
        lanMap.put("alb","阿尔巴尼亚语");
        lanMap.put("amh","阿姆哈拉语");
        lanMap.put("ara","阿拉伯语");
        lanMap.put("arm","亚美尼亚语");
        lanMap.put("aze","阿塞拜疆语");
//        lanMap.put("bos","");
        lanMap.put("bul","保加利亚语");
        lanMap.put("cat","加泰罗尼亚语");
        lanMap.put("chi","中文");
        lanMap.put("cze","捷克语");
        lanMap.put("dan","丹麦语");
        lanMap.put("dut","荷兰语");
        lanMap.put("eng","英语");
        lanMap.put("epo","世界语");
        lanMap.put("est","爱沙尼亚语");
        lanMap.put("fin","芬兰语");
        lanMap.put("fre","法语");
        lanMap.put("geo","格鲁吉亚语");
        lanMap.put("ger","德语");
        //lanMap.put("gla","");
        lanMap.put("gre","希腊语");
        lanMap.put("heb","希伯来语");
        lanMap.put("hin","印地语");
        lanMap.put("hrv","克罗地亚语");
        lanMap.put("hun","匈牙利语");
        lanMap.put("ice","冰岛语");
        lanMap.put("ind","印度尼西亚语");
        lanMap.put("ita","意大利与");
        lanMap.put("jpn","日语");
        lanMap.put("kin","卢旺达语");
        lanMap.put("kor","朝鲜语");
        lanMap.put("lat","拉丁语");
        lanMap.put("lav","拉脱维亚语");
        lanMap.put("lit","立陶宛语");
        lanMap.put("mac","马其顿语");
        lanMap.put("mal","马拉雅拉姆语");
        lanMap.put("mao","毛利语");
        lanMap.put("may","马来语");
        lanMap.put("mul","多种语言");
        lanMap.put("nor","挪威语");
        lanMap.put("per","波斯语");
        lanMap.put("pol","波兰语");
        lanMap.put("por","葡萄牙语");
        lanMap.put("pus","普什图语");
        lanMap.put("rum","罗马尼亚语");
        lanMap.put("rus","俄语");
        lanMap.put("slo","斯洛伐克语");
        lanMap.put("slv","斯洛文尼亚语");
        lanMap.put("spa","西班牙语");
        lanMap.put("srp","塞尔维亚语");
        lanMap.put("swe","瑞典语");
        lanMap.put("tha","泰语");
        lanMap.put("tur","土耳其语");
        lanMap.put("ukr","乌克兰语");
        lanMap.put("und","西班牙语");
        lanMap.put("vie","越南语");
        lanMap.put("wel","威尔士语");
        return lanMap.get(key);
    }

}
