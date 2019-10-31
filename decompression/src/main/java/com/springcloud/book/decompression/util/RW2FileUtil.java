package com.springcloud.book.decompression.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class RW2FileUtil {
    public static void writeJSONObject2File(JSONObject json,String fileName){
        if (json!=null){
            try {
                File f = new File(fileName);
                if (!f.exists()) {
                	File parent = new File(f.getParent());
					parent.mkdirs();
					System.out.println("**********创建文件路径**********"+f.getParent());
				}
                FileWriter fw = new FileWriter(f);
                fw.write(json.toString());
                fw.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            	System.out.println("记录完成");
			}

        }
    }
    
    public static Map<String,Object> readFile2JSONObject(String fileName){
    	StringBuffer sb = new StringBuffer();
    	if (fileName!=null && !"".equals(fileName)){
        	File file = new File(fileName);
        	if (file.exists() && file.isFile()) {
        		try{
        			FileReader fr = new FileReader(file);
        			BufferedReader br = new BufferedReader(fr);
        			String line = null;
        			while((line = br.readLine()) != null){
        				sb.append(line);
        			}
        			fr.close();
        		}catch(Exception e){
        			e.printStackTrace();
        		}finally {
        			System.out.println("读取完成");
				}
			}else{
				System.out.println("路径不存在");
			}
        }
    	if(sb.toString()!=null && !"".equals(sb.toString())){
    		Map<String,Object> map = JSON.parseObject(sb.toString(), new HashMap<String,Object>().getClass());
    		return map;
    	}
    	return null;
    }
}
