package com.czh.basicframe.https;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * author  : czh
 * create Date : 2019/10/22  11:54
 * 详情 :
 */
public interface MyService {

    /***
     *   1.普通post请求
     *
     *   @FormUrlEncoded
     *   @POST("{url}")
     *   Observable<Object> moethodName (@Path("url") String url, @FieldMap Map<String, String> maps);
     *
     *   2、上传文件
     *    @Multipart
     *    @POST("{url}")
     *    Observable<Object> moethodName(@Path("url") String url, @Part List<MultipartBody.Part> partList);
     *
     *   3、GET请求
     *   @GET("{url}")
     *   Observable<Object> moethodName (@Path("url") String url, @QueryMap Map<String, String> maps);
     *
     *   4、下载文件
     *   @Streaming
     *   @GET
     *   Observable<ResponseBody> down(@Url String fileUrl);
     *
     *   5、PUT请求
     *   @FormUrlEncoded
     *   @PUT("/api/category/update")
     *   Observable<GetArticleAppPresenter.Bean> put(@Query("id") String id, @FieldMap() Map<String, String> maps);
     *
     *   6、Delete
     *   @DELETE("/api/category/delete")
     *   Observable<GetArticleAppPresenter.Bean> del(@Query("id") String id);
     *
     **/

    @GET("{url}")
    Observable<Object> test(@Path("url") String url);

}
