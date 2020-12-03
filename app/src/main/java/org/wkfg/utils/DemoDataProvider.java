/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.wkfg.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.wkfg.R;
import org.wkfg.adapter.entity.NewInfo;
import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示数据
 *
 * @author xuexiang
 * @since 2018/11/23 下午5:52
 */
public class DemoDataProvider {

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    @MemoryCache
    public static List<BannerItem> getBannerList() {
        List<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            item.title = titles[i];

            list.add(item);
        }
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    public static List<NewInfo> getDemoNewInfos() {
        List<NewInfo> list = new ArrayList<>();
        list.add(new NewInfo("情感", "男朋友不陪我，我该生气吗")
                .setSummary("他说他很忙，这段时间没空")
                .setDetailUrl("https://juejin.im/post/5c6fc0cdf265da2dda694f05")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/2/22/16914891cd8a950a?imageView2/0/w/1280/h/960/format/webp/ignore-error/1")
                .setThreadID("#000001"));

        list.add(new NewInfo("体育", "皮尔斯：\"勒布朗詹姆斯排不上历史前五\"")
                .setSummary("皮尔斯说的对吗")
                .setDetailUrl("https://juejin.im/post/5c3ed1dae51d4543805ea48d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/1/16/1685563ae5456408?imageView2/0/w/1280/h/960/format/webp/ignore-error/1")
                .setThreadID("#000002"));

        list.add(new NewInfo("音乐", "CDC Cypher大家听了吗")
                .setSummary("法老牛逼")
                .setDetailUrl("https://juejin.im/post/5ca4df966fb9a05e4e58320c")
                .setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554629219186&di=6cdab5cfceaae1f7e6d78dbe79104c9f&imgtype=0&src=http%3A%2F%2Fimg.qinxue365.com%2Fuploads%2Fallimg%2F1902%2F4158-1Z22FZ64E00.jpg")
                .setThreadID("#000003"));

        list.add(new NewInfo("社会", "你们怎么看绿地事件")
                .setSummary("这是蠢还是坏")
                .setDetailUrl("https://juejin.im/post/5b480b79e51d45190905ef44")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/7/13/16492d9b7877dc21?imageView2/0/w/1280/h/960/format/webp/ignore-error/1")
                .setThreadID("#000004"));

        list.add(new NewInfo("校园", "你们想返校吗")
                .setSummary("在家葛优躺5个月了哈哈")
                .setDetailUrl("https://juejin.im/post/5b6b9b49e51d4576b828978d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/8/9/1651c568a7e30e02?imageView2/0/w/1280/h/960/format/webp/ignore-error/1")
                .setThreadID("#000005"));



        // Hybernate 把一个表映射成一个类 ORM object relation mapping， 不禁需要import，还需要在project里面配置好
        // 映射有两种形式，一种是通过配置文件.xml，配置好类和映射，另一种是通过注释，以@开头，不需要配置文件，只需要写实体类，实体类前有@
        // Spring Spring.data.jpa java提供的连接数据库的借口，用法麻烦一些（默认底层框架还是hybernate）
        return list;
    }

    public static List<AdapterItem> getGridItems(Context context) {
        return getGridItems(context, R.array.grid_titles_entry, R.array.grid_icons_entry);
    }


    private static List<AdapterItem> getGridItems(Context context, int titleArrayId, int iconArrayId) {
        List<AdapterItem> list = new ArrayList<>();
        String[] titles = ResUtils.getStringArray(titleArrayId);
        Drawable[] icons = ResUtils.getDrawableArray(context, iconArrayId);
        for (int i = 0; i < titles.length; i++) {
            list.add(new AdapterItem(titles[i], icons[i]));
        }
        return list;
    }

}
