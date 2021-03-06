package com.hs.elsearch.dao.logDao;

import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;

import java.util.List;
import java.util.Map;

public interface ILogIndexDao {


    /**
     * 创建指定名称的索引
     * @param index 索引名称
     * @return  成功true 失败false
     */
    public boolean createIndex(String index);

    /**
     * 创建index下type的mapping属性
     * @param index 索引名
     * @param type index 的type字段，在7版本中移除
     * @param mappingproperties 字段熟悉，json格式
     * @return 返回是否创建成功true，失败false
     */
    public Boolean addMapping(String index, String type, Map<String,Object> settings, String mappingproperties) throws Exception;

    /**
     * 查询索引是否存在
     * @param index 索引名称
     * @return
     * @throws Exception
     */
    public Boolean indexExists(String index) throws Exception;


    /**
     * 删除指定index
     * @param index
     * @return
     */
    public boolean deleteByIndex(String index);

    /**
     * 查询备份仓库信息
     * @param repositories 仓库名称
     * @return
     */
    public List<Map<String, Object>> getRepositoriesInfo(String... repositories);

    /**
     * 创建备份仓库
     * @param repositoryName 仓库名称
     * @param repoPath 物理路径
     * @return
     */
    public Boolean createRepositories(String repositoryName,String repoPath);

    /**
     * 删除备份仓库
     * @param repositoryName 仓库名称
     * @return
     */
    public Boolean deleteRepositories(String repositoryName);

    /**
     * 更新inde的setting属性
     * @param index 索引名
     * @param map setting的map
     * @return
     */
    public boolean updateSettings(String index,Map<String, Object> map);

    /**
     * 合并索引
     * @param indices 需要合并的索引
     * @param maxNumSegments 合并段数
     * @param onlyExpungeDeletes 是否仅合并删除段
     * @return
     */
    public ForceMergeResponse indexForceMerge(String [] indices, int maxNumSegments, boolean onlyExpungeDeletes);

    /**
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
     * 用于开启服务前的验证，保证elasticsearch5版本的index必须存在或者elasticsearch7版本的template必须存在
     * @param indexOrTemplate 索引名称或者模板命名称(elasticsearch5版本传入index，elasticsearch7版本传入template)
     * @return
     */
    public boolean checkOfIndexOrTemplate(String... indexOrTemplate) throws Exception;

    /**
     * 初始化elasticsearch数据结构，5版本初始化当天index，7版本初始化template
     * @param indexOrTemplate
     * @param templatePattern
     * @param type
     * @param settings
     * @param mapping
     * @throws Exception
     */
    public void initOfElasticsearch(String indexOrTemplate, String templatePattern, String type, Map<String, Object> settings, String mapping) throws Exception;
}
