package net.yuanjin.widgetlib.imageviewpager;

import java.io.Serializable;
import java.util.List;

/**
 *  Created by WuZhanQiang on 2016/9/22.
 */
public class ImageUriParams implements Serializable {

    private static final long serialVersionUID = 1L;
    public List<ImageUri> uris;
    public ImageUriParams(List<ImageUri> uris) {
        this.uris = uris;
    }
}
