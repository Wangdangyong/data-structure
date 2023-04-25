package com.wdy.springbootvue.pojo;

import lombok.Data;

/**
 * 让前端收到统一的数据
 */
@Data
public class Result {
    private int code;//编码200 400
    private String msg;//成功或失败
    private Long total;//总记录数
    private Object data;//数据
    public static Result result(int code,String msg,Long total,Object data){
        Result res=new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }
   public static Result fall(){
        return result(400,"失败",0L,null);
    }
    public static Result sucess(){
        return result(200,"成功",0L,null);
    }
    public static Result sucess(Object data){
        return result(200,"成功",0L,data);
    }
    public static Result sucess(Object data,Long total){
        return result(200,"成功",total,data);
    }





















}
