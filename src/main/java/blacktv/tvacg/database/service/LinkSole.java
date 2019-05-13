package blacktv.tvacg.database.service;

import blacktv.tvacg.database.pojo.Resources;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作Sole的类
 */
@Service
public class LinkSole {
    @Value("${tvacg.solr.baseURL}")
    private String baseURL;
    @Value("${tvacg.page.pageSize}")
    public String pageSize222;
    private Integer pageSize;


    /**
     * 向solr里添加新资源，或修改某一个具体的属性，solr不能像mysql那样只改某一个属性，
     * 他本质是删除旧的，添加新的，所以不想修改的属性也要原封不动的传入
     *
     * @param resources
     * @throws IOException
     * @throws SolrServerException
     */
    public void addResources(Resources resources) throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(baseURL);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", resources.getId().toString());
        document.setField("title_ik", resources.getTitle());
        document.setField("describe_ik", resources.getDescribe());
        document.setField("isDlete", resources.getIsDlete().toString());
        document.setField("toExamine", resources.getToExamine());
        solrServer.add(document);
        solrServer.commit();
    }

    /**
     * 根据关键词、isDelete、toExamine分页查询,返回的是id的集合，用id去数据库里查询信息。
     * 如果isDelete和toExamine为null则不作为条件。
     *
     * @param keyWord
     * @param pageNum
     * @return
     * @throws SolrServerException
     */
    public List<Long> selectPage(String keyWord, int pageNum,int pageSize, Integer isDelete, Integer toExamine) throws SolrServerException {
        String query = "resources_ik:" + keyWord;
        if (isDelete != null)
            query += " AND isDlete:" + isDelete;//AND前面要有空格
        if (toExamine != null)
            query += " AND toExamine:" + toExamine;//AND前面要有空格
        long dataNumber = this.getDataNumber(query);//该查询条件下，能查到的数据总数
        long pageAllNumber = this.getPageAllNumber(dataNumber, pageSize);//总页数
        if (pageNum > pageAllNumber || pageNum < 1)
            return null;// 超出范围或小于1
        // 计算出从第几个进行查询
        Integer from = null;
        if (pageNum > 1)
            from = (pageSize * (pageNum - 1));
        else
            from = 0;
        SolrServer solrServer = new HttpSolrServer(baseURL);
        SolrQuery solrQuery = new SolrQuery(query);
        //设置分页从form开始查询，总共查询pageSize条，solr的分页和mysql不一样
        solrQuery.set("start", from);
        solrQuery.set("rows", pageSize);
        //根据id升序排序
        solrQuery.addSort("id", SolrQuery.ORDER.asc);
        //执行查询
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //文档结果集
        SolrDocumentList documents = queryResponse.getResults();
        List<Long> idList = new ArrayList<>();
        //遍历查询结果
        for (SolrDocument document : documents){
            String id = (String) document.get("id");
            idList.add(new Long(id));//通过域名获取值
        }

        return idList;
    }

    /**
     * 获取该查询条件，能够查询到多少条数据
     *
     * @param query 查询条件
     */
    private long getDataNumber(String query) throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer(baseURL);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.set("start", 0);//设置分页起点为0
        solrQuery.set("rows", 0);//查询0条数据，这样不会获取实际的数据，但是能获取到总条数
        //执行查询
        return solrServer.query(solrQuery).getResults().getNumFound();
    }

    /**
     * 根据关键字、isDelete、toExamine获取资源总数
     * @param keyWord
     * @param isDelete
     * @param toExamine
     * @return
     */
    public long getDataNumber(String keyWord, Integer isDelete, Integer toExamine) throws SolrServerException {
        String query = "resources_ik:" + keyWord;
        if (isDelete != null)
            query += " AND isDlete:" + isDelete;//AND前面要有空格
        if (toExamine != null)
            query += " AND toExamine:" + toExamine;//AND前面要有空格
        return this.getDataNumber(query);
    }

    /**
     * 计算总页数
     *
     * @param dataNumber 数据总数
     * @param pageSize   一页承载多少数据
     * @return
     */
    public long getPageAllNumber(long dataNumber, int pageSize) {
        if (dataNumber <= 0 || pageSize <= 0)
            return 0;
        Long pageAllNumber = null;
        if (dataNumber % pageSize != 0) {
            pageAllNumber = 1 + (dataNumber / pageSize);
        } else {
            pageAllNumber = dataNumber / pageSize;
        }
        return pageAllNumber;
    }
}
