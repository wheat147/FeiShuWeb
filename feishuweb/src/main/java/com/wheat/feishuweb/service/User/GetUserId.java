package com.wheat.feishuweb.service.User;

import com.lark.oapi.Client;
import com.lark.oapi.service.contact.v3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @projectName: FeiShu_CopyDept
 * @package: com.wheat.function
 * @className: GetUserId
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/16 10:48
 * @version: 1.0
 */
@Component
public class GetUserId {

    @Value("${feiShuApp.APP_ID}")
    private String APP_ID;
    @Value("${feiShuApp.APP_SECRET}")
    private String APP_SECRET;

    /**
     * @param department_id: 部门id
     * @return Iterator<User>: 返回获取到的 用户数组迭代器
     * @description TODO: 通过 部门id 获取该部门下的所有用户信息
     */
    public Iterator<User> getUserIdByDeptId(String department_id) throws Exception {
        //构建client
        Client client = Client.newBuilder(APP_ID, APP_SECRET).build();

        //创建请求对象
        FindByDepartmentUserReq req = FindByDepartmentUserReq.newBuilder()
                .userIdType("user_id")
                .departmentIdType("department_id")
                .departmentId(department_id)
                .pageSize(50).build();

        //发起请求
        FindByDepartmentUserResp resp = client.contact().user().findByDepartment(req);

        //处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return null;
        }

        //业务数据处理
//        判断 getItems(),即返回的员工列表 是否为空
        if (resp.getData().getItems() != null){
//            生成迭代器
            Iterator<User> iterator = Arrays.stream(resp.getData().getItems()).iterator();
            return iterator;
        }
        return null;
    }
}
