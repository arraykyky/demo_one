package com.springcloud.book.foreign.util.pubmed;

import com.alibaba.fastjson.JSON;
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
import java.util.Map;

public class AbstractMXLParser {

    public Map<String,String> parse(String xml) throws Exception {
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
        PubMedAbstract pma = new PubMedAbstract();
        for (int i = 0; i < nodeList.getLength(); i++){
            Node paNode = nodeList.item(i);
            if ("PubmedArticle".equals(paNode.getNodeName())){
                NodeList paNodeList = paNode.getChildNodes();
                for (int j = 0; j < paNodeList.getLength(); j++){
                    Node mcOrPdNode = paNodeList.item(j);
                    if ("MedlineCitation".equals(mcOrPdNode.getNodeName())){
                        NodeList mcNodeList = mcOrPdNode.getChildNodes();
                        for (int m=0;m<mcNodeList.getLength();m++){
                            Node mcNode = mcNodeList.item(m);
                            if ("PMID".equals(mcNode.getNodeName())){
                                pma.pmid = mcNode.getTextContent().trim();
                            }
                            if ("Article".equals(mcNode.getNodeName())){
                                NodeList aNodeList = mcNode.getChildNodes();
                                for (int n =0;n<aNodeList.getLength();n++){
                                    Node articleNode = aNodeList.item(n);
                                    if ("Journal".equals(articleNode.getNodeName())){
                                        NodeList jNodeList = articleNode.getChildNodes();
                                        for (int x = 0;x<jNodeList.getLength();x++){
                                            Node jNode = jNodeList.item(x);
                                            if ("JournalIssue".equals(jNode.getNodeName())){
                                                NodeList jiNodeList = jNode.getChildNodes();
                                                for (int q=0;q<jiNodeList.getLength();q++){
                                                    Node jiNode = jiNodeList.item(q);
                                                    if ("Volume".equals(jiNode.getNodeName())){
                                                        pma.volume = jiNode.getTextContent().trim();
                                                    }
                                                    if ("Issue".equals(jiNode.getNodeName())){
                                                        pma.issue = jiNode.getTextContent().trim();
                                                    }
                                                    if ("PubDate".equals(jiNode.getNodeName())){
                                                        NodeList pNodeList = jiNode.getChildNodes();
                                                        for (int s =0;s<pNodeList.getLength();s++){
                                                            Node pNode = pNodeList.item(s);
                                                            if (pNode.getNodeType()==Node.ELEMENT_NODE){
                                                                if ("Year".equals(pNode.getNodeName())){
                                                                    pma.medlineDate = pNode.getTextContent();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if ("ArticleTitle".equals(articleNode.getNodeName())){
                                        pma.articleTitle = articleNode.getTextContent();
                                    }
                                    if ("Pagination".equals(articleNode.getNodeName())){
                                        NodeList pNodeList = articleNode.getChildNodes();
                                        for (int p=0;p<pNodeList.getLength();p++){
                                            Node pNode = pNodeList.item(p);
                                            if ("MedlinePgn".equals(pNode.getNodeName())){
                                                pma.medlinePage = pNode.getTextContent();
                                            }
                                        }
                                    }
                                    if ("AbstractSolr".equals(articleNode.getNodeName())){
                                        NodeList atNodeList = articleNode.getChildNodes();
                                        for (int at=0;at<atNodeList.getLength();at++){
                                            Node atNode = atNodeList.item(at);
                                            if ("AbstractText".equals(atNode.getNodeName())){
                                                pma.abstractText = atNode.getTextContent();
                                            }
                                        }
                                    }
                                    if ("AuthorList".equals(articleNode.getNodeName())){
                                        List<Map> list = new ArrayList();
                                        NodeList auNodeList = articleNode.getChildNodes();
                                        for (int au=0;au<auNodeList.getLength();au++){
                                            Node auNode = auNodeList.item(au);
                                            if ("Author".equals(auNode.getNodeName())){
                                                Map<String,String> map = new HashMap();
                                                NodeList auNameNode = auNode.getChildNodes();
                                                for (int aun =0;aun<auNameNode.getLength();aun++){
                                                    Node nameNode = auNameNode.item(aun);
                                                    if ("LastName".equals(nameNode.getNodeName())){
                                                        map.put("lastName",nameNode.getTextContent());
                                                    }
                                                    if ("ForeName".equals(nameNode.getNodeName())){
                                                        map.put("foreName",nameNode.getTextContent());
                                                    }
                                                }
                                                list.add(map);
                                            }
                                        }
                                        pma.abstractAuthors = list.toString();
                                    }
                                    if ("Language".equals(articleNode.getNodeName())){
                                        pma.language = articleNode.getTextContent();
                                    }
                                }
                            }
                            if ("MedlineJournalInfo".equals(mcNode.getNodeName())){
                                NodeList mjNodeList = mcNode.getChildNodes();
                                for (int mj=0;mj<mjNodeList.getLength();mj++){
                                    Node mNode = mjNodeList.item(mj);
                                    if ("Country".equals(mNode.getNodeName())){
                                        pma.country = mNode.getTextContent();
                                    }
                                    if ("MedlineTA".equals(mNode.getNodeName())){
                                        pma.medlineTa = mNode.getTextContent();
                                    }
                                }
                            }
                            if ("KeywordList".equals(mcNode.getNodeName())){
                                NodeList kwNodeList = mcNode.getChildNodes();
                                StringBuffer stringBuffer = new StringBuffer();
                                for (int kw=0;kw<kwNodeList.getLength();kw++){
                                    Node kwNode = kwNodeList.item(kw);
                                    if ("Keyword".equals(kwNode.getNodeName())){
                                        stringBuffer.append(kwNode.getTextContent());
                                        if (kw < kwNodeList.getLength()-1){
                                            stringBuffer.append(",");
                                        }
                                    }
                                }
                                pma.keyWords = stringBuffer.toString();
                            }
                        }
                    }
                    if ("PubmedData".equals(mcOrPdNode.getNodeName())){
                        NodeList pdNodeList = mcOrPdNode.getChildNodes();
                        for (int pd=0;pd<pdNodeList.getLength();pd++){
                            Node pdNode = pdNodeList.item(pd);
                            if ("ArticleIdList".equals(pdNode.getNodeName())){
                                NodeList aidNodeList = pdNode.getChildNodes();
                                for (int ai=0;ai<aidNodeList.getLength();ai++){
                                    Node articId = aidNodeList.item(ai);
                                    if ("ArticleId".equals(articId.getNodeName())){
                                        String idType = articId.getAttributes().getNamedItem("IdType").getNodeValue().trim();
                                        if (idType.equals("pubmed")){
                                            pma.europePubMedCentral = "https://www.ncbi.nlm.nih.gov/pmc/articles/pmid/"+articId.getTextContent().trim();
                                        }
                                        if (idType.equals("doi")){
                                            pma.thridLink = "https://sci-hub.tw/" + articId.getTextContent().trim();
                                        }
                                        if (idType.equals("pii")){
                                            pma.elsevierScience = "https://linkinghub.elsevier.com/retrieve/pii/"+articId.getTextContent().trim();
                                        }
                                        if (idType.equals("pmc")){
                                            pma.pubMedCentral = "https://www.ncbi.nlm.nih.gov/pmc/articles/"+articId.getTextContent().trim();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return JSON.parseObject(JSON.toJSONString(pma),HashMap.class);
    }

    class PubMedAbstract{
        public String articleTitle;
        public String abstractAuthors;
        public String medlineTa;
        public String medlineDate;
        public String volume;
        public String issue;
        public String issn;
        public String medlinePage;
        public String country;
        public String language;
        public String abstractText;
        //https://linkinghub.elsevier.com/retrieve/pii/S0166-0616(18)30027-7(pii)
        public String elsevierScience;

        //http://europepmc.org/abstract/MED/29955203
        public String europePubMedCentral;

        //https://www.ncbi.nlm.nih.gov/pmc/articles/PMC6020082/
        public String pubMedCentral;

        //https://sci-hub.tw/10.1016/j.simyco.2018.05.001(doi)
        public String thridLink;
        public String pmid;
        public String keyWords;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
