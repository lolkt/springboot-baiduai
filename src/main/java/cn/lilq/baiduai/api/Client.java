package cn.lilq.baiduai.api;

import cn.lilq.baiduai.pojo.FaceAddAPI;
import cn.lilq.baiduai.pojo.FaceSearchAPI;
import cn.lilq.baiduai.util.GsonUtils;
import com.baidu.aip.face.AipFace;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class Client {
    private static String APP_ID = "18243176";
    private static String API_KEY = "VtanZOh6EPcHj324HDKzrscD";
    private static String SECRET_KEY = "aIiDeGVpqajNa9qaaprXx9R7NWmgyKM9";
    private AipFace client;

    public Client() {
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    }

    public FaceSearchAPI search(String image, String imageType, String groupIdList) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "3");
        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");
        options.put("max_user_num", "3");

//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//        String groupIdList = "test,test0";//用户列表

        // 人脸搜索 M:N 识别
        JSONObject res = client.multiSearch(image, imageType, groupIdList, options);
        FaceSearchAPI faceSearchAPI = GsonUtils.fromJson(res.toString(), FaceSearchAPI.class);
        System.out.println(faceSearchAPI);
        return faceSearchAPI;
    }

    public FaceAddAPI add(String image, String imageType, String groupId, String userId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");
        options.put("action_type", "REPLACE");

//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//        String groupId = "group1";
//        String userId = "user1";
        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
//        System.out.println(res.toString());
//        System.out.println(res.toString(2));
        return GsonUtils.fromJson(res.toString(), FaceAddAPI.class);

    }

}
