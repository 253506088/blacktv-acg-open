package blacktv.tvacg.controller.admin;

import blacktv.tvacg.database.pojo.Resources;
import blacktv.tvacg.database.pojo.ResourcesType;
import blacktv.tvacg.database.service.RedisUtil;
import blacktv.tvacg.database.service.ResourcesService;
import blacktv.tvacg.tool.LayuiJsonUtil;
import blacktv.tvacg.tool.SerializeUtils;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController()
@RequestMapping(value = "/blacktv/acg/admin/resources")
public class AdminResourcesController {
    @Autowired
    private ResourcesService resourcesService;
    @Value("${tvacg.upload.resourceTypeKeyName}")
    private String typeKey;
    @Autowired
    private RedisUtil redisUtil;
    private String error404Url = "/error/404";

    /**
     * 获取待审核资源的json数据
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/get/examineDate", produces = "text/html;charset=utf-8", params = {"page", "limit"})
    public String getResourcesExamineDate(int page, int limit, HttpServletRequest request) throws IOException, ClassNotFoundException {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求待审核的资源");
            return "redirect:" + error404Url;
        }
        Map<Long, ResourcesType> resourcesTypes = this.getResourcesTypeList();
        //查询isDelete = 1 并且 toExamine = 0的资源，用户删除掉的资源就不审核了
        Byte isDelete = new Byte("1");
        Byte toExamine = new Byte("0");
        PageInfo<Resources> resourcesPage = resourcesService.getResourcesPage(page, limit, null, isDelete, toExamine, null);
        //循环设置分类名称,以及完善封面地址
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        for (Resources resources : resourcesPage.getList()) {
            resources.setTypeName(resourcesTypes.get(resources.getTypeId()).getName());
            resources.setCoverFilename("<img src='" + basePath + "/" + resources.getCoverFilename() + "'/>");
        }
        return LayuiJsonUtil.getLayuiJson(0, resourcesService.getResourcesMapper().getNumberByToExamineAndIsDelete(isDelete, toExamine), "", resourcesPage.getList());
    }

    /**
     * 设置资源审核状态接口
     *
     * @param resources
     * @param request
     * @return
     */
    @PostMapping(value = "/set/examine", produces = "text/html;charset=utf-8", params = {"id", "toExamine"})
    public String setResourcesToExamine(Resources resources, HttpServletRequest request) throws IOException, SolrServerException {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求设置资源审核状态接口");
            return "redirect:" + error404Url;
        }
        Map<String, Object> msg = new HashMap<>();
        if (resourcesService.setToExamine(resources))
            msg.put("msg", "msg025");//成功
        else
            msg.put("msg", "msg026");//失败
        return JSONArray.toJSONString(msg);
    }

    /**
     * 为管理资源页面提供资源的json数据
     *
     * @param page
     * @param limit
     * @param request
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @GetMapping(value = "/get/adminDate", produces = "text/html;charset=utf-8", params = {"page", "limit"})
    public String getResourcesAdminDate(int page, int limit, HttpServletRequest request) throws IOException, ClassNotFoundException {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求待审核的资源");
            return "redirect:" + error404Url;
        }
        Map<Long, ResourcesType> resourcesTypes = this.getResourcesTypeList();
        //查询全部的资源，只分页
        PageInfo<Resources> resourcesPage = resourcesService.getResourcesPage(page, limit, null, null, null, true);
        //循环设置分类名称,以及完善封面地址
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        for (Resources resources : resourcesPage.getList()) {
            resources.setTypeName(resourcesTypes.get(resources.getTypeId()).getName());
            resources.setCoverFilename("<img src='" + basePath + "/" + resources.getCoverFilename() + "'/>");
        }
        return LayuiJsonUtil.getLayuiJson(0, resourcesService.getResourcesMapper().getAllNumber(), "", resourcesPage.getList());
    }

    /**
     * 设置资源的删除状态
     *
     * @param resources
     * @param request
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @PostMapping(value = "/set/isDelete", produces = "text/html;charset=utf-8", params = {"id", "isDlete"})
    public String setResourcesExamineDate(Resources resources, HttpServletRequest request) throws IOException, SolrServerException {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求设置资源审核状态接口");
            return "redirect:" + error404Url;
        }
        Map<String, Object> msg = new HashMap<>();
        if (resourcesService.setIsDelete(resources, null, null))
            msg.put("msg", "msg027");//成功
        else
            msg.put("msg", "msg028");//失败
        return JSONArray.toJSONString(msg);
    }

    /**
     * 获取资源分类isDelete为1的分类列表
     *
     * @return
     */
    private Map<Long, ResourcesType> getResourcesTypeList() throws IOException, ClassNotFoundException {
        Map<Long, ResourcesType> resourcesTypes = new HashMap<>();
        //先从缓存中查询，缓存中不存在再去数据库中查询
        if (redisUtil.exists(typeKey))
            resourcesTypes = (Map<Long, ResourcesType>) SerializeUtils.serializeToObject(redisUtil.stringGet(typeKey));
        else {
            List<ResourcesType> buffer = resourcesService.getResourcesTypeByIsDelete(1);//查询未被删除的数据
            //过滤掉没用的信息，并存入map中
            for (ResourcesType resourcesType : buffer) {
                resourcesType.setIsDlete(null);
                resourcesTypes.put(resourcesType.getId(), resourcesType);
            }
            redisUtil.stringSet(typeKey, SerializeUtils.serialize(resourcesTypes), 0);//存储到redis中，不限制时长
        }
        return resourcesTypes;
    }

    /**
     * 登录验证
     *
     * @param request
     * @return
     */
    private boolean checkLogin(HttpServletRequest request) {
        //如果没登录转发到404
        if (request.getSession().getAttribute("adminUser") == null) {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                    + "/";
            log.info("ip:" + request.getRemoteAddr() + "\t非法请求后台接口,请求的url为: " + basePath);
            return false;
        }
        return true;
    }
}
