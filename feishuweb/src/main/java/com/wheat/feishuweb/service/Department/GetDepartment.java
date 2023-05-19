package com.wheat.feishuweb.service.Department;

import com.lark.oapi.Client;
import com.lark.oapi.service.contact.v3.model.Department;
import com.lark.oapi.service.contact.v3.model.ListDepartmentReq;
import com.lark.oapi.service.contact.v3.model.ListDepartmentResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

/**
 * @projectName: FeiShu_CopyDept
 * @package: com.wheat.Department
 * @className: GetDepartmentId
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/16 12:11
 * @version: 1.0
 */
@Component
public class GetDepartment {

    @Value("${feiShuApp.APP_ID}")
    private String APP_ID;
    @Value("${feiShuApp.APP_SECRET}")
    private String APP_SECRET;

    /**
     * @param parentDeptId: 父部门id
     * @return Iterator<Department>: 返回获取到的 部门数组迭代器
     * @description TODO: 通过 父部门id 获取部门信息
     */
    public Iterator<Department> getDeptByParentId(String parentDeptId) throws Exception {
        //构建Client
        Client client = Client.newBuilder(APP_ID, APP_SECRET).build();

        //创建请求对象
        ListDepartmentReq req = ListDepartmentReq.newBuilder()
                .departmentIdType("department_id")
                .parentDepartmentId(parentDeptId)
                .fetchChild(false)
                .pageSize(50)
                .build();

        //发起请求
        ListDepartmentResp resp = client.contact().department().list(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return null;
        }

        //业务数据处理
        if (resp.getData().getItems() != null) {
            Iterator<Department> iterator = Arrays.stream(resp.getData().getItems()).iterator();
            return iterator;
        }
        return null;
    }
}
