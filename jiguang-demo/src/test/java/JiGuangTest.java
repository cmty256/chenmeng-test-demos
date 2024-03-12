import com.chenmeng.project.dto.PushDTO;
import com.chenmeng.project.service.JiGuangPushService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 沉梦听雨
 */
@SpringBootTest
public class JiGuangTest {

    @Resource
    private JiGuangPushService jiGuangService; //注入极光推送服务类对象

    @Test
    public void testPush() {
        //定义和赋值推送实体
        PushDTO pushBean = new PushDTO();
        pushBean.setTitle("titleStr");
        pushBean.setAlert("alertStr");
        //额外推送信息
        Map<String, String> map = new HashMap<>();
        map.put("xxx", "xxx");
        pushBean.setExtras(map);
        //进行推送，推送到所有使用Android客户端的用户，返回推送结果布尔值
        boolean flag = jiGuangService.pushAndroid(pushBean);
        System.out.println("flag = " + flag);
    }

}
