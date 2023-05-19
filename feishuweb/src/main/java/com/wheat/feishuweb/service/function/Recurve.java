package com.wheat.feishuweb.service.function;

import com.lark.oapi.service.contact.v3.model.Department;
import com.lark.oapi.service.contact.v3.model.User;
import com.wheat.feishuweb.service.Department.GetDepartment;
import com.wheat.feishuweb.service.User.GetUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @projectName: FeiShu_CopyDept
 * @package: com.wheat.function
 * @className: Recurve
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/16 12:54
 * @version: 1.0
 */
@Component
public class Recurve {

    @Autowired
    private GetDepartment getDepartment;
    @Autowired
    private GetUserId getUserId;
//    private static HashMap<String,String> users = new HashMap();    //用于存储 用户名 与 用户id 的映射关系
    public static ArrayList<String> users = new ArrayList();         //用于存储 用户id

    /**
     * @param departmentId: 需要递归的部门id
     * @return void
     * @description TODO: 递归获取部门下的所有用户id
     */
    public void getUserIdByRecurve(String departmentId) throws Exception {

//        调用 getUserIdByDeptId() 方法来获取迭代器，然后遍历迭代器来获取 用户id
        Iterator<User> userIterator = getUserId.getUserIdByDeptId(departmentId);
        if (userIterator != null) {
            while (userIterator.hasNext()) {
                User user = userIterator.next();
//                String userName = user.getName();
                String userId = user.getUserId();
//                users.put(userName,userId);
                users.add(userId);
            }
        }

//        调用 getDeptByParentId() 方法来获取迭代器，然后遍历迭代器来获取 部门id
        Iterator<Department> departmentIterator = getDepartment.getDeptByParentId(departmentId);
        if (departmentIterator != null) {
            while (departmentIterator.hasNext()) {
                Department department = departmentIterator.next();
//                如果当前部门的memberCount为0，则不需要递归遍历该部门，直接跳过
                if (department.getMemberCount() != 0) {
                    String id = department.getDepartmentId();
                    getUserIdByRecurve(id);
                }
            }
        }

    }
}
