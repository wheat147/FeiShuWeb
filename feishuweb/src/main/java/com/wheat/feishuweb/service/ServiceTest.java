package com.wheat.feishuweb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wheat.feishuweb.dao.UserAttendanceDataMapper;
import com.wheat.feishuweb.dao.UserMapper;
import com.wheat.feishuweb.pojo.User;
import com.wheat.feishuweb.pojo.UserAttendanceData;
import com.wheat.feishuweb.service.function.GetData;
import com.wheat.feishuweb.service.function.Recurve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @projectName: FeiShuWeb
 * @package: com.wheat.feishuweb.service
 * @className: Service
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/30 16:00
 * @version: 1.0
 */

@Service
public class ServiceTest {

    @Autowired
    private Recurve recurve;
    @Autowired
    private GetData getData;
    @Autowired
	private UserMapper userMapper;
    @Autowired
	private UserAttendanceDataMapper userAttendanceDataMapper;

    public Collection<UserAttendanceData> getData() throws Exception {
//    	从数据库获取所有用户id
    	ArrayList<String> userIds = new ArrayList();
		List<User> users = userMapper.selectList(null);
		for (int i = 0; i < users.size(); i++) {
			userIds.add(users.get(i).getUserId());
		}
		String[] ids = userIds.toArray(new String[userIds.size()]);
//		获取考勤信息
		getData.getAttendanceData("month",20230101,20230116,ids);
		Map<String, UserAttendanceData> attendanceData = getData.getAttendanceData();
		return attendanceData.values();
    }

	public PageInfo<UserAttendanceData> getDataByPage(Integer pageNum) throws Exception {
		//开启分页功能，自动的对PageHelper.startPage 方法下的第一个sql 查询进行分页
		PageHelper.startPage(pageNum, 10);
		List<UserAttendanceData> list = userAttendanceDataMapper.selectList(null);
		//获取分页相关数据
		PageInfo<UserAttendanceData> page = new PageInfo(list, 5);
		return page;
	}

	/**
	 * @param :
	 * @return void
	 * @description 按月获取考勤信息（暂定），并将考勤信息存储到数据库
	 */
	public Boolean getDataByMonth(String startDate, String endDate) throws Exception {
//		清空数据库中的考勤信息，避免数据重复
		userAttendanceDataMapper.delete(null);
//		从数据库获取所有用户id
		ArrayList<String> userIds = new ArrayList();
		List<User> users = userMapper.selectList(null);
		for (int i = 0; i < users.size(); i++) {
			userIds.add(users.get(i).getUserId());
		}
		String[] ids = userIds.toArray(new String[userIds.size()]);

//		获取考勤信息
		if (startDate != null && !startDate.equals("") && endDate != null && !endDate.equals("")) {
			Boolean flag = getData.getAttendanceData("month", Integer.parseInt(startDate), Integer.parseInt(endDate), ids);
			if (flag == false) {
				return false;
			}
		}
		else {
			getData.getAttendanceData("month",20230101,20230201,ids);
		}
		Map<String, UserAttendanceData> attendanceData = getData.getAttendanceData();
		Collection<UserAttendanceData> values = attendanceData.values();
		List<UserAttendanceData> userAttendanceDataList = Arrays.asList(values.toArray(new UserAttendanceData[values.size()]));

//		将考勤信息存储到数据库
		for (int i = 0; i < userAttendanceDataList.size(); i++) {
			UserAttendanceData userAttendanceData = userAttendanceDataList.get(i);
			userAttendanceDataMapper.insert(userAttendanceData);
		}
		return true;
	}

	public PageInfo<UserAttendanceData> getDataByName(String name, Integer pageNum){
		QueryWrapper<UserAttendanceData> qw = new QueryWrapper();
		PageHelper.startPage(pageNum, 10);
		List<UserAttendanceData> list = userAttendanceDataMapper.selectList(qw.eq("name", name));
		PageInfo page = new PageInfo(list, 5);
		return page;
	}

	public PageInfo<UserAttendanceData> getDataByDesc(String sortType, String sortCol, Integer pageNum) throws Exception {
		PageHelper.startPage(pageNum, 10, sortCol + " " + sortType);
		List<UserAttendanceData> list = userAttendanceDataMapper.selectList(null);
		PageInfo page = new PageInfo(list, 5);
		return page;
	}

    /**
	 * @param :
	 * @return void
	 * @description 将员工id保存到数据库
	 */
    public void saveIds() throws Exception {
//    	清空数据库中的用户id信息，避免数据重复
    	userMapper.delete(null);

		recurve.getUserIdByRecurve("0");
		ArrayList<String> userIds = recurve.users;
		for (int i = 0; i < userIds.size(); i++) {
			User user = new User(userIds.get(i));
			userMapper.insert(user);
		}
	}
}
