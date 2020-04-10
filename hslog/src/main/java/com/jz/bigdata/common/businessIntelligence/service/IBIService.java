package com.jz.bigdata.common.businessIntelligence.service;

import com.hs.elsearch.dao.biDao.entity.VisualParam;
import com.jz.bigdata.common.businessIntelligence.entity.MappingField;
import com.jz.bigdata.common.businessIntelligence.entity.Visualization;
import org.elasticsearch.action.DocWriteResponse;

import java.util.List;

public interface IBIService {

    /**
     * 通过X轴的聚合方式获取对应的field
     * @param templateName
     * @param indexName
     * @param agg 聚合方式
     * @return
     */
    public List<MappingField> getFieldByXAxisAggregation(String templateName, String indexName,String agg) throws Exception;
    /**
     * 通过Y轴的聚合方式获取对应的field
     * @param templateName
     * @param indexName
     * @param agg 聚合方式
     * @return
     */
    public List<MappingField> getFieldByYAxisAggregation(String templateName, String indexName,String agg) throws Exception;

    /**
     * 实现类sql的group by并进行sum求和计算的功能,包含时间范围、条件等
     * @param indices 索引或template名称
     * @param groupByField 需要进行聚合的字段
     * @param groupByFieldType 需要进行聚合方式（terms/DataHistogram）
     * @param sumField 聚合分组后进行sum计算的字段
     * @param size 聚合结果最大返回数
     * @param sort 排序
     * @param starttime 时间范围-开始时间
     * @param endtime 时间范围-结束时间
     * @param map 其他限制条件
     * @return
     */
    public String groupByThenSum(VisualParam params) throws Exception;
    /**
     * 实现类sql的group by并进行sum求和计算的功能,包含时间范围、条件等
     * @param indices 索引或template名称
     * @param groupByField 需要进行聚合的字段
     * @param groupByFieldType 需要进行聚合方式（terms/DataHistogram）
     * @param sumField 聚合分组后进行sum计算的字段
     * @param size 聚合结果最大返回数
     * @param sort 排序
     * @param starttime 时间范围-开始时间
     * @param endtime 时间范围-结束时间
     * @param map 其他限制条件
     * @return
     */
    public String groupByThenCount(VisualParam params) throws Exception;
    /**
     * 实现类sql的group by并进行sum求和计算的功能,包含时间范围、条件等
     * @param indices 索引或template名称
     * @param groupByField 需要进行聚合的字段
     * @param groupByFieldType 需要进行聚合方式（terms/DataHistogram）
     * @param sumField 聚合分组后进行sum计算的字段
     * @param size 聚合结果最大返回数
     * @param sort 排序
     * @param starttime 时间范围-开始时间
     * @param endtime 时间范围-结束时间
     * @param map 其他限制条件
     * @return
     */
    public String groupByThenAvg(VisualParam params) throws Exception;
    /**
     * 实现类sql的group by并进行sum求和计算的功能,包含时间范围、条件等
     * @param indices 索引或template名称
     * @param groupByField 需要进行聚合的字段
     * @param groupByFieldType 需要进行聚合方式（terms/DataHistogram）
     * @param sumField 聚合分组后进行sum计算的字段
     * @param size 聚合结果最大返回数
     * @param sort 排序
     * @param starttime 时间范围-开始时间
     * @param endtime 时间范围-结束时间
     * @param map 其他限制条件
     * @return
     */
    public String groupByThenMax(VisualParam params) throws Exception;
    /**
     * 实现类sql的group by并进行sum求和计算的功能,包含时间范围、条件等
     * @param indices 索引或template名称
     * @param groupByField 需要进行聚合的字段
     * @param groupByFieldType 需要进行聚合方式（terms/DataHistogram）
     * @param sumField 聚合分组后进行sum计算的字段
     * @param size 聚合结果最大返回数
     * @param sort 排序
     * @param starttime 时间范围-开始时间
     * @param endtime 时间范围-结束时间
     * @param map 其他限制条件
     * @return
     */
    public String groupByThenMin(VisualParam params) throws Exception;

    /**
     * 保存图表信息
     * @param visual 图表信息bean
     * @return
     */
    public DocWriteResponse.Result saveVisualization(Visualization visual , String indexName) throws Exception;
    /**
     * 获取图表列表，不包含option和params信息（数据内容太多，影响响应速度）
     * @return
     */
    public String getVisualizations(String indexName) throws Exception;

    /**
     * 根据id获取图表详情以及查询的数据结果
     * @param id
     * @return
     */
    public String getVisualizationById(String id,String indexName) throws Exception;
    /**
     * 根据id删除图表
     * @param id
     * @return
     */
    public String deleteVisualizationById(String id,String indexName) throws Exception;

    /**
     * 查看该标题的图表是否存在
     * @param title 标题
     * @param indexName 索引
     * @return
     * @throws Exception
     */
    public boolean isVisualizationExists(String title,String indexName) throws Exception;
}
