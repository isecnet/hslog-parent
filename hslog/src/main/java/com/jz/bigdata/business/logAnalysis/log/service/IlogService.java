package com.jz.bigdata.business.logAnalysis.log.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;

public interface IlogService {

	public List<Map<String, Object>> index(String index,String type) ;

	/**
	 * 创建elasticsearch的index
	 * @param index 索引名称
	 */
	public void createIndex(String index);

	/**
	 * 判断index是否存在
	 * @param index 索引名称
	 * @return 存在返回true，不存在false
	 */
	public boolean indexExists(String index);

	/**
	 * 创建elasticsearch的index和type，并配置mapping
	 * @param index 索引名称
	 * @param type 索引类型（7版本删除）
	 * @param mappingproperties 字段属性
	 */
	public void createIndexAndmapping(String index, String type, String mappingproperties);

	/**
	 *
	 * 用于初始化操作创建index的模板
	 * @param tempalateName 模板名称
	 * @param tempalatePattern index名称通配
	 * @param settings 针对index模板的具体属性设置，包括分片数、副本数、最大查询值等，其中分片数设置之后是不能再修改的
	 * @param type 在5.4版本中index的template需要指明mapping对应的type，在7版本后只需要指定mapping
	 * @param mapping 字段属性
	 * @return 创建成功返回true，失败false
	 */
	public boolean createTemplateOfIndex(String tempalateName, String tempalatePattern, Map<String,Object> settings,String type, String mapping) throws Exception;

	/**
	 * 向elasticsearch新建索引数据
	 * @param index
	 * @param type
	 * @param json
	 */
	public void insert(String index,String type,String json);

	/**
	 * 获取索引数据量通过条件
	 * @param index
	 * @param types
	 * @param map
	 * @return
	 */
	public long getCount(String index,String [] types,Map<String, String> map);
	public long getCount(Map<String, String> map, String starttime,String endtime, String[] types, String... indices);

	/**
	 * 实现类sql的group by功能,包含时间范围、条件等
	 * @param index 索引名称
	 * @param types index 的 type字段，在7版本中移除
	 * @param groupByField 需要进行聚合的字段
	 * @param starttime 时间范围-开始时间
	 * @param endtime 时间范围-结束时间
	 * @param map 其他限制条件
	 * @return
	 */
	public List<Map<String, Object>> groupBy(String index,String[] types,String groupByField, String starttime, String endtime,Map<String, String> map);
	/**
	 * 实现类sql的group by功能
	 * @param index
	 * @param types
	 * @param groupByField groupby的key值
	 * @param map 条件参数
	 * @return
	 */
	public List<Map<String, Object>> groupBy(String index,String[] types,String groupByField,Map<String, String> map);

	/**
	 * 实现类sql的group by功能
	 * @param index
	 * @param types
	 * @param param groupby的key值
	 * @param size 设置es group by返回的数据条数，es默认是10条
	 * @param map 条件参数
	 * @return
	 */
	public List<Map<String, Object>> groupBy(String index, String[] types, String param, Map<String, String> map, int size);

	/**
	 * 实现类sql的group by功能
	 * @param index
	 * @param types
	 * @param param groupby的key值
	 * @param size 设置es group by返回的数据条数，es默认是10条
	 * @param map 条件参数
	 * @return
	 */
	public List<Map<String, Object>> groupBy(String index, String[] types, String[] param, Map<String, String> map, int size);

	/**
	 * 分页排序
	 * @param index
	 * @param type
	 * @param param
	 * @param order
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> orderBy(String index,String type,String param,String order,String page,String size) ;

	/*/**
	 * 根据设备id查询索引数据
	 * @param index
	 * @param type
	 * @param param
	 * @param order
	 * @param equipmentId
	 * @param page
	 * @param size
	 * @return
	 */
	/*public List<Map<String, Object>> getLogListByEquipmentId(String index,String type,String param,String order,String equipmentId,String page,String size) ;*/

	public void update();

	/**
	 * 通过ID查询索引数据
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public String searchById(String index,String type,String id);

	/**
	 * 全文检索
	 * @param index
	 * @param types
	 * @param content
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getListByContent(String index,String[] types,String content,String page,String size);
	public List<Map<String, Object>> getListByContent(String content,String userid,String page,String size,String[] types,String... indices);
	/**
	 * 全文检索
	 * @param index
	 * @param types
	 * @param content
	 * @param userid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getListByContent(String index,String[] types,String content,String userid,String page,String size);

	/**
	 * @param index
	 * @param types
	 * @param param
	 * @param equipmentid
	 * TO DO 获取资产各个时段的日志数据
	 * @return
	 */
	public List<Map<String, Object>> getListGroupByTime(String index,String[] types,String param,String equipmentid);

	/**
	 * 根据时间统计单个资产的各个事件级别数量
	 * @param index
	 * @param types
	 * @param dates
	 * @param equipmentid
	 * @param eventtype
	 * @param i
	 * @return
	 */
	public List<Map<String, Object>> getEventListGroupByTime(String index,String[] types,String dates,String equipmentid,String eventtype,int i);

	/**
	 * 通过map构建的查询条件进行数据查询
	 * @param map 查询条件
	 * @param starttime 时间范围-开始时间
	 * @param endtime  时间范围-结束时间
	 * @param types index type字段，在7版本中移除
	 * @param indices 索引名称
	 * @return 返回符合查询条件的日志内容
	 */
	public List<Map<String, Object>> getListByMap(Map<String, String> map, String starttime, String endtime, String[] types,String... indices);

	/**
	 * 通过map构建的查询条件进行数据查询
	 * @param map 查询条件
	 * @param starttime 时间范围-开始时间
	 * @param endtime  时间范围-结束时间
	 * @param page 页码
	 * @param size 单页显示条数
	 * @param types index type字段，在7版本中移除
	 * @param indices 索引名称
	 * @return 返回符合查询条件的日志内容
	 */
	public List<Map<String, Object>> getListByMap(Map<String, String> map, String starttime, String endtime, String page, String size, String[] types,String... indices);

	/**
	 * getListByBlend重载
	 * 通过遍历map中的查询条件进行查询
	 * @param logindex
	 * @param types
	 * @param map
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getListByBlend(String logindex, String[] types, Map<String, String> map,String page,String size);

	/**
	 * getListByBlend重载userid
	 * 通过遍历map中的查询条件进行查询
	 * @param index
	 * @param types
	 * @param pamap
	 * @param userid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getListByBlend(String index, String[] types, Map<String, String> pamap, String userid,String page,String size);

	/**
	 * 通过map查询数据
	 * @param index
	 * @param types
	 * @param starttime
	 * @param endtime
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getListByMap(String index, String[] types, String starttime, String endtime,Map<String, String> map);

	/**
	 * 通过时间段+map
	 * @param index
	 * @param types
	 * @param starttime
	 * @param endtime
	 * @param map
	 * @param page
	 * @param size
	 * @return
	 */
	/*public List<Map<String, Object>> getListByMap(String index, String[] types, String starttime, String endtime,Map<String, String> map,String page,String size);*/

	/**
	 * 通过时间段+map+userid
	 * @param index
	 * @param types
	 * @param starttime
	 * @param endtime
	 * @param map
	 * @param userid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getListByMap(String index, String[] types, String starttime, String endtime,Map<String, String> map, String userid, String page, String size);

	/**
	 * 通过ID删除索引数据
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public String deleteById(String index, String type, String id);

	/**
	 * 根据时间统计单个资产的事件数量
	 * @param index
	 * @param types
	 * @param dates
	 * @param equipmentid
	 * @param groupby
	 * @return
	 */
	public List<Map<String, Object>> getEventListGroupByEventType(String index, String[] types, String dates, String equipmentid,
																  String groupby);

	/**
	 * 统计某个时间段内单个资产的事件类型
	 * @param index
	 * @param types
	 * @param equipmentid
	 * @param event_type
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	List<Map<String, Object>> getListGroupByEvent(String index, String[] types, String equipmentid, String event_type,
												  String starttime, String endtime);

	/**
	 * 统计客户自定义的安全策略数据，实现安全告警
	 * @param index
	 * @param types
	 * @param equipmentid
	 * @param enddate
	 * @return
	 */
	List<Map<String, Object>> getEventstypeCountByEquipmentid(String index, String[] types, String equipmentid, Date enddate);

	/**
	 * 构建multiField查询
	 * @param index
	 * @param types
	 * @param multifield map中的 key是查询内容，value是需要查询的字段，value为String数组
	 * @param param 其他条件参数
	 * @param page
	 * @param size
	 * @return
	 */
	List<Map<String, Object>> getListByMultiField(String index, String[] types, Map<String, String[]> multifield, Map<String, String> param, String page, String size);

	/**
	 *
	 * @param indices
	 * @param type
	 * @param map 查询条件
	 * @return 删除数
	 *
	 * 通过查询数据实现批量删除
	 */
	public long deleteByQuery(String[] indices,String type,Map<String, String> map) ;

	/**
	 *
	 * @param indices
	 * @return
	 *
	 * 实现indices合并
	 */
	public ForceMergeResponse indexForceMerge(String[] indices);

	/**
	 *
	 * @param indices
	 * @return
	 *
	 * 实现indices中对已删除segments的合并达到释放存储空间的作用
	 */
	public ForceMergeResponse indexForceMergeForDelete(String[] indices);

	/**
	 * 实现删除数据后强制合并 删除段释放存储空间
	 */
	public void deleteAndForcemerge(String[] indices, String type, Map<String, String> map) ;

	/**
	 * 创建备份仓库
	 * @param repositoryName 备份仓库名称
	 * @param repoPath 备份仓库路径
	 * @return
	 */
	public boolean createRepositories(String repositoryName,String repoPath) ;

	/**
	 * 获取备份仓库信息
	 * @param repositoryName
	 * @return
	 */
	public List<Map<String, Object>> getRepositoriesInfo(String ... repositoryName);

	/**
	 * 删除备份仓库
	 * @param repositoryName
	 * @return
	 */
	public boolean deleteRepositories(String repositoryName) ;

	/**
	 * 更新index的setting属性
	 * @param index
	 * @param map
	 * @return
	 */
	public boolean updateSettings(String index,Map<String, Object> map) ;

	/**
	 * 定时任务：创建
	 * @return
	 */
	public boolean createIndexRegularly();

}
