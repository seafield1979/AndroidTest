package com.sunsunsoft.shutaro.testxmlparser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by shutaro on 2017/01/17.
 */

@Root
public class XmlTest12 {
    @Element
    private String name;

    @ElementList(required = false)
    private List<Test1> list;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<Test1> list) {
        this.list = list;
    }


    // for Debug
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("name:" + name + "\n");
        if (list != null) {
            for (Test1 test : list) {
                buf.append(test.toString() + "\n");
            }
        }
        return buf.toString();
    }
}

