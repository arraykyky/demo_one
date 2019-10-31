package com.springcloud.book.foreign.enums;

public enum RoleCodeKeyEnum {
    //超级管理员				role_number=CJZGLY			    key=1	借读点充值
    //外文本地管理员			role_number=LOCALGLY			key=-1	借读点分配
    //外文院内管理员			role_number= HOSPITALGLY		key=2	借读点分配
    //馆际互借管理员	        role_number=CLOUD		        key=3
    //普通用户	                role_number= PTYH		        key=4
    //测试角色	                role_number= TESTJS		        key=5
    R_SUPER("CJZGLY","1"),
    R_LOCALGLY("LOCALGLY","-1"),
    R_HOSPITALGLY("HOSPITALGLY","2"),
    R_CLOUD("CLOUD","3"),
    R_PTYH("PTYH","4"),
    R_TESTJS("TESTJS","5"),
    ;
    private String code;
    private String num;

    private RoleCodeKeyEnum(String code, String num) {
        this.code = code;
        this.num = num;
    }

    /**
     * 通过请求code获取描述num
     * @param code
     * @return
     */
    public static String getNumByCode(String code){
        for (RoleCodeKeyEnum r : RoleCodeKeyEnum.values()){
            if (r.getCode().equals(code)){
                return r.getNum();
            }
        }
        return "4";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
