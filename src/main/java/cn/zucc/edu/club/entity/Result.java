package cn.zucc.edu.club.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    //200是正常，非200表示异常
    private Integer code;
    // 结果消息
    private String msg;
    //结果数据
    private Object data;


    //调用正常时返回
    public static Result success(Integer code,String msg,Object data){
        return new Result(code,msg,data);
    }
    public static Result success(Integer code,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        return result;
    }
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    //异常时返回
    public static Result fail(Integer code,String msg,Object data){
        return new Result(code,msg,data);
    }
    public static Result fail(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
    public static Result fail(String msg){
        Result result = new Result();
        result.setCode(400);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

}

