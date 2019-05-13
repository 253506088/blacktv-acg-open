package blacktv.tvacg.controller.reception;

import blacktv.tvacg.database.pojo.Resources;
import blacktv.tvacg.database.service.LinkSole;
import blacktv.tvacg.database.service.ResourcesService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责前台的全部视图
 */
@Controller
public class ReceptionAllView {
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private LinkSole linkSole;

    /**
     * 登录、找回密码、注册页面
     *
     * @return
     */
    @GetMapping(value = "/user/loginPage")
    public String loginPage() {
        return "reception/login";
    }

    /**
     * 首页
     *
     * @param page
     * @param limit
     * @param typeId
     * @param search
     * @param request
     * @param session
     * @return
     * @throws SolrServerException
     */
    @GetMapping(value = "/index")
    public String indexPage(Integer page, Integer limit, Long typeId, String search, HttpServletRequest request, HttpSession session) throws SolrServerException {
        List<Resources> resourcesList = null;
        //如果有null说明是第一次打开首页，初始化一下数据。如果search不为空说明是搜索，就不需要初始化。
        if (page == null || limit == null || typeId == null && search == null) {
            page = 1;
            limit = 12;
            typeId = new Long(0);
        }
        long count = 0;
        if (typeId != null && typeId > 0) {//分类查询
            resourcesList = resourcesService.getResourcesPage(page, limit, typeId, null, null, null).getList();
            count = resourcesService.getResourcesMapper().getNumberByType(typeId);
        } else if (typeId != null && typeId == 0) {//只分页查询,查询通过审核且未被删除的
            Byte num = new Byte("1");
            resourcesList = resourcesService.getResourcesPage(page, limit, null, num, num, null).getList();
            count = resourcesService.getResourcesMapper().getNumberByToExamineAndIsDelete(num, num);
        } else if (search != null) {//关键词查询
            List<Long> idList = linkSole.selectPage(search, page, limit, 1, 1);
            resourcesList = new ArrayList<>();
            if (idList != null) {
                for (Long id : idList) {
                    resourcesList.add(resourcesService.getResourcesById(id));
                }
            }
            count = linkSole.getDataNumber(search, 1, 1);
        }

        long allPage = 0;
        if (count % limit != 0) {
            allPage = 1 + (count / limit);
        } else {
            allPage = count / limit;
        }

        List<Integer> pageList = new ArrayList<>();

        //计算页码
        if (page <= 5) {
            long buffer = 10;
            if (allPage < 10) {
                buffer = allPage;
            }
            for (int i = 1; i <= buffer; i++) {
                pageList.add(i);
            }
        }
        if (page > 5) {
            for (int i = (page - 5); i <= page; i++) {
                pageList.add(i);
            }
            long buffer = 5;
            if ((allPage - page) < 5) {
                buffer = (allPage - page) + 1;
            }
            for (int i = 1; i < buffer; i++) {
                int p = page + i;
                pageList.add(p);
            }
        }
        session.setAttribute("resourcesList", resourcesList);//资源列表
        session.setAttribute("pageList", pageList);//页码列表
        session.setAttribute("prePage", page - 1 > 0 ? page - 1 : 1);//上一页
        session.setAttribute("nextPage", page + 1 <= count ? page + 1 : count);//下一页
        session.setAttribute("page", page);//当前页面
        return "reception/index";
    }

    /**
     * 资源页面
     *
     * @return
     */
    @GetMapping(value = "/resources")
    public String resourcesPage() {
        return "reception/resources";
    }

    /**
     * 用户个人空间
     *
     * @return
     */
    @GetMapping(value = "/user/index")
    public String userIndex() {
        return "reception/userIndex";
    }

    /**
     * 用户修改个人信息页面
     *
     * @return
     */
    @GetMapping(value = "/user/update/msg")
    public String updateUserMgsPage() {
        return "reception/updateUserMsg";
    }

    /**
     * 投稿页面
     *
     * @return
     */
    @GetMapping(value = "/resources/uploadPage")
    public String resourcesUploadPage() {
        return "reception/resourcesUpload";
    }

    /**
     * 用户管理资源界面
     *
     * @return
     */
    @GetMapping(value = "/resources/user/admin")
    public String resourcesUserAdminPage() {
        return "reception/resourcesAdmin";
    }

    /**
     * 施工页面500错误用
     *
     * @return
     */
    @GetMapping(value = "/error/500")
    public String working() {
        return "reception/working";
    }

    @GetMapping(value = "/error/404")
    public String error404() {
        return "reception/error404";
    }
}
