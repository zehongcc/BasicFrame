package com.czh.basicframe.adapter;

/**
 * author  : czh
 * create Date : 2019/8/30  15:52
 * 详情 : 上传后的附件json
 */
public class FileJsonBean {

    /**
     * name : v2-a9369d993087b322ac3680bac4bb2866_hd.gif
     * path : http://localhost:30304//2019/812//90c0ddfd2582473fb9a28db87d9e1aad.gif
     * exName : .gif
     * size : 3128568
     */

    private String name;
    private String path;
    private String exName;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
