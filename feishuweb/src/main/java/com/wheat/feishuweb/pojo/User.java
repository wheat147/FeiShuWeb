package com.wheat.feishuweb.pojo;

import lombok.Data;

/**
 * @projectName: FeiShuWeb
 * @package: com.wheat.feishuweb.pojo
 * @className: User
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/30 17:26
 * @version: 1.0
 */
@Data
public class User {

    private String userId;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }
}
