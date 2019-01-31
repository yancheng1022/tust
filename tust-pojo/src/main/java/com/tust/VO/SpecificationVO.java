package com.tust.VO;



import com.tust.pojo.Specification;
import com.tust.pojo.specificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/9 10:16
 * @Description: 规格组合实体类  规格+规格选项
 */
@Data
public class SpecificationVO implements Serializable {
    private Specification specification;
    private List<specificationOption> specificationOptionList;

}
