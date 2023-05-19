package com.wheat.feishuweb.service.attendance;

import com.lark.oapi.Client;
import com.lark.oapi.service.attendance.v1.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @projectName: FeiShu_CopyDept
 * @package: com.wheat.attendance
 * @className: GetAttendance
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/16 16:12
 * @version: 1.0
 */
@Component
public class GetAttendance {

    @Value("${feiShuApp.APP_ID}")
    private String APP_ID;
    @Value("${feiShuApp.APP_SECRET}")
    private String APP_SECRET;

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getAPP_SECRET() {
        return APP_SECRET;
    }

    public void setAPP_SECRET(String APP_SECRET) {
        this.APP_SECRET = APP_SECRET;
    }

    /**
     * @param statsType 统计类型，可选 daily 或 month
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param userIds 需统计用户的id
     * @return Iterator<UserStatsData> 统计的用户出勤数据迭代器
     * @description TODO 获取用户的出勤信息数据
     */
    public Iterator<UserStatsData> getAttendance(String statsType, Integer startDate, Integer endDate, String[] userIds) throws Exception {
        //构建client
        Client client = Client.newBuilder(APP_ID, APP_SECRET).build();

        //创建请求对象
        QueryUserStatsDataReq req = QueryUserStatsDataReq.newBuilder()
                .employeeType("employee_id")
                .queryUserStatsDataReqBody(QueryUserStatsDataReqBody.newBuilder()
                        .locale("zh")
                        .statsType(statsType)
                        .startDate(startDate)
                        .endDate(endDate)
                        .userIds(userIds)
                        .needHistory(true)
                        .currentGroupOnly(true)
                        .userId("ededf38c")
                        .build())
                .build();

        //发起请求
        QueryUserStatsDataResp resp = client.attendance().userStatsData().query(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return null;
        }

        //业务数据处理
        Iterator<UserStatsData> iterator = Arrays.stream(resp.getData().getUserDatas()).iterator();
        return iterator;

    }
}
