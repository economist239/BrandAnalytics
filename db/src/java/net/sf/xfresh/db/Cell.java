package net.sf.xfresh.db;

/**
 * Date: Nov 9, 2010
 * Time: 1:36:33 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class Cell {
    private String name;
    private Object value;

    public Cell(final String name, final Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
