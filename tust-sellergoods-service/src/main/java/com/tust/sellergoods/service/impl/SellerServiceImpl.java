package com.tust.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.mapper.SellerMapper;
import com.tust.pojo.PageResult;
import com.tust.pojo.Seller;
import com.tust.pojo.SellerExample;
import com.tust.sellergoods.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
//@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public List<Seller> findAll() {
        return sellerMapper.selectByExample(null);
    }

    @Override
    public PageInfo findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Seller> sellers = sellerMapper.selectByExample(null);
        return new PageInfo(sellers);
    }

    @Override
    public void add(Seller seller) {
        seller.setStatus("0");
        seller.setCreateTime(new Date());
        sellerMapper.insert(seller);
    }

    @Override
    public void update(Seller seller) {
        sellerMapper.updateByPrimaryKey(seller);
    }

    @Override
    public Seller findOne(String id) {
        return sellerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(String[] ids) {
        for (String id:ids){
            sellerMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageInfo search(Seller seller, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SellerExample example=new SellerExample();
        SellerExample.Criteria criteria = example.createCriteria();

        if(seller!=null){
            if(seller.getSellerId()!=null && seller.getSellerId().length()>0){
                criteria.andSellerIdLike("%"+seller.getSellerId()+"%");
            }
            if(seller.getName()!=null && seller.getName().length()>0){
                criteria.andNameLike("%"+seller.getName()+"%");
            }
            if(seller.getNickName()!=null && seller.getNickName().length()>0){
                criteria.andNickNameLike("%"+seller.getNickName()+"%");
            }
            if(seller.getPassword()!=null && seller.getPassword().length()>0){
                criteria.andPasswordLike("%"+seller.getPassword()+"%");
            }
            if(seller.getEmail()!=null && seller.getEmail().length()>0){
                criteria.andEmailLike("%"+seller.getEmail()+"%");
            }
            if(seller.getMobile()!=null && seller.getMobile().length()>0){
                criteria.andMobileLike("%"+seller.getMobile()+"%");
            }
            if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
                criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
            }
            if(seller.getStatus()!=null && seller.getStatus().length()>0){
                criteria.andStatusLike("%"+seller.getStatus()+"%");
            }
            if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
                criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
            }
            if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
                criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
            }
            if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
                criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
            }
            if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
                criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
            }
            if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
                criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
            }
            if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
                criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
            }
            if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
                criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
            }
            if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
                criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
            }
            if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
                criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
            }
            if(seller.getBrief()!=null && seller.getBrief().length()>0){
                criteria.andBriefLike("%"+seller.getBrief()+"%");
            }
            if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
                criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
            }
            if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
                criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
            }
            if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
                criteria.andBankUserLike("%"+seller.getBankUser()+"%");
            }
            if(seller.getBankName()!=null && seller.getBankName().length()>0){
                criteria.andBankNameLike("%"+seller.getBankName()+"%");
            }

        }
        List<Seller> sellers = sellerMapper.selectByExample(example);
        return new PageInfo(sellers);
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
        seller.setStatus(status);
        sellerMapper.updateByPrimaryKey(seller);
    }
}