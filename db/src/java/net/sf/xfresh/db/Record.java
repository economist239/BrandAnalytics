package net.sf.xfresh.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: Nov 9, 2010
 * Time: 1:35:03 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class Record {
    private final List<Cell> cells = new ArrayList<Cell>();

    public void addCell(final Cell cell) {
        cells.add(cell);
    }

    public void addCell(final String name, final Object value) {
        addCell(new Cell(name, value));
    }

    public List<Cell> getCells() {
        return cells;
    }
}
