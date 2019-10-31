package com.springcloud.book.foreign.util;

import com.springcloud.book.foreign.enums.ExcelType;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

public class ExcelUtil<T> {
    /**
     * 文件导出工具
     * @param excelTypeStr ExcelType枚举类的枚举值，对应获取对象字段map的switch中的case值
     * @param list  导出文件的数据对象集合
     * @param response
     * @throws Exception
     */
    public void export(String excelTypeStr , List<T> list, HttpServletResponse response)throws Exception{
        ExcelType excelType = ExcelType.valueOf(excelTypeStr);
        Map<String, String> enName = excelType.enName();
        Set<Map.Entry<String, String>> entries = enName.entrySet();
        XSSFWorkbook xb = new XSSFWorkbook();
        //创建Excel的工作sheet，对应到一个excel文档的tab
        XSSFSheet sheet = xb.createSheet();
        //创建第一行，标题行
        XSSFRow titleRow = sheet.createRow(0);
        //excel标题列控制符
        int i = 0;
        //遍历列数据，生成标题行信息
        for (Map.Entry<String,String> entry : entries){
            XSSFCell titleCell = titleRow.createCell(i);
            titleCell.setCellValue(entry.getValue());
            i++;
        }
        //数据行的行控制符
        int j = 1;
        for (T abstractSolr : list){
            //循环创建新行
            XSSFRow dataRow = sheet.createRow(j);
            //数据行的列控制符
            int k = 0;
            for (Map.Entry<String,String> entry : entries){
                try {
                    XSSFCell dataCell = dataRow.createCell(k);
                    Class<?> type = PropertyUtils.getPropertyType(abstractSolr, entry.getKey());
                    if (Integer.class.equals(type)){
                        dataCell.setCellValue(Integer.valueOf(String.valueOf(PropertyUtils.getProperty(abstractSolr,entry.getKey()))));
                    }else if (Date.class.equals(type)){
                        Date date = (Date) PropertyUtils.getProperty(abstractSolr, entry.getKey());
                        String year = DateUtil.format(date, "yyyy");
                        dataCell.setCellValue(year);
                    }else {
                        String str = String.valueOf(PropertyUtils.getProperty(abstractSolr,entry.getKey()));
                        if (str.length() > SpreadsheetVersion.EXCEL2007.getMaxTextLength()) {
                            str = str.substring(0,SpreadsheetVersion.EXCEL2007.getMaxTextLength() - 10) + "......";
                        }
                        dataCell.setCellValue(str);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    k++;
                }
            }
            j++;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        xb.write(baos);
        byte[] bytes = baos.toByteArray();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=abstracts.xlsx");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 读取.xls内容
     * @param file
     * @return
     * @throws Exception
     */
    public static List<ArrayList<String>> readXls (MultipartFile file) throws Exception{
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        //创建输入流
        InputStream input = null;
        //创建文档
        Workbook wb = null;
        try {
            input = file.getInputStream();
            //创建文档
            wb = WorkbookFactory.create(input);
            ArrayList<String> rowList = null;
            int totoalRows = 0;//总行数
            int totalCells = 0;//总列数
            //读取sheet(页)
            for (int sheetIndex = 0 ; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                Sheet hssfSheet = wb.getSheetAt(sheetIndex);
                if (hssfSheet == null) {
                    continue;
                }
                totoalRows = hssfSheet.getLastRowNum();
                //读取row
                for (int rowIndex = 0; rowIndex <= totoalRows; rowIndex++) {
                    Row hssfRow = hssfSheet.getRow(rowIndex);
                    if (hssfRow == null) {
                        continue;
                    }
                    rowList = new ArrayList<String>();
                    totalCells = hssfRow.getLastCellNum();
                    //读取列
                    for (int cellIndex = 0; cellIndex < totalCells; cellIndex++) {
                        Cell hssfCell = hssfRow.getCell(cellIndex);
                        if (hssfCell == null) {
                            rowList.add("");
                        } else {
                            //hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                            hssfCell.setCellType(CellType.STRING);
                            rowList.add(String.valueOf(hssfCell.getStringCellValue()));
                        }
                    }
                    list.add(rowList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if ( wb != null) {
                    wb.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    /**
     * 读取.xls内容
     * @param file
     * @param titleValueMap
     * @return
     * @throws Exception
     */
    public static List<Map<String,String>> readXlsToListMap(MultipartFile file, Map<String, String> titleValueMap) throws Exception{
        List<Map<String,String>> list = new ArrayList<>();
        //创建输入流
        InputStream input = null;
        //创建文档
        Workbook wb = null;
        try {
            input = file.getInputStream();
            //创建文档
            wb = WorkbookFactory.create(input);
            ArrayList<String> rowList = null;
            int totoalRows = 0;//总行数
            int totalCells = 0;//总列数
            //读取sheet(页)
            for (int sheetIndex = 0 ; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                //每一个sheet创建一个标题map
                Map<Integer,String> titleMap = new HashMap<>();
                Sheet hssfSheet = wb.getSheetAt(sheetIndex);
                if (hssfSheet == null) {
                    continue;
                }
                totoalRows = hssfSheet.getLastRowNum();
                //读取row
                boolean isTitleRow = true;
                int titleRow = 0;
                for (int rowIndex = 0; rowIndex <= totoalRows; rowIndex++) {
                    Map<String,String> rowObject = new HashMap<>();
                    Row hssfRow = hssfSheet.getRow(rowIndex);
                    if (hssfRow == null) {
                        continue;
                    }
                    //获取第一个不为空行的及为标题行
                    if (isTitleRow){
                        titleRow = rowIndex;
                        isTitleRow = false;
                    }
                    rowList = new ArrayList<String>();
                    totalCells = hssfRow.getLastCellNum();
                    //读取列
                    for (int cellIndex = 0; cellIndex < totalCells; cellIndex++) {
                        Cell hssfCell = hssfRow.getCell(cellIndex);
                        String value = null;
                        if (hssfCell != null) {
                            hssfCell.setCellType(CellType.STRING);
                            value = String.valueOf(hssfCell.getStringCellValue());
                        }
                        if (rowIndex == titleRow){
                            titleMap.put(cellIndex,value);
                        }else {
                            String titleName = titleMap.get(cellIndex);
                            if (titleValueMap.containsKey(titleName)){
                                rowObject.put(titleValueMap.get(titleName),value);
                            }else {
                                rowObject.put(titleName,value);
                            }
                        }
                    }
                    if (!rowObject.isEmpty()){
                        list.add(rowObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if ( wb != null) {
                    wb.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    /**
     * 读取excel指定位置的数据
     * @param is
     * @param startRow
     * @param readDataTitleCellsList
     * @param title
     * @return
     */
    public static List<Map> getPositionExcelData(InputStream is, Integer startRow, List<String> readDataTitleCellsList, String[] title) throws Exception{
        List<Map> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);
        if (title == null && title.length == 0){
            throw new RuntimeException("未定义数据标题title");
        }
        //从读取数据的标题中获取所在列号
        List<Integer> readCellList = new ArrayList<>();
        for (String str : readDataTitleCellsList){
            Row row = sheet.getRow(startRow);
            for (int c = 0 ; c < row.getLastCellNum();c++){
                Cell cell = row.getCell(c);
                if (cell != null){
                    CellType type = cell.getCellTypeEnum();
                    String value = cell.getStringCellValue();
                    if (value.equals(str)){
                        readCellList.add(c);
                    }
                }
            }
        }
        if (readCellList.size() != readDataTitleCellsList.size()){
            throw new RuntimeException("无法获取读取数据的列，请检查输入的“数据导入列名&数据起始行号”是否正确");
        }
        //标题行行号移动一行，读取数据行
        startRow++;
        for (;startRow < sheet.getLastRowNum();startRow++){
            Map map = new HashMap();
            Row row = sheet.getRow(startRow);
            for (int i=0;i< title.length ;i++){
                String key = title[i];
                Cell cell = row.getCell(readCellList.get(i));
                if (cell != null){
                    CellType type = cell.getCellTypeEnum();
                    Object value = null;
                    if( type.equals(CellType.NUMERIC)){
                        value = cell.getNumericCellValue();
                    }else if (type.equals(CellType.STRING)){
                        value = cell.getStringCellValue();
                    }
                    if (key != null && value != null && !"".equals(value)){
                        map.put(key,value);
                    }
                }
            }
            if (!map.isEmpty()){
                list.add(map);
            }
        }
        return list;
    }
}
