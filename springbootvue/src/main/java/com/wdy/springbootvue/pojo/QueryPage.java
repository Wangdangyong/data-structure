package com.wdy.springbootvue.pojo;

import lombok.Data;

import java.util.HashMap;

/**
 * 封装分页请求的参数
 */
@Data
public class QueryPage {
    //默认值
    private static int PAGE_SIZE=20;
    private static  int PAGE_NUM=1;
    private int pageSize=PAGE_SIZE;
    private int pageNum= PAGE_NUM;
    private HashMap param=new HashMap();
}
