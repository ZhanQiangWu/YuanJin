package net.yuanjin.widgetlib.photolib.album;


import java.io.Serializable;

/**
 *  Created by WuZhanQiang on 2016/9/19.
 */
public class AlbumPhotoItem1 implements Serializable {

    private static final long  serialVersionUID = 8682674788506891598L;;
    private int photoID;
    private boolean select;
    private String name;
    private String path;

    public AlbumPhotoItem1(int id, String name, String path, boolean select) {
        photoID = id;
        this.path=path;
        this.select = select;
        this.setName(name);
    }

    public AlbumPhotoItem1(int id, boolean select) {
        photoID = id;
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

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

    @Override
    public String toString() {
        return "PhotoItem [photoID=" + photoID + ", select=" + select + "]";
    }
}
