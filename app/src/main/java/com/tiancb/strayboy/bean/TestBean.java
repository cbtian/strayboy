package com.tiancb.strayboy.bean;

import com.strayboy.strayboy.http.net.CommonModel;
import com.strayboy.strayboy.http.net.MwResponse;

/**
 * Created by tiancb on 2018/4/11.
 */

public class TestBean extends MwResponse {
    public static final String url = CommonModel.HOST + "myselfSpitslot";
    public static final String url_comment = CommonModel.HOST + "commentBeRepliedSpitslot";

    public static final String PARAM_START = "start";//
    public static final String PARAM_OFFSET = "size";//task_id
    public static final int TYPE_MINE_SUB = 0;//我的帖子
    public static final int TYPE_MINE_COM = 1;//我的评论
    public ReturnData result;

    public class ReturnData {
//        public PostInfo content;
//        public String extra;
//        public ArrayList<PostInfo> items;
    }


    public interface PostCityCallBack {
//        void success(PostByMineModel data);

//        void error(int errorcode);
    }
}
