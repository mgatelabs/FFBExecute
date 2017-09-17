package com.mgatelabs.ffbe.shared.mapper;

/**
 * Used to bring in
 *
 * Created by @mgatelabs (Michael Fuller) on 9/10/2017.
 */
public class MapSampleArea {

    private TileDefinition area [][];

    public MapSampleArea(String definition) {

        final String [] lines = definition.split("-");
        area = new TileDefinition[lines.length][lines[0].length()];
        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[y].length(); x++) {
                TileDefinition def = new TileDefinition();
                switch (lines[y].charAt(x)) {
                    case '?': {
                        def.setType(TileType.UNKNOWN);
                    } break;
                    case '#': {
                        def.setType(TileType.WALL);
                    } break;
                    case 'R': {
                        def.setType(TileType.EXIT);
                    } break;
                    case 'G': {
                        def.setType(TileType.START);
                    } break;
                    case 'B': {
                        def.setType(TileType.FLOOR);
                    } break;
                }
            }
        }
    }

    public TileDefinition getTile(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return area[y][x];
        }
        return null;
    }

    public int getWidth() {
        if (area != null && area.length > 0) {
            return area[0].length;
        }
        return 0;
    }

    public int getHeight() {
        if (area != null) {
            return area.length;
        }
        return 0;
    }

    public TileDefinition[][] getArea() {
        return area;
    }

    public void setArea(TileDefinition[][] area) {
        this.area = area;
    }
}
