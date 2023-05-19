package com.wheat.feishuweb.pojo;

import lombok.Data;

/**
 * @projectName: FeiShu_CopyDept
 * @package: com.wheat.feishuweb.pojo
 * @className: UserAttendanceData
 * @author: Wheat
 * @description: TODO: 将人员的 出勤信息 包装成一个类
 * @date: 2023/1/17 10:02
 * @version: 1.0
 */
@Data
public class UserAttendanceData {
    private String id;                          //用户id
    private String name;                        //用户姓名
    private Double timeOfAttendanceRequired;    //应出勤时长（小时）
    private Double timeOfAttendanceActually;    //实际出勤时长（小时）
    private Integer daysOfAttendanceRequired;   //应出勤天数
    private Integer daysOfAttendanceActually;   //实际出勤天数
    private Integer numOfLate;                  //迟到次数
    private Integer numOfEarlyDismissals;       //早退次数
    private Integer daysOfAbsence;              //缺勤天数

    public UserAttendanceData() {
    }

    public UserAttendanceData(String id, String name, Double timeOfAttendanceRequired, Double timeOfAttendanceActually, Integer daysOfAttendanceRequired, Integer daysOfAttendanceActually, Integer numOfLate, Integer numOfEarlyDismissals, Integer daysOfAbsence) {
        this.id = id;
        this.name = name;
        this.timeOfAttendanceRequired = timeOfAttendanceRequired;
        this.timeOfAttendanceActually = timeOfAttendanceActually;
        this.daysOfAttendanceRequired = daysOfAttendanceRequired;
        this.daysOfAttendanceActually = daysOfAttendanceActually;
        this.numOfLate = numOfLate;
        this.numOfEarlyDismissals = numOfEarlyDismissals;
        this.daysOfAbsence = daysOfAbsence;
    }
}
