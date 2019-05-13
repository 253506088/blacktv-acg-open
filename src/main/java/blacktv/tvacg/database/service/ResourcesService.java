package blacktv.tvacg.database.service;

import blacktv.tvacg.database.mapper.ResourcesMapper;
import blacktv.tvacg.database.mapper.ResourcesTypeMapper;
import blacktv.tvacg.database.mapper.SequenceMapper;
import blacktv.tvacg.database.pojo.Resources;
import blacktv.tvacg.database.pojo.ResourcesType;
import blacktv.tvacg.database.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 资源与资源分类的业务逻辑类
 */
@Service
@Log4j
public class ResourcesService {
    @Autowired
    private ResourcesTypeMapper resourcesTypeMapper;
    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private LinkSole linkSole;
    @Value("${tvacg.page.pageSize}")
    private Integer pageSize;
    @Value("${tvacg.sequence.resources}")
    private String sequenceName;

    /**
     * 新增资源,只有通过审核后才保存到solr中
     *
     * @param resources
     * @return
     */
    @Transactional
    public boolean addResources(Resources resources) throws Exception {
        //先获取id
        resources.setId(this.sequenceMapper.getNextVal(sequenceName));
        //保存到数据库
        if (this.resourcesMapper.addResources(resources) <= 0)
            throw new Exception("新增资源到数据库失败，抛出异常回滚事务。");
        return true;
    }

    /**
     * 设置指定资源的删除状态。如果不需要验证，就不用传入User和request
     *
     * @param resources
     * @param user
     * @param request
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Transactional
    public boolean setIsDelete(Resources resources, User user, HttpServletRequest request) throws IOException, SolrServerException {
        Byte isDlete = resources.getIsDlete();
        //根据id获取资源全部信息,不明白为什么要这样做去看一下LinkSole的addResources方法就懂了
        resources = resourcesMapper.getResourcesById(resources.getId());
        if (user != null && request != null) {
            if (user.getId() != resources.getUserId()) {
                log.info("ip:" + request.getRemoteAddr() + " 试图用id为: " + user.getId() + " 修改投稿者id为: " + resources.getUserId() + " 的稿件，稿件id为: " + resources.getId());
                return false;
            }
            //用户无权设置稿件的删除状态为1
            if (isDlete == 1) {
                log.info("ip:" + request.getRemoteAddr() + " 试图用id为: " + user.getId() + " 修改稿件id为: " + resources.getId() + " 的删除状态为1");
                return false;
            }
        }
        resources.setIsDlete(isDlete);
        this.linkSole.addResources(resources);
        return this.resourcesMapper.setIsDelete(resources) > 0;
    }

    /**
     * 设置指定资源的审核状态
     *
     * @param resources
     * @return
     */
    public boolean setToExamine(Resources resources) throws IOException, SolrServerException {
        //先备份一下要设置的审核状态
        Byte toExamine = resources.getToExamine();
        //根据id获取资源全部信息,不明白为什么要这样做去看一下LinkSole的addResources方法就懂了
        resources = resourcesMapper.getResourcesById(resources.getId());
        resources.setToExamine(toExamine);
        this.linkSole.addResources(resources);
        return this.resourcesMapper.setToExamine(resources) > 0;
    }

    /**
     * 设置指定资源的点击量
     *
     * @param resources
     * @return
     */
    public boolean setClicks(Resources resources) {
        return this.resourcesMapper.setClicks(resources) > 0;
    }

    /**
     * 修改指定资源的：分类、标题、描述、封面文件名、来源、备注、下载链接,并设置为待审核
     *
     * @param resources
     * @return
     */
    @Transactional
    public boolean modifyResources(Resources resources,User user) throws IOException, SolrServerException {
        boolean flag = true;
        try {
            //将旧资源里需要保留下来的信息赋予修改后的资源
            Resources buffer = resourcesMapper.getResourcesById(resources.getId());
            //如果修改者与资源作者id不同则不允许修改
            if(user.getId() != buffer.getUserId())
                return false;
            if(resources.getCoverFilename() == null)
                resources.setCoverFilename(buffer.getCoverFilename());//说明未修改封面
            resources.setIsDlete(buffer.getIsDlete());
            resources.setToExamine(new Byte("0"));//需要重新审核

            //开始修改
            this.resourcesMapper.setToExamine(resources);
            this.resourcesMapper.modifyResources(resources);
            this.linkSole.addResources(resources);
        } catch (Exception e) {
            log.error("修改资源内容过程出错，报错信息: " + e.toString());
            flag = false;
        } finally {
            return flag;
        }
    }

    /**
     * 只通过数据库分页获取资源，根据需求传入参数
     *
     * @param pageNun   当前页码
     * @param pageSize  如果此参数传入为null，则用配置文件中默认的pageSize
     * @param tyepId    如果要分类查询就传入，否则传入bull
     * @param isDelete  删除状态
     * @param toExamine 审核状态
     * @param isAll     如果要查询全部就传入true，否则传入null或false
     * @return
     */
    public PageInfo<Resources> getResourcesPage(int pageNun, Integer pageSize, Long tyepId, Byte isDelete, Byte toExamine, Boolean isAll) {
        if (pageSize == null)
            PageHelper.startPage(pageNun, this.pageSize);
        else
            PageHelper.startPage(pageNun, pageSize);
        List<Resources> resources = null;

        if (tyepId == null && isDelete != null && toExamine != null && isAll == null)//根据isDelete和toExamine获取资源类型
            resources = this.resourcesMapper.getResourcesByToExamineAndIsDelete(isDelete, toExamine);
        else if (tyepId != null)//根据资源类型分页获取未被删除、通过审核的资源
            resources = this.resourcesMapper.getResourcesByType(tyepId);
        else if (isAll != null && isAll)//只分页获取
            resources = this.resourcesMapper.getAllResources();
        else
            return null;
        PageInfo<Resources> studentPageInfo = new PageInfo<>(resources);
        return studentPageInfo;
    }

    /**
     * 根据用户id分页获取资源
     *
     * @param pageNun
     * @param pageSize
     * @param userId
     * @return
     */
    public PageInfo<Resources> getResourcesPage(int pageNun, Integer pageSize, Long userId) {
        if (pageSize == null)
            PageHelper.startPage(pageNun, this.pageSize);
        PageHelper.startPage(pageNun, pageSize);
        List<Resources> resources = this.resourcesMapper.getResourcesByUserid(userId);
        PageInfo<Resources> studentPageInfo = new PageInfo<>(resources);
        return studentPageInfo;
    }

    /**
     * 根据id获取资源
     *
     * @param id
     * @return
     */
    public Resources getResourcesById(Long id) {
        return this.resourcesMapper.getResourcesById(id);
    }

    /**
     * 新增分类
     *
     * @param name
     * @return
     */
    public boolean addResourcesType(String name) {
        return this.resourcesTypeMapper.addResourcesType(name) > 0;
    }

    /**
     * 根据id修改资源分类名称
     *
     * @param resourcesType
     * @return
     */
    public boolean updateNameById(ResourcesType resourcesType) {
        return this.resourcesTypeMapper.updateNameById(resourcesType) > 0;
    }

    /**
     * 根据id设置资源分类的isDelete属性
     *
     * @param resourcesType
     * @return
     */
    public boolean updateIsDleteById(ResourcesType resourcesType) {
        return this.resourcesTypeMapper.updateIsDleteById(resourcesType) > 0;
    }

    /**
     * 根据名称查询资源分类
     *
     * @param name
     * @return
     */
    public ResourcesType getResourcesTypeByName(String name) {
        return this.resourcesTypeMapper.getResourcesTypeByName(name);
    }

    /**
     * 根据id查询资源分类
     *
     * @param id
     * @return
     */
    public ResourcesType getResourcesTypeById(int id) {
        return this.resourcesTypeMapper.getResourcesTypeById(id);
    }

    /**
     * 获取全部资源分类
     *
     * @return
     */
    public List<ResourcesType> getAllResourcesTypes() {
        return this.resourcesTypeMapper.getAllResourcesType();
    }

    /**
     * 根据isDelete获取资源分类列表
     *
     * @param isDelete
     * @return
     */
    public List<ResourcesType> getResourcesTypeByIsDelete(int isDelete) {
        return this.resourcesTypeMapper.getResourcesTypeByIsDelete(isDelete);
    }

    /**
     * 分页查询资源分类
     *
     * @param pageNun
     * @param pageSize
     * @return
     */
    public PageInfo<ResourcesType> getResourcesTypeByPage(int pageNun, int pageSize) {
        PageHelper.startPage(pageNun, pageSize);
        List<ResourcesType> userList = resourcesTypeMapper.getAllResourcesType();
        PageInfo<ResourcesType> studentPageInfo = new PageInfo<>(userList);
        return studentPageInfo;
    }

    public ResourcesTypeMapper getResourcesTypeMapper() {
        return resourcesTypeMapper;
    }

    public ResourcesMapper getResourcesMapper() {
        return resourcesMapper;
    }
}
