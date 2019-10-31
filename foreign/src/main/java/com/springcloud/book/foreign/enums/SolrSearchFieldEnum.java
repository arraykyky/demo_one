package com.springcloud.book.foreign.enums;
/**
 * @author grl
 * @since 2018-08-05
 */
public enum SolrSearchFieldEnum {
    /******************文献*******************/
    //相关度
    SCORE(-1,"SCORE","score"),
    //标题与文摘
    TITLE_TEXT(0,"TITLEANDTEXT","title_text"),
    //标题
    TITLE(1,"TITLE","article_title"),
    //文摘(摘要)
    ARTICLE_TITLE(2,"TEXT","abstract_text"),
    //作者/第一作者/最末作者
    AUTHORS_STR(3,"AUTHOR","authors_string"),
    //第一作者
    FIRST_AUTHOR(4,"FIRSTAUTHOR","authors_string"),
    //最末作者
    FINAL_AUTHOR(5,"FINALAUTHOR","authors_string"),
    //作者单位
    AFFILIATION_STR(6,"AFFILIATION","affiliation_string"),
    //期刊或ISSN
    JOURNAL_ISSN(7,"JOURNALORISSN","journal_issn"),
    //期刊
    JOURNAL(8,"JOURNALTITLE","journal_title_main"),
    //issn
    ISSN(9,"ISSN","ISSN"),
    //主题词 -- 支持中英文检索
    MESH_HEADS(10,"MESHHEADS","mesh_heads_en_ch"),
    //pmid
    PMID(11,"PMID","PMID"),
    //影响因子
    RECENT_IF(12,"IF","recent_IF"),
    //年
    MEDLINE_DATE(13,"YEAR","medline_date"),
    //卷
    VOLUME(14,"VOLUME","volume"),
    //期
    ISSUE(15,"ISSUE","issue"),
    //页码
    MEDLINE_PAGE(16,"PAGE","medline_page"),
    //语种(语言)
    LANGUAGE(17,"LANGUAGE","language"),
    //DOI
    DOI(18,"DOI","elocation_id_DOI"),
    //国籍(出版地)
    COUNTRY(19,"COUNTRY","country"),
    //出版类型
    PUBLICATION_TYPES(20,"PUBLICATIONTYPES","publication_types_str"),
    //有免费全文
    HAD_FREE_FULL_TEXT(21,"FREETEXT","had_free_full_text"),
    //有全文
    HAD_FULL_TEXT(22,"HADTEXT","had_full_text"),
    //收录
    JOURNAL_INDEXES(23,"INDEXES","journal_indexes_str"),
    //副主题词
    QUALIFIER(24,"QUALIFIER","qualifier_string"),
    //文献id
    ABSTRACTID(25,"ABSTRACTID","abstract_id"),
    //文献对应期刊id
    ABJOURNALID(26,"JOURNALID","journal_id"),
    //关键词
    ABSTRACT_AUTHORS(27,"KEYWORDS","key_words_str"),
    //英文主题词
    MESH_HEADS_EN(28,"MESHHEADSEN","mesh_heads_str"),
    //中文主题词
    MESH_HEADS_CH(29,"MESHHEADSCH","mesh_heads_ch_str"),
    //英文主题词--聚合数组
    MESH_HEADS_GROUP(30,"MESHHEADSG","mesh_heads"),
    //文献类型（出版类型）--聚合数组
    ABSTRACT_TYPES(31,"ABSTRACTTYPES","publication_types"),
    //全部 ISO标题简写、摘要、单位、标题、作者、期刊标题、issn号、关键词、出处、主题词、限定词、出版类型
    TEXT(99,"ALL","text"),
    /*********************期刊********************/
    JOURNALID(100,"JOURNALID","journal_id"),
    JOURNALISSN(101,"JOURNALISSN","lssn"),
    MESHHEADINGS(102,"MESHHEADINGS","mesh_headings"),
    TITLEMAINCOPY(103,"SUBJECTCLASSIFICATION","subject_classification"),
    JOURNALLANGUAGE(104,"LANGUAGE","language"),
    INDEXES(105,"INDEXES","indexes"),
    RECENTIF(106,"RECENTIF","recent_If"),
    FIRSTTITLEMAIN(107,"FIRSTTITLEMAIN","title_initial_copy"),//期刊首字母
    TITLEMAIN(108,"TITLEMAIN","title_main"),
    TITLECH(109,"TITLECH","title_ch"),
    NLMTITLEABBREVIATION(110,"NLMTITLEABBREVIATION","nlm_title_abbreviation"),
    ;
    private final int key;
    private final String value;
    private final String tableField;

    private SolrSearchFieldEnum(int key, String value, String tableField){
        this.key = key;
        this.value = value;
        this.tableField = tableField;
    }

    /**
     * 通过枚举key获取Value
     * @param intKey
     * @return
     */
    public static String getEnumValueByKey(int intKey){
        for (SolrSearchFieldEnum sf : SolrSearchFieldEnum.values()){
            if (sf.getKey() == intKey){
                return sf.getValue();
            }
        }
        return null;
    }

    /**
     * 通过key获取tablefield
     * @param intKey
     * @return
     */
    public static String getTableFieldByKey(int intKey)throws Exception{
        String field = null;
        for (SolrSearchFieldEnum sf : SolrSearchFieldEnum.values()){
            if (sf.getKey() == intKey){
                field = sf.getTableField();
            }
        }
        if (field == null){
            throw new RuntimeException("Your value is wrong");
        }
        return field;
    }

    /**
     * 通过value获取tablefield
     * @param value
     * @return
     */
    public static String getEnumTableFieldByValue(String value){
        for (SolrSearchFieldEnum sf : SolrSearchFieldEnum.values()){
            if (sf.getValue().equals(value)){
                return sf.getTableField();
            }
        }
        return null;
    }

    /**
     * 通过value获取key
     * @param value
     * @return
     */
    public static int getEnumKeyByValue(String value){
        for (SolrSearchFieldEnum sf : SolrSearchFieldEnum.values()){
            if (sf.getValue().equals(value)){
                return sf.getKey();
            }
        }
        return -1;
    }


    public static void main(String[] args) throws Exception{
        String value = SolrSearchFieldEnum.getTableFieldByKey(0);
        System.out.println(value);
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getTableField() {
        return tableField;
    }
}
