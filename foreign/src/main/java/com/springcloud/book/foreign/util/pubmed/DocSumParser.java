/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springcloud.book.foreign.util.pubmed;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kingcl
 */
public class DocSumParser {

    public List<ShowData> parse(String xml) throws Exception {
        List<ShowData> docSums = new ArrayList();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        //忽略dtd验证
        dbFactory.setValidating(false);
        dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
        dbFactory.setFeature("http://xml.org/sax/features/validation", false);
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
        DocumentBuilder db = dbFactory.newDocumentBuilder();
        Document document = db.parse(new InputSource(new StringReader(xml)));
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node dsNode = nodeList.item(i);
            if ("DocSum".equals(dsNode.getNodeName())) {
                DocSum docSum = new DocSum();
                NodeList dsNodeList = dsNode.getChildNodes();
                for (int j = 0; j < dsNodeList.getLength(); j++) {
                    Node itemNode = dsNodeList.item(j);
                    if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                        if ("Id".equals(itemNode.getNodeName())){
                            docSum.id = itemNode.getTextContent().trim();
                        }
                        if ("Item".equals(itemNode.getNodeName())) {
                            Item item = new Item();
                            item.name = itemNode.getAttributes().getNamedItem("Name").getNodeValue();
                            item.value = itemNode.getTextContent().trim();
                            item.type = itemNode.getAttributes().getNamedItem("Type").getNodeValue();
                            if ("List".equals(item.type)) {
                                item.value = "";
                                NodeList itemNodeList = itemNode.getChildNodes();
                                for (int k = 0; k < itemNodeList.getLength(); k++) {
                                    Node subItemNode = itemNodeList.item(k);
                                    if (subItemNode.getNodeType() == Node.ELEMENT_NODE) {
                                        if ("Item".equals(subItemNode.getNodeName())) {
                                            Item sitem = new Item();
                                            sitem.name = subItemNode.getAttributes().getNamedItem("Name").getNodeValue();
                                            sitem.value = subItemNode.getTextContent().trim();
                                            sitem.type = subItemNode.getAttributes().getNamedItem("Type").getNodeValue();
                                            item.addItem(sitem);
                                        }
                                    }
                                }
                            }
                            docSum.addItem(item.name,item);
                        }
                    }
                }
                //封装docSum数据
                ShowData showData = addDocSumData(docSum);
                //添加docSum
                if (showData!=null){
                    docSums.add(showData);
                }
            }
        }
        return docSums;
    }

    //封装docSum数据
    public ShowData addDocSumData(DocSum docSum){
        ShowData showData = null;
        if (docSum!=null){
            showData = new ShowData();
            //获取id
            showData.id = docSum.id;
            //摘要详情路径
            showData.abstrctURL = "https://www.ncbi.nlm.nih.gov/pubmed/"+docSum.id;
            HashMap<String, Item> items = docSum.items;
            //从items中获取docSum基本数据
            if (!items.isEmpty()){
                //获取标题
                if (items.containsKey("Title")){
                    Item itemTitle = items.get("Title");
                    showData.title = itemTitle.value;
                }
                //获取类似文章url
                if (docSum.id!=null){
                    showData.similarArticlesURL = "https://www.ncbi.nlm.nih.gov/pubmed?linkname=pubmed_pubmed&from_uid="+docSum.id;
                }
                //获取作者
                if (items.containsKey("AuthorList")){
                    Item itemAuthors = items.get("AuthorList");
                    List<Item> itemsAuthors = itemAuthors.items;
                    if (!itemsAuthors.isEmpty()){
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int m=0;m<itemsAuthors.size();m++){
                            Item autor = itemsAuthors.get(m);
                            stringBuffer.append(autor.value);
                            if (m<itemsAuthors.size()-1){
                                stringBuffer.append(",");
                            }else {
                                stringBuffer.append(".");
                            }
                        }
                        showData.authors = stringBuffer.toString();
                    }
                }
                //获取期刊号描述
                if (items.containsKey("SO")){
                    Item s = items.get("SO");
                    showData.so = s.value;
                }
                //获取doi
                if (items.containsKey("DOI")){
                    Item doi = items.get("DOI");
                    showData.doi = doi.value;
                }
                //获取来源
                if (items.containsKey("Source")){
                    Item source = items.get("Source");
                    showData.source = source.value;
                }
                //获取Epub
                if (items.containsKey("EPubDate")){
                    Item epd = items.get("EPubDate");
                    if (epd.value!=null && !epd.value.equals("")){
                        showData.ePubDate = "EPub:" + epd.value;
                    }else{
                        showData.ePubDate = "No abstract available";
                    }
                }
                //免费PMC文章
                if (items.containsKey("ArticleIds")){
                    Item aid = items.get("ArticleIds");
                    List<Item> ids = aid.items;
                    for (Item it : ids){
                        if (it.name.equals("pmc") && docSum.id!=null){
                            showData.pmcURL = "https://www.ncbi.nlm.nih.gov/pubmed/"+docSum.id;
                        }
                    }
                }
            }
        }
        return showData;
    }

    class ShowData{
        //PMID
        public String id;
        //标题
        public String title;
        //类似文章（https://www.ncbi.nlm.nih.gov/pubmed?linkname=pubmed_pubmed&from_uid=29955203）
        public String similarArticlesURL;
        //作者
        public String authors;
        //年卷期描述
        public String so;
        //doi编码
        public String doi;
        //来源
        public String source;
        //EPubDate(No abstract available) 该标签下如果没有content时，描述No ...
        public String ePubDate;
        //免费pmc文章（https://www.ncbi.nlm.nih.gov/pubmed/29955203）
        public String pmcURL;
        //文献摘要详情路径(https://www.ncbi.nlm.nih.gov/pubmed/29955203)
        public String abstrctURL;
    }

    class DocSum {
        //PMID
        public String id;
        public HashMap<String, Item> items = new HashMap();

        public void addItem(String name, Item item) {
            this.items.put(name, item);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (String key : items.keySet()) {
                sb.append(items.get(key).toString());
            }
            return sb.toString();
        }
    }

    class Item {

        public String name;
        public String value;
        public String type;
        public List<Item> items = new ArrayList();

        public void addItem(Item item) {
            this.items.add(item);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name + "," + type + "," + value + "\r\n");
            for (Item item : items) {
                sb.append("\t"+item.toString()+"\r\n");
            }
            return sb.toString();
        }
    }

}
