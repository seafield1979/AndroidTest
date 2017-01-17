package com.sunsunsoft.shutaro.testxmlparser;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by shutaro on 2016/12/17.
 *
 * xmlのカード情報
 * Simple-XMLで読み込んだ先を格納するフォーマット
 */

@Root
public class XmlTangoCard {
    @Element
    private String wordA;
    @Element
    private String wordB;
    @Attribute
    private String comment;

    public String getWordA() {
        return wordA;
    }
    public void setWordA(String wordA) { this.wordA = wordA; }

    public String getWordB() {
        return wordB;
    }
    public void setWordB(String wordB) { this.wordB = wordB; }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) { this.comment = comment; }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("wordA:" + wordA + "\n");
        buf.append("wordB:" + wordB + "\n");
        return buf.toString();
    }
}
