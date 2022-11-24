package com.qb.springboot.webhomework.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("通用返回类")
public class Result {

    @ApiModelProperty("识别代码， 100成功， 250失败")
    private String code;

    @ApiModelProperty("错误信息")
    private String msg;

    @ApiModelProperty("携带数据")
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_100, "", null);
    }
    public static Result success(Object data){
        return new Result(Constants.CODE_100, "", data);
    }
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }
}
