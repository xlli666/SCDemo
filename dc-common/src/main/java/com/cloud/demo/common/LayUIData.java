package com.cloud.demo.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayUIData extends HashMap<String, Object> {

    final private static int CODE = 0;

    final private static String MSG = "";

    public static LayUIData tableResult(Long count, List<?> data) {
        LayUIData tableResult = new LayUIData();
        tableResult.put("code", CODE);
        tableResult.put("msg", MSG);
        tableResult.put("count", count);
        tableResult.put("data", data);
        return tableResult;
    }
    public static LayUIData formSubResult(String result) {
        LayUIData formSubmitResult = new LayUIData();
        formSubmitResult.put("code",CODE);
        formSubmitResult.put("msg",MSG);
        formSubmitResult.put("data",result);
        return formSubmitResult;
    }
    public static LayUIData uploadResult(String imgUrl) {
        Map<String, String> srcMap = new HashMap<>();
        srcMap.put("src",imgUrl);
        LayUIData uploadResult = new LayUIData();
        uploadResult.put("code", CODE);
        uploadResult.put("msg",MSG);
        uploadResult.put("data",srcMap);
        return uploadResult;
    }
    public static LayUIData failure(int code, String msg) {
        LayUIData failureInfo = new LayUIData();
        failureInfo.put("code", code);
        failureInfo.put("msg", msg);
        return failureInfo;
    }
}
