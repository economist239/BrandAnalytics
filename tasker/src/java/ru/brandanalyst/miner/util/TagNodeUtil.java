package ru.brandanalyst.miner.util;

/*
 *  Date: 21.02.12
 *  Time: 20:59
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.htmlcleaner.TagNode;

public class TagNodeUtil {
    public static String getText(TagNode tagNode) {
        StringBuilder sb = new StringBuilder();
        sb.append(tagNode.getText());
        for (TagNode childNode : tagNode.getChildTags()) {
            sb.append(getText(childNode));
        }
        return sb.toString();
    }
}
