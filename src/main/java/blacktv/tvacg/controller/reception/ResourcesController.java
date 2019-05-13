package blacktv.tvacg.controller.reception;

import blacktv.tvacg.database.pojo.Resources;
import blacktv.tvacg.database.pojo.ResourcesType;
import blacktv.tvacg.database.pojo.User;
import blacktv.tvacg.database.service.CheckJWT;
import blacktv.tvacg.database.service.RedisUtil;
import blacktv.tvacg.database.service.ResourcesService;
import blacktv.tvacg.tool.GetMd5;
import blacktv.tvacg.tool.LayuiJsonUtil;
import blacktv.tvacg.tool.SerializeUtils;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源与资源分类的控制器
 */
@Log4j
@RestController()
@RequestMapping(value = "/resources")
public class ResourcesController {
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CheckJWT checkJWT;
    @Value("${tvacg.upload.resourcesCoverPath}")
    private String filePath;
    @Value("${tvacg.upload.resourceTypeKeyName}")
    private String typeKey;
    @Value("${tvacg.upload.resourceKeyName}")
    private String key;
    @Value("${tvacg.user.visitKey}")
    private String visitKey;
    @Value("${tvacg.upload.resourcesSeconds}")
    private int seconds;
    @Value("${tvacg.page.pageSize}")
    private Integer pageSize;

    @GetMapping(value = "/test")
    public String test() {
        return null;
    }

    /**
     * 获取全部 isDelete == 1 的资源分类
     *
     * @return
     */
    @GetMapping(value = "/get/type")
    public String getResourcesTypeList() throws IOException, ClassNotFoundException {
        List<ResourcesType> resourcesTypes = null;
        //先从缓存中查询，缓存中不存在再去数据库中查询
        if (redisUtil.exists(typeKey))
            resourcesTypes = (List<ResourcesType>) SerializeUtils.serializeToObject(redisUtil.stringGet(typeKey));
        else {
            resourcesTypes = resourcesService.getResourcesTypeByIsDelete(1);//查询未被删除的数据
            //过滤掉不想展示给用户的数据
            for (ResourcesType resourcesType : resourcesTypes)
                resourcesType.setIsDlete(null);
            redisUtil.stringSet(typeKey, SerializeUtils.serialize(resourcesTypes), 0);//存储到redis中，不限制时长
        }
        return JSONArray.toJSONString(resourcesTypes);
    }

    /**
     * 上传资源封面
     *
     * @return
     */
    @PostMapping(value = "/upload/cover", produces = "text/html;charset=utf-8", params = {"file", "jwt"})
    public String upload(@RequestParam("file") MultipartFile file, String jwt, HttpServletRequest request) throws IOException {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        if (file.isEmpty()) {
            log.info("ip:" + request.getRemoteAddr() + "\t非法请求上传资源封面接口,传入的文件为空，操作的用户id:" + user.getId());
            return null;
        }
        // 获取后缀名
        String imgType = "." + FilenameUtils.getExtension(file.getOriginalFilename());
        //文件名用当前时间戳的字符串+上传用户的id来命名，用的是字符串不会涉及数字加法
        String fileName = new Long(System.currentTimeMillis()).toString() + user.getId() + imgType;
        File dest = new File(filePath + "/" + fileName);
        Map<String, Object> msg = new HashMap<>();
        try {
            log.info("上传文件大小: " + file.getSize() + "Bit ,上传用户id: " + user.getId());
            file.transferTo(dest);
            log.info("ip:" + request.getRemoteAddr() + "\t用户id: " + user.getId() + " 上传资源封面成功,文件名为: " + fileName);
            redisUtil.stringSet(key + user.getId(), fileName, seconds);
            msg.put("uoloadFlag", true);
            msg.put("fileUrl", filePath + fileName);
        } catch (Exception e) {
            log.info("ip:" + request.getRemoteAddr() + "\t用户id: " + user.getId() + " 上传资源封面失败，错误信息为:" + e.toString());
            msg.put("uoloadFlag", false);
        } finally {
            return JSONArray.toJSONString(msg);
        }
    }

    /**
     * 用于上传资源描述（describe）里的图片的接口,layui富文本编辑的上传图片接口不支持捎带其他参数，所以这里没上传jwt
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload/describe/img", produces = "text/html;charset=utf-8", params = {"file"})
    public String uploadDescribeImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            log.info("ip:" + request.getRemoteAddr() + "\t非法请求上传图片接口,传入的文件为空");
            return null;
        }
        // 获取后缀名
        String imgType = "." + FilenameUtils.getExtension(file.getOriginalFilename());
        //文件名用当前时间戳的字符串+上传用户的ip然后再md5加密，用的是字符串不会涉及数字加法
        String fileName = GetMd5.md5(new Long(System.currentTimeMillis()).toString() + request.getRemoteAddr()) + imgType;
        File dest = new File(filePath + "/" + fileName);
        boolean flag = true;
        try {
            log.info("上传文件大小: " + file.getSize() + "Bit ,上传用户ip: " + request.getRemoteAddr());
            file.transferTo(dest);
            log.info("ip:" + request.getRemoteAddr() + "\t用户ip: " + request.getRemoteAddr() + " 上传图片成功,文件名为: " + fileName);
            return this.getJson(fileName, "上传成功", request);
        } catch (Exception e) {
            log.info("ip:" + request.getRemoteAddr() + "\t用户ip: " + request.getRemoteAddr() + " 上传资源封面失败，错误信息为:" + e.toString());
            flag = false;
        } finally {
            if (flag)
                return this.getJson(fileName, "上传成功", request);
            else
                return this.getJson(null, "意外错误,上传失败", request);
        }
    }

    /**
     * SpringBoot版本用于给layui富文本上传图片接口返回json数据的方法
     *
     * @param fileName
     * @param msg
     * @return
     */
    private String getJson(String fileName, String msg, HttpServletRequest request) {
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String json = null;
        if (fileName != null)
            json = "{\"code\":" + 0 + ",\"msg\":" + "\"上传成功\"" + ",\"data\":{\"src\":" + "\"" + basePath + "/" + fileName + "\"}}";
        else
            json = "{\"code\":" + 1 + ",\"msg\":" + msg + ",\"data\":{\"src\":" + "\"\"" + "\"}}";
        return json;
    }

    /**
     * 新增资源接口
     *
     * @param resources
     * @return
     */
    @PutMapping(value = "/add", produces = "text/html;charset=utf-8", params = {"title", "describe", "from", "remarks", "download", "jwt"})
    public String addResources(Resources resources, String jwt, HttpServletRequest request) throws Exception {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        Map<String, Object> msg = new HashMap<>();
        //判断是否上传封面，没有上传封面返回错误信息
        if (redisUtil.exists(key + user.getId())) {
            resources.setCoverFilename(redisUtil.stringGet(key + user.getId()));
            resources.setUserId(user.getId());
            resourcesService.addResources(resources);
            redisUtil.delete(key + user.getId());//没事了就删除掉redis中的封面路径
            msg.put("msg", "msg016");//投稿成功，请等待审核
        } else {
            log.info("用户id: " + user.getId() + "请求新增资源接口未上传封面");
            msg.put("msg", "msg015");//未上传封面或封面失效，请重新上传封面
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 修改资源接口
     *
     * @param resources
     * @param jwt
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update", produces = "text/html;charset=utf-8", params = {"id", "title", "describe", "from", "remarks", "download", "jwt"})
    public String updateResources(Resources resources, String jwt, HttpServletRequest request) throws Exception {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        Map<String, Object> msg = new HashMap<>();
        //判断是否更改封面
        if (redisUtil.exists(key + user.getId())) {
            resources.setCoverFilename(redisUtil.stringGet(key + user.getId()));
            redisUtil.delete(key + user.getId());
        }
        if (resourcesService.modifyResources(resources, user))
            msg.put("msg", "msg031");//资源修改成功，请等待审核
        else {
            msg.put("msg", "msg032");//资源修改失败
        }
        redisUtil.delete(key + user.getId());//没事了就删除掉redis中的封面路径
        return JSONArray.toJSONString(msg);
    }

    /**
     * 分页获取该用户上传的资源
     *
     * @param page
     * @param limit
     * @param request
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @GetMapping(value = "/get/date", produces = "text/html;charset=utf-8", params = {"page", "limit", "jwt"})
    public String getResourcesAdminDate(int page, int limit, String jwt, HttpServletRequest request) throws IOException, ClassNotFoundException {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        Map<Long, ResourcesType> resourcesTypes = this.getResourcesTypeListIsDelete1();
        //查询全部的资源，只分页
        PageInfo<Resources> resourcesPage = resourcesService.getResourcesPage(page, limit, user.getId());
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
    @PostMapping(value = "/set/isDelete", produces = "text/html;charset=utf-8", params = {"id", "isDlete", "jwt"})
    public String setResourcesExamineDate(Resources resources, String jwt, HttpServletRequest request) throws IOException, SolrServerException {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        Map<String, Object> msg = new HashMap<>();
        if (resourcesService.setIsDelete(resources, user, request))
            msg.put("msg", "msg027");//成功
        else
            msg.put("msg", "msg028");//失败
        return JSONArray.toJSONString(msg);
    }

    /**
     * 根据id获取指定资源的json信息
     *
     * @param id
     * @param jwt
     * @param visit   如果传入这个参数为true就代表是访客，则根据算法增加点击量
     * @param request
     * @return
     */
    @GetMapping(value = "/get/byId", produces = "text/html;charset=utf-8", params = {"id"})
    public String getResourcesById(Long id, String jwt, Boolean visit, HttpServletRequest request) {
        User user = null;
        //如果你上传了jwt，检查一下是否合法，不合法还是未登录
        if (jwt != null)
            user = checkJWT.checkJWT(jwt, request);
        Resources resources = resourcesService.getResourcesById(id);
        //判断是否是访客
        if (visit != null && visit) {
            String key = visitKey + request.getRemoteAddr() + "_" + resources.getId();
            //判断是否访问过，访问过就不增加点击量了
            if (!redisUtil.exists(key)) {
                resources.setClicks(resources.getClicks() + 1);
                resourcesService.setClicks(resources);
                redisUtil.stringSet(key, "1", 24 * 60 * 60);//有效期一天
            }
        }
        if (resources != null) {
            //如果你未登录，则下面信息不展示给你看
            if (user == null) {
                resources.setDownload(null);
                resources.setFrom(null);
                resources.setRemarks(null);
                resources.setIsDlete(null);
                resources.setUserId(null);
                resources.setGmt_modified(null);
                resources.setGmt_create(null);
            }
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            resources.setCoverFilename(basePath+"/"+resources.getCoverFilename());
            return JSONArray.toJSONString(resources);
        }
        return null;
    }

    /**
     * 为首页或分类页面提供资源数据,typeId=0则不分类。查询出的数据一定未被删除且通过审核
     *
     * @param page
     * @param limit
     * @param typeId
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @GetMapping(value = "/index/get/date", produces = "text/html;charset=utf-8", params = {"page", "limit", "typeId"})
    public String getResourcesIndexDate(int page, int limit, Long typeId) throws IOException, ClassNotFoundException {
        Map<Long, ResourcesType> resourcesTypes = this.getResourcesTypeListIsDelete1();
        PageInfo<Resources> resourcesPage = null;
        long count = 0;
        if (typeId > 0) {//分类查询
            resourcesPage = resourcesService.getResourcesPage(page, limit, typeId, null, null, null);
            count = resourcesService.getResourcesMapper().getNumberByType(typeId);
        } else if (typeId == 0) {//只分页查询,查询通过审核且未被删除的
            Byte num = new Byte("1");
            resourcesPage = resourcesService.getResourcesPage(page, limit, null, num, num, null);
            count = resourcesService.getResourcesMapper().getNumberByToExamineAndIsDelete(num, num);
        }
        return LayuiJsonUtil.getLayuiJson(0, count, "", resourcesPage.getList());
    }

    /**
     * 获取资源分类isDelete为1的分类列表
     *
     * @return
     */
    private Map<Long, ResourcesType> getResourcesTypeListIsDelete1() throws IOException, ClassNotFoundException {
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
}
