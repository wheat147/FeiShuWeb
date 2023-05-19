package com.wheat.feishuweb.service.function;

import com.lark.oapi.service.attendance.v1.model.UserStatsData;
import com.lark.oapi.service.attendance.v1.model.UserStatsDataCell;
import com.wheat.feishuweb.service.attendance.GetAttendance;
import com.wheat.feishuweb.pojo.UserAttendanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @projectName: FeiShu_CopyDept
 * @package: com.wheat.function
 * @className: GetData
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/17 9:26
 * @version: 1.0
 */
@Component
public class GetData {

    @Autowired
    private GetAttendance getAttendance;

//    定义出勤信息包装类
    public UserAttendanceData userAttendanceData;
//    存储 用户id 和 出勤信息包装类 的映射关系
    private HashMap<String,UserAttendanceData> attendanceData = new HashMap();

    public HashMap<String, UserAttendanceData> getAttendanceData() {
        return attendanceData;
    }

    public Boolean getAttendanceData(String statsType, Integer startDate, Integer endDate, String[] userIds) throws Exception {

//        定义临时变量
        String userId, userName, code, value;
        Iterator<UserStatsData> statsDataIterator = getAttendance.getAttendance(statsType, startDate, endDate, userIds);
        if(statsDataIterator == null) {
            return false;
        }

        if (statsDataIterator != null) {
            while (statsDataIterator.hasNext()) {
//                将获取到的 出勤信息 放入到 包装类 中
                UserStatsData userStatsData = statsDataIterator.next();
                userAttendanceData = new UserAttendanceData();
                userId = userStatsData.getUserId();
                userName = userStatsData.getName();
                userAttendanceData.setId(userId);
                userAttendanceData.setName(userName);
                Iterator<UserStatsDataCell> cellIterator = Arrays.stream(userStatsData.getDatas()).iterator();
                while (cellIterator.hasNext()) {
                    UserStatsDataCell dataCell = cellIterator.next();
                    code = dataCell.getCode();
                    value = dataCell.getValue();
                    switch (code) {
//                        应出勤时长
                        case "52104":
                            userAttendanceData.setTimeOfAttendanceRequired(Double.parseDouble(value));
                            break;
//                        实际出勤时长
                        case "52105":
                            userAttendanceData.setTimeOfAttendanceActually(Double.parseDouble(value));
                            break;
//                        应出勤天数
                        case "52101":
                            userAttendanceData.setDaysOfAttendanceRequired(Integer.parseInt(value));
                            break;
//                        实际出勤天数
                        case "52102":
                            userAttendanceData.setDaysOfAttendanceActually(Integer.parseInt(value));
                            break;
//                        迟到次数
                        case "52201":
                            userAttendanceData.setNumOfEarlyDismissals(Integer.parseInt(value));
                            break;
//                        早退次数
                        case "52203":
                            userAttendanceData.setNumOfLate(Integer.parseInt(value));
                            break;
//                        缺勤天数
                        case "52207": {
//                            去除字符串末尾的 “天” 字（原字符串为 “x天”），否则会报 NumberFormatException
                            value = value.substring(0, value.length() - 1);
//                            删除开头和结尾的空格
                            value = value.trim();
                            userAttendanceData.setDaysOfAbsence(Integer.parseInt(value));
                            break;
                        }
                    }
                }
//                存储包装好的 出勤信息类
                attendanceData.put(userId, userAttendanceData);

            }
        }
        return true;
    }
}
