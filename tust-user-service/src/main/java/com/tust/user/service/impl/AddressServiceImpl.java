package com.tust.user.service.impl;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tust.mapper.AddressMapper;
import com.tust.pojo.Address;
import com.tust.pojo.AddressExample;
import com.tust.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/29 16:06
 * @Description: 服务实现层
*/
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressMapper addressMapper;
	

	@Override
	public List<Address> findAll() {
		return addressMapper.selectByExample(null);
	}


	@Override
	public PageInfo findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Address> addresses = addressMapper.selectByExample(null);
		return new PageInfo(addresses);
	}


	@Override
	public void add(Address address) {
		addressMapper.insert(address);		
	}

	

	@Override
	public void update(Address address){
		addressMapper.updateByPrimaryKey(address);
	}	
	

	@Override
	public Address findOne(Long id){
		return addressMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			addressMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
	@Override
	public PageInfo findPage(Address address, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		AddressExample example=new AddressExample();
		AddressExample.Criteria criteria = example.createCriteria();
		
		if(address!=null){			
						if(address.getUserId()!=null && address.getUserId().length()>0){
				criteria.andUserIdLike("%"+address.getUserId()+"%");
			}
			if(address.getProvinceId()!=null && address.getProvinceId().length()>0){
				criteria.andProvinceIdLike("%"+address.getProvinceId()+"%");
			}
			if(address.getCityId()!=null && address.getCityId().length()>0){
				criteria.andCityIdLike("%"+address.getCityId()+"%");
			}
			if(address.getTownId()!=null && address.getTownId().length()>0){
				criteria.andTownIdLike("%"+address.getTownId()+"%");
			}
			if(address.getMobile()!=null && address.getMobile().length()>0){
				criteria.andMobileLike("%"+address.getMobile()+"%");
			}
			if(address.getAddress()!=null && address.getAddress().length()>0){
				criteria.andAddressLike("%"+address.getAddress()+"%");
			}
			if(address.getContact()!=null && address.getContact().length()>0){
				criteria.andContactLike("%"+address.getContact()+"%");
			}
			if(address.getIsDefault()!=null && address.getIsDefault().length()>0){
				criteria.andIsDefaultLike("%"+address.getIsDefault()+"%");
			}
			if(address.getNotes()!=null && address.getNotes().length()>0){
				criteria.andNotesLike("%"+address.getNotes()+"%");
			}
			if(address.getAlias()!=null && address.getAlias().length()>0){
				criteria.andAliasLike("%"+address.getAlias()+"%");
			}
	
		}

		List<Address> addresses = addressMapper.selectByExample(example);
		return new PageInfo(addresses);
	}

	@Override
	public List<Address> findListByUserId(String userId) {
		
		AddressExample example=new AddressExample();
		AddressExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return addressMapper.selectByExample(example);
	}
	
}
