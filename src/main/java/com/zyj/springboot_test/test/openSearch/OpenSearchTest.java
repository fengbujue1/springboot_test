package com.zyj.springboot_test.test.openSearch;

import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Maps;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONArray;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.document.Command;
import com.aliyun.opensearch.sdk.generated.document.DocumentConstants;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;
public class OpenSearchTest {

    private static String appName = "test_opensearh";
    private static String tableName = "person";
    private static String accesskey = "";
    private static String secret = "";
    private static String host = "http://opensearch-cn-shenzhen.aliyuncs.com";

    public static void main(String[] args) {
//        testByPush();
        query();
    }

    public static void testByPush() {
        //查看文件和默认编码格式
        System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
        System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));
        //-------------数据推送示例代码-----------------
        //生成随机数，作为主键值
        Random rand = new Random();
        int id1 = rand.nextInt(Integer.MAX_VALUE);
        int id2 = rand.nextInt(Integer.MAX_VALUE);
        //定义Map对象存储上传文档数据,此为文档1
        Map<String, Object> doc1 = Maps.newLinkedHashMap();
        doc1.put("id", id1);
        String name = "周赟吉";// utf-8
        byte[] bytes;
        try {
            bytes = name.getBytes("utf-8");
            String utf8_string = new String(bytes, "utf-8");
            doc1.put("name", utf8_string);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        doc1.put("school", "湖南大学");
        doc1.put("age", 26);
        doc1.put("register_time", System.currentTimeMillis());
        //标准版不支持update,需要使用ADD进行全字段更新；高级版支持update,部分字段更新。
        JSONObject json1 = new JSONObject();
        json1.put(DocumentConstants.DOC_KEY_CMD, Command.ADD.toString());
        json1.put(DocumentConstants.DOC_KEY_FIELDS, doc1);
        //定义Map对象存储上传文档数据,此为文档2
        Map<String, Object> doc2 = Maps.newLinkedHashMap();
        doc2.put("id", id2);
        String title_string2 = "张聚";// utf-8
        byte[] bytes2;
        try {
            bytes2 = title_string2.getBytes("utf-8");
            String utf8_string2 = new String(bytes2, "utf-8");
            doc2.put("name", utf8_string2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        doc2.put("school", "西华大学");
        doc2.put("age", 26);
        doc2.put("register_time", System.currentTimeMillis());
        //新增及更新都设为ADD，不支持update，当已存在相同主键值文档时做更新，否则新增，此处作为新增
        JSONObject json2 = new JSONObject();
        json2.put(DocumentConstants.DOC_KEY_CMD, Command.ADD.toString());
        json2.put(DocumentConstants.DOC_KEY_FIELDS, doc2);
        JSONArray docsJsonArr = new JSONArray();
        docsJsonArr.put(json1);//新增文档1
        docsJsonArr.put(json2);//新增文档2
        String docsJson = docsJsonArr.toString();
        //创建并构造OpenSearch对象
        OpenSearch openSearch = new OpenSearch(accesskey, secret, host);
        //创建OpenSearchClient对象，并以OpenSearch对象作为构造参数
        OpenSearchClient serviceClient = new OpenSearchClient(openSearch);
        //定义DocumentClient对象添加json格式doc数据批量提交
        DocumentClient documentClient = new DocumentClient(serviceClient);
        try {
            //执行推送操作
            OpenSearchResult osr = documentClient.push(docsJson, appName, tableName);
            //判断数据是否推送成功，主要通过判断2处，第一处判断用户方推送是否成功，第二处是应用控制台中有无报错日志
            //用户方推送成功后，也有可能在应用端执行失败，此错误会直接在应用控制台错误日志中生成，比如字段内容转换失败
            if(osr.getResult().equalsIgnoreCase("true")){
                System.out.println("用户方推送无报错！\n以下为getTraceInfo推送请求Id:"+osr.getTraceInfo().getRequestId());
            }else{
                System.out.println("用户方推送报错！"+osr.getTraceInfo());
            }
        } catch (OpenSearchException e) {
            e.printStackTrace();
        } catch (OpenSearchClientException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);//休眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void query() {
        //创建并构造OpenSearch对象
        OpenSearch openSearch = new OpenSearch(accesskey, secret, host);
        //创建OpenSearchClient对象，并以OpenSearch对象作为构造参数
        OpenSearchClient serviceClient = new OpenSearchClient(openSearch);
        //-------------查询上面push的文档-----------------
        //创建SearcherClient对象，并以OpenSearchClient对象作为构造参数
        SearcherClient searcherClient = new SearcherClient(serviceClient);
        //定义Config对象，用于设定config子句参数，用于分页或设置数据返回格式
        Config config = new Config(Lists.newArrayList(appName));
        config.setStart(0);
        config.setHits(1);
        //设置返回格式为json,目前只支持返回xml和json格式，暂不支持返回fulljson类型
        config.setSearchFormat(SearchFormat.JSON);
        // 设置搜索结果返回应用中哪些字段
        config.setFetchFields(Lists.newArrayList("id", "name", "school", "age", "register_time"));
        // 创建参数对象
        SearchParams searchParams = new SearchParams(config);
//        // 设置查询子句，若需多个索引组合查询，需要setQuery处合并，否则若设置多个setQuery后面的会替换前面查询
//        searchParams.setQuery("id:'" + id1 + "'|'"+id2 +"'");
        // 设置查询过滤条件
        searchParams.setFilter("age<=30");
        // 设置sort条件
        Sort sorter = new Sort();
        sorter.addToSortFields(new SortField("id", Order.DECREASE)); //设置id字段降序
        sorter.addToSortFields(new SortField("RANK", Order.INCREASE)); //若id相同则以RANK升序
        //添加Sort对象参数
        searchParams.setSort(sorter);
        // 执行返回查询结果
        SearchResult searchResult;
        try {
            searchResult = searcherClient.execute(searchParams);
            String result = searchResult.getResult();
            JSONObject obj = new JSONObject(result);
            // 输出查询结果
            System.out.println(obj.toString());
        } catch (OpenSearchException e) {
            e.printStackTrace();
        } catch (OpenSearchClientException e) {
            e.printStackTrace();
        }
    }
}
