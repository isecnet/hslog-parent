package com.hs.elsearch.dao.biDao.entity;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.util.Map;

/**
 * 图表查询时的参数，用于统一管理维护
 */
public class VisualParam {
    private String x_agg;//x轴聚合方式
    private String x_field;//x轴聚合字段
    private String y_agg;//y轴聚合方式
    private String y_field;//y轴聚合字段
    private int size = 10;//查询结果条数，默认10条
    private String sort;//排序，正序/倒序
    private IntervalType intervalType;//间隔类型 enum
    private int intervalValue;//间隔值
    private String indexName;//数据源：目前是索引，后期可能会调整
    private String startTime;
    private String endTime;
    public enum IntervalType{
        SECOND,//秒
        MINUTE,//分钟
        HOURLY,//小时
        DAILY,//天
        WEEKLY,//周
        MONTHLY,//月
        YEARLY,//年
        UNKNOWN;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public IntervalType getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(IntervalType intervalType) {
        this.intervalType = intervalType;
    }

    public int getIntervalValue() {
        return intervalValue;
    }

    public void setIntervalValue(int intervalValue) {
        this.intervalValue = intervalValue;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getX_agg() {
        return x_agg;
    }

    public void setX_agg(String x_agg) {
        this.x_agg = x_agg;
    }

    public String getX_field() {
        return x_field;
    }

    public void setX_field(String x_field) {
        this.x_field = x_field;
    }

    public String getY_agg() {
        return y_agg;
    }

    public void setY_agg(String y_agg) {
        this.y_agg = y_agg;
    }

    public String getY_field() {
        return y_field;
    }

    public void setY_field(String y_field) {
        this.y_field = y_field;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     *  map转bean
     * @param map 参数map
     * @return
     */
    public VisualParam mapToBean(Map<String,String[]> map){
        //将枚举类的对应进行注册。
        ConvertUtils.register(new Converter() {
            public Object convert(Class type, Object value) {
                if(!"".equals(value)){
                    return IntervalType.valueOf(((String)value).toUpperCase());
                }else{
                    return null;
                }
            }
        }, IntervalType.class);
        try{
            BeanUtils.populate(this,map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return this;
    }

}