package com.cetc.plan.demand.model.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.utils.PageUtils;
import lombok.Data;

import java.util.List;

/**
 * @Classname ParamEntity 参数实体类
 * @Description: TODO
 * @author: kg
 * @Date: 2019/7/8 8:38
 */
@Data
public class ParamEntity extends PageUtils {
    /*模糊搜索 国家城市*/
    private String countryName;

    /*区域目标*/
    private Double leftLng;
    private Double leftLat;
    private Double rightLng;
    private Double rightLat;

    /*需求列表  复杂项*/
    private List<Integer> xqzt; //需求状态 多个
    private List<Integer> ly; //来源 多个

    private String rksjstart;//入库开始时间
    private String rksjend;//入库结束时间
    private String yxqkssj;//有效期开始时间
    private String yxqjssj;//有效期结束时间


    private String vagueName; //模糊搜索的名称
    private String countFlag;//查询总数

    //需求列表排序字段 1：入库时间倒序 2：入库时间正序 3：有效期开始时间倒序 4：有效期开始时间正序 默认1
    private Integer sortRule = 1;


    public ParamEntity(){

    }
    public ParamEntity(List<Integer> xqzt,List<Integer> ly,String countryName,String vagueName,String yxqkssj,String yxqjssj,Integer sortRule){
        this.xqzt = xqzt;
        this.ly = ly;
        this.vagueName = vagueName;
        this.countryName = countryName;
        this.yxqkssj = yxqkssj;
        this.yxqjssj = yxqjssj;
        this.sortRule = sortRule;
    }

    public ParamEntity(Double leftLng,Double leftLat,Double rightLng,Double rightLat){
        this.leftLng = leftLng;
        this.leftLat = leftLat;
        this.rightLng = rightLng;
        this.rightLat = rightLat;
    }
    public ParamEntity(List<?> list, int totalCount, int pageSize, int currentPage) {
        super(list, totalCount, pageSize, (currentPage-1)*pageSize);
    }

    public ParamEntity(Page<?> page) {
        super(page);
    }
}
