package com.springcloud.book.decompression.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIPUtill {
	
  static Logger logger = LoggerFactory.getLogger(ZIPUtill.class);
	
	//判断数组中是否有某个值
	public static boolean contains(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

	//创建文件
    public static File createFile(String dstPath, String fileName) throws Exception{
        // 00110640R/12-NA/11609742.xml
        // pubmed/8008349/40-9/11609742.xml
        //期刊
        //journal/....xml
	    String[] dirs = fileName.split("/");
        File file = new File(dstPath);
        if (dirs.length > 1){
            int index = 0;
            for (String dirStr : dirs){
                //期刊           journal
                if (dirStr.contains("journal")){
                    file = new File(file,"pubmed");
                    file = new File(file,dirs[dirs.length-1]);
                    break;
                }
                //摘要
                else {
                    if (!contains(dirs,"pubmed") && index == 0){
                        file = new File(file,"pubmed");
                        index++;
                    }
                    file = new File(file,dirStr.replaceAll(":","_").replaceAll("\\*","_"));
                }
            }
            File pFile = file.getParentFile();
            if (!pFile.exists()){
                pFile.mkdirs();
            }
        }else {
            throw new RuntimeException("压缩文件路径不符合要求");
        }
        return file;
    }

    /**
     * 压缩文件解压
     * @param zipFileName 压缩文件
     * @param dstPath 解压文件目标路径
     * @return
     */
    public static Set<String> unzip(String zipFileName,String dstPath){
        Set<String> catalogList = new HashSet<>();
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry zipEntry = null;
            byte[] buffer = new byte[1024];
            int readLength = 0;
            while ((zipEntry = zipInputStream.getNextEntry()) != null){
            	//获取文件写入路径
                if (!zipEntry.isDirectory()){
                    File file = createFile(dstPath,zipEntry.getName());
                    if (file.exists()){
                        file.delete();
                        System.out.println("存在数据已删除");
                    }
                    try {
                        OutputStream outputStream = new FileOutputStream(file);
                        while ((readLength = zipInputStream.read(buffer,0,512)) != -1){
                            outputStream.write(buffer,0,readLength);
                        }
                        outputStream.close();
                        String path = file.getCanonicalPath();
                        Path paths = Paths.get(path);
                        List<String> pathList = new ArrayList<>();
                        for(Iterator<Path> it = paths.iterator(); it.hasNext();) {
                            pathList.add(it.next().toString());
                        }
                        if (pathList.size() >= 3){
                            catalogList.add(pathList.get(pathList.size() - 3));
                        }
                        System.out.println("file finished :" + file.getCanonicalPath());
                    } catch (IOException e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            }
            zipInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("unzip success...!");
        return catalogList;
    }

    /**
     * 压缩文件解压
     * @param zipFilePath 压缩文件所在目录
     * @param dstPath   解压文件的目标路径
     * @param lastTime  已解压文件的时间位置
     * @param catalogSet  解压位置记录
     * @throws Exception
     */
    public static Map<String, Object> getUnzip(String zipFilePath, String dstPath,Long lastTime,Set<String> catalogSet )throws Exception{
        Map<String,Object> map = new HashMap<>();
        File file = new File(zipFilePath);
        if (!file.exists()){
            map.put("msg","压缩文件路径不存在");
            return map;
        }
        String[] fileArray = file.list();
        File dstPathFile = new File(dstPath);
        if(!dstPathFile.exists()){
        	dstPathFile.mkdirs();
        	System.out.println("解压文件存放路径不存在，已完成创建。");
        }
        long lastModifiedTime = lastTime;
        boolean isChange = false;
        for (String str : fileArray){
            File f = new File(file.getCanonicalPath(),str);
            long fSize = f.length();
            if (fSize >0) {
            	int index = f.getName().toLowerCase().indexOf(".zip");
                if (index > 0){
                	long modifiedTime = f.lastModified();
                	if(lastTime < modifiedTime){
                		System.out.println("上次解压文件的文件时间:"+lastTime+",解压文件的文件时间:"+modifiedTime);
                		Set<String> set = ZIPUtill.unzip(f.getCanonicalPath(),dstPath);
            			if(set != null && !set.isEmpty()){
            				catalogSet.addAll(set);
            			}
            			if(modifiedTime > lastModifiedTime){
            				lastModifiedTime = modifiedTime;
            				isChange = true;
            			}
                	}else{
                		System.out.println(f.getName()+"是已完成解压的文件");
                	}
                }else {
                	System.out.println(f.getCanonicalPath() + "不是压缩文件");
                }
			}
        }
        //记录解压信息
        if (isChange) {
            //JSONObject json = new JSONObject();
            map.put("time", lastModifiedTime);
            map.put("set", catalogSet);
            map.put("msg","SUCCESS");
    		//RW2FileUtil.writeJSONObject2File(json, filePath);
		}
		return map;
    }
}
