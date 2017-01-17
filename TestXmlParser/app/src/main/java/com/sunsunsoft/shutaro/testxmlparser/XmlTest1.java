package com.sunsunsoft.shutaro.testxmlparser;

/**
 * Created by shutaro on 2017/01/17.
 *
 * Xmlテスト
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
public class XmlTest1 {
    @Element
    private String name;

    @Element(required = false)
    private String comment;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    // for Debug
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("name:" + name + "\n");
        buf.append("comment:" + comment + "\n");
        return buf.toString();
    }
}

