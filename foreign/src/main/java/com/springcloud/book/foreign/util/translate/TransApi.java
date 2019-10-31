package com.springcloud.book.foreign.util.translate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransApi {
    static Logger logger = LoggerFactory.getLogger(TransApi.class);
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    
    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    /**
     * 默认翻译——EN2CH
     * @param appid
     * @param securityKey
     * @param query
     * @return
     */
    public static String getStr(String appid, String securityKey,String query){
        String str = null;
        try{
            TransApi api = new TransApi(appid, securityKey);
            str = api.getTransResult(query, "auto", "zh");
            JSONObject json = JSON.parseObject(str, JSONObject.class);
            String tr = json.getString("trans_result");
            List<JSONObject> list = JSON.parseArray(tr, JSONObject.class);
            if(list!=null && list.size() > 0){
                str = list.get(0).getString("dst");
            }
        }catch (Exception e){
            logger.error(str);
        }
        return str;
    }

    /**
     * 定义翻译——CH2EN
     * @param appid
     * @param securityKey
     * @param query
     * @return
     */
    public static String getStrCh2EN(String appid, String securityKey,String query){
        TransApi api = new TransApi(appid, securityKey);
        String str = api.getTransResult(query, "auto", "en");
        JSONObject json = JSON.parseObject(str, JSONObject.class);
        String tr = json.getString("trans_result");
        List<JSONObject> list = JSON.parseArray(tr, JSONObject.class);
        if(list!=null && list.size() > 0){
            return list.get(0).getString("dst");
        }else{
            return null;
        }
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
