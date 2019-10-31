package com.springcloud.book.foreign.util.pubmed;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PubmedSearch {
    public PubmedSearch() {
    }

    public Result query(String term, long index, long pageCount) {
        Result result = new Result();
        Http http = new Http();
        HashMap headers = new HashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.2 Safari/605.1.15");
        HashMap params = new HashMap();
        params.put("db", "pubmed");
        params.put("term", term);
        params.put("retmode", "json");
        params.put("retmax", pageCount);
        params.put("retstart", index);
        //params.put("sort", "pub date");
        Http.Response response = http.post("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi", "utf-8", headers, params);
        if (response.code == 200) {
            try {
                JSONObject respJSON = new JSONObject(response.content);
                JSONObject sresult = respJSON.getJSONObject("esearchresult");
                int count = sresult.getInt("count");
                JSONArray uids = sresult.getJSONArray("idlist");
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < uids.length(); ++i) {
                    if (i != uids.length() - 1) {
                        sb.append(uids.getString(i) + ",");
                    } else {
                        sb.append(uids.getString(i));
                    }
                }

                Result eresult = this.esummary(sb.toString());
                if (eresult.code == 0) {
                    result.code = 0;
                    result.count = count;
                    result.content = eresult.content;
                } else {
                    result.code = 1;
                    result.message = eresult.message;
                }
            } catch (Exception var15) {
                result.code = 1;
                result.message = var15.getLocalizedMessage();
            }
        } else {
            result.code = 1;
            result.message = response.code + "," + response.error;
        }

        return result;
    }

    public Result esummary(String ids) {
        Result result = new Result();
        Http http = new Http();
        HashMap headers = new HashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.2 Safari/605.1.15");
        HashMap params = new HashMap();
        params.put("db", "pubmed");
        params.put("retmode", "xml");
        params.put("rettype", "abstract");
        params.put("id", ids);
        Http.Response response = http.post("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi", "utf-8", headers, params);
        if (response.code == 200) {
            result.code = 0;
            result.content = response.content;
        } else {
            result.code = 1;
            result.message = response.code + "," + response.error;
        }

        return result;
    }

    public String fetch(String id) {
        Http http = new Http();
        HashMap headers = new HashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.2 Safari/605.1.15");
        HashMap params = new HashMap();
        params.put("db", "pubmed");
        params.put("retmode", "xml");
        params.put("rettype", "abstract");
        params.put("id", id);
        Http.Response response = http.post("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi", "utf-8", headers, params);
        return response.code == 200 ? response.content : null;
    }
}
