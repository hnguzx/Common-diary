package pers.guzx.notice.entity;

import lombok.Getter;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/3 14:08
 * @describe
 */
@Getter
public enum NoticeType {
    LOGIN("login"), REGISTRY("registry"),OTHER("other");
    private String type;

    NoticeType(String type) {
        this.type = type;
    }
}
