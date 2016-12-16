package com.demo.ds;

import java.util.List;

/**
 * @Title:事务管理器规则列表对象
 * @Description:用来映射yaml文件中的规则list
 * @author:xu.he
 * @create:2016-12-15 18:24
 * @version:v1.0
 */
public class DataSourceTransactionAttribute {

    private List<DSAttribute> list;

    public List<DSAttribute> getList() {
        return list;
    }

    public void setList(List<DSAttribute> list) {
        this.list = list;
    }

    public static class DSAttribute{
        private String matchName;

        private String rule;

        public String getMatchName() {
            return matchName;
        }

        public void setMatchName(String matchName) {
            this.matchName = matchName;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }
    }

}

