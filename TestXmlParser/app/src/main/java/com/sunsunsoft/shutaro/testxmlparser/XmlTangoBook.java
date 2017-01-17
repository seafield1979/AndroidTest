package com.sunsunsoft.shutaro.testxmlparser;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by shutaro on 2016/12/17.
 *
 * xmlの単語帳
 * Simple-XMLで読み込んだ先を格納するフォーマット
 */

@Root
public class XmlTangoBook {
    @ElementList
    private List<XmlTangoCard> cards;

    @Attribute
    private String name;

    @Element
    private String comment;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<XmlTangoCard> getCards() {
        return cards;
    }
    public void setCards(List cards) {
        this.cards = cards;
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
        for (XmlTangoCard card : cards) {
            buf.append(card.toString());
        }
        return buf.toString();
    }
}

