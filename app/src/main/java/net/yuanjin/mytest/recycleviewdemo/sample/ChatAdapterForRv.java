package net.yuanjin.mytest.recycleviewdemo.sample;

import android.content.Context;

import net.yuanjin.mytest.recycleviewdemo.base.MsgComingItemDelagate;
import net.yuanjin.mytest.recycleviewdemo.base.MsgSendItemDelagate;
import net.yuanjin.mytest.recycleviewdemo.been.ChatMessage;

import java.util.List;

/**
 *  Created by WuZhanQiang on 2016/12/23.
 */

public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage>{


    public ChatAdapterForRv(Context context, List<ChatMessage> datas) {
        super(context, datas);

        addItemViewDelegate(new MsgComingItemDelagate());
        addItemViewDelegate(new MsgSendItemDelagate());
    }
}
