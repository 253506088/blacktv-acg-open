# 项目描述
blacktv-acg-open是原版的blacktv-acg项目，而blacktv-acg项目重构用户相关的服务后闭源。 blacktv-acg-open为后端基于Spring Boot+SSM+MySQL+Solr+Redis的前后分离项目。该项目为资源共享网站，主要分享ACG相关的资源（电影、动漫、音乐、图片、小说等等），用户发布的资源经过审核成功后就可以被其他用户浏览到。用户可以通过内置的搜索引擎检索想要的资源，或资源分类筛选资源。用户的会话状态通过JWT进行保存，节省了服务器内存，并且也可以解决分布式项目共享Session的问题。

# 依赖内容
 * Spring Boot、Spring、Spring MVC、Mybatis
 * FastJson、JJWT
 * MySQL、Redis
 * Solr
 * Laiyu、jQuery