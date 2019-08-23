package com.codefans.concurrency.servertomcat.web.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: codefans
 * @date: 2019-08-22 16:12
 */
@Setter
@Getter
@ToString
public class QpsInfo {

    private String uri;

    private String currentTime;

    private int count;

    public boolean equals(QpsInfo obj) {
        if(obj == null) {
            return false;
        }
        if(uri.equals(obj.uri) && currentTime.equals(obj.currentTime)) {
            return true;
        }
        return false;
    }

    public int hashcode() {
        String uniqueKey = uri + currentTime;
        if(uniqueKey != null) {
            return uniqueKey.hashCode();
        }
        return hashCode();
    }

}
