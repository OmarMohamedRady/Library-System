package com.library.librarysystem.util.datawrapper;

import java.util.List;

public class DataWrapperUtils {


    public static <E> DataWrapper<E> getWrapper(E pojo , DataWrapper<E> dw) {
        dw.setData(pojo);
        if(dw.getMessage()==null) {
            dw.setMessage(DataWrapperUtils.getMessage(pojo));
        }
        dw.setSuccess(DataWrapperUtils.getSuccess(pojo));
        dw.setTotalCount(1);
        return dw;
    }

    public static <E> DataWrapper<E> getWrapper(E pojo , DataWrapper<E> dw, Integer totalCount) {
        dw.setData(pojo);
        if(dw.getMessage()==null) {
            dw.setMessage(DataWrapperUtils.getMessage(pojo));
        }
        dw.setSuccess(DataWrapperUtils.getSuccess(pojo));
        dw.setTotalCount(totalCount);
        return dw;
    }

    public static <E> DataWrapper<E> getWrapperError(String errorMsg , DataWrapper<E> dw) {
        dw.setData(null);
        if(dw.getMessage()==null) {
            dw.setMessage(errorMsg);
        }
        dw.setSuccess(false);
        dw.setTotalCount(1);
        return dw;
    }

    public static String getMessage(Object pojo) {
        if(pojo == null) {
            return "Null Object Retrieved.";
        }else {
            return "Data retrieved successfully.";
        }
    }

    public static boolean getSuccess(Object pojo) {
        return pojo != null;
    }

}

