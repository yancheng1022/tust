package com.tust.VO;

import com.tust.pojo.Goods;
import com.tust.pojo.GoodsDesc;
import com.tust.pojo.Item;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsVO implements Serializable {
    private Goods goods;
    private GoodsDesc goodsDesc;  //商品SPU拓展信息
    private List<Item>itemList; //SKU列表
}
