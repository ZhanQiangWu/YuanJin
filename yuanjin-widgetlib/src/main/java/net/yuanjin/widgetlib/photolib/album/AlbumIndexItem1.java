package net.yuanjin.widgetlib.photolib.album;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by WuZhanQiang on 2016/9/19.
 */
public class AlbumIndexItem1 implements Serializable {

    private static final long serialVersionUID =1L;
    private String dir_id ;//相册id
    private String name ;//相册名字
    private String count ; //数量
    private String bitmap;//相册第一张 ,文件路径
    private boolean selected;
    private List<AlbumPhotoItem1> bitList=new ArrayList<AlbumPhotoItem1>();

    public AlbumIndexItem1() {
    }

    public AlbumIndexItem1(String name, String count, String bitmap) {
        super();
        this.name = name;
        this.count = count;
        this.bitmap = bitmap;
    }

    public String getDir_id() {
        return dir_id;
    }

    public void setDir_id(String dir_id) {
        this.dir_id = dir_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * 获取图片信息列表
     * @return ：List<AlbumPhotoItem1>
     */
    public List<AlbumPhotoItem1> getBitList() {
        return bitList;
    }

    public void setBitList(List<AlbumPhotoItem1> bitList) {
        this.bitList = bitList;
    }

    /**
     * 获取当前被选择图片的Uri（path）集合
     * @return  ：List<String>
     */
    public List<String> getSelectedItemUris(){
        List<String> uris =new ArrayList<String>();
        for (AlbumPhotoItem1 item :getBitList()){
            if (item.isSelect()){
                uris.add(item.getPath());
            }
        }
        return uris;
    }

    /**
     * 获取当前被选择图片的名字集合
     * @return ： List<String>
     */
    public List<String> getSelectedItemIds(){
        List<String> ids =new ArrayList<String>();
        for (AlbumPhotoItem1 item:getBitList()){
            if (item.isSelect()){
                ids.add(item.getName());
            }
        }
        return ids;
    }
}
