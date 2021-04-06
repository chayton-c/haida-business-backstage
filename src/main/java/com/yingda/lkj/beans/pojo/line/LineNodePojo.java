package com.yingda.lkj.beans.pojo.line;

import com.yingda.lkj.beans.entity.backstage.line.Fragment;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLine;
import com.yingda.lkj.beans.entity.backstage.line.Station;
import com.yingda.lkj.utils.StreamUtil;
import lombok.Data;

import java.util.List;

/**
 * 线路管理页，把线路和区间都装进去
 *
 * @author hood  2020/1/2
 */
public class LineNodePojo {
    private static final byte RAILWAY_LINE = 0;
    private static final byte STATION = 1;

    private String id;
    private String name;
    private byte type;
    private List<LineNodePojo> lineNodePojoList;

    public LineNodePojo(RailwayLine railwayLine, List<Station> stations) {
        this.id = railwayLine.getId();
        this.name = railwayLine.getName();
        this.type = RAILWAY_LINE;
        this.lineNodePojoList = StreamUtil.getList(stations, LineNodePojo::new);
    }

    public LineNodePojo(Station station) {
        this.id = station.getId();
        this.name = station.getName();
        this.type = STATION;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public List<LineNodePojo> getLineNodePojoList() {
        return lineNodePojoList;
    }

    public void setLineNodePojoList(List<LineNodePojo> lineNodePojoList) {
        this.lineNodePojoList = lineNodePojoList;
    }
}
