package com.whomade.kycarrots.front.member.service;

import com.whomade.kycarrots.entity.member.TbAddressBookVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.member.AddressBookRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("addressBookService")
public class AddressBookServiceImpl implements AddressBookService {

    @Resource
    private AddressBookRepository addressBookRepository;

    @Override
    public List<DataMap> selectAddressList(DataMap param) throws Exception {
        return addressBookRepository.selectAddressList(param);
    }

    @Override
    public DataMap selectAddress(DataMap param) throws Exception {
        return addressBookRepository.selectAddress(param);
    }

    @Override
    public DataMap selectDefaultAddress(DataMap param) throws Exception {
        return addressBookRepository.selectDefaultAddress(param);
    }

    @Override
    @Transactional
    public void insertAddress(TbAddressBookVo vo) throws Exception {
        if (vo.getAddressName() == null || vo.getAddressName().trim().isEmpty()) {
            vo.setAddressName("기본배송지");
        }
        DataMap param = new DataMap();
        param.put("userNo", vo.getUserNo());
        int count = addressBookRepository.selectAddressCount(param);

        // 첫 등록이면 무조건 기본으로 설정
        if (count == 0) {
            vo.setIsDefault(1);
        }

        addressBookRepository.insertAddress(vo);

        // 기본 배송지로 설정된 경우 다른 주소들 초기화
        if (vo.getIsDefault() != null && vo.getIsDefault() == 1) {
            param.put("addressId", vo.getAddressId()); // Generated ID should be populated if properly configured
            addressBookRepository.updateResetDefault(param);
        }
    }

    @Override
    @Transactional
    public void updateAddress(TbAddressBookVo vo) throws Exception {
        if (vo.getAddressName() == null || vo.getAddressName().trim().isEmpty()) {
            vo.setAddressName("기본배송지");
        }
        addressBookRepository.updateAddress(vo);

        // 기본 배송지로 설정된 경우 다른 주소들 초기화
        if (vo.getIsDefault() != null && vo.getIsDefault() == 1) {
            DataMap param = new DataMap();
            param.put("userNo", vo.getUserNo());
            param.put("addressId", vo.getAddressId());
            addressBookRepository.updateResetDefault(param);
        }
    }

    @Override
    public void deleteAddress(DataMap param) throws Exception {
        addressBookRepository.deleteAddress(param);
    }

    @Override
    @Transactional
    public void setDefaultAddress(DataMap param) throws Exception {
        // 특정 주소를 기본으로 설정
        TbAddressBookVo vo = new TbAddressBookVo();
        vo.setAddressId(param.getLong("addressId"));
        vo.setUserNo(param.getString("userNo"));
        vo.setIsDefault(1);

        // updateAddress의 logic을 타거나 직접 reset 호출
        addressBookRepository.updateResetDefault(param);

        // 해당 ID의 IS_DEFAULT를 1로 업데이트
        DataMap address = addressBookRepository.selectAddress(param);
        if (address != null) {
            TbAddressBookVo updateVo = new TbAddressBookVo();
            updateVo.setAddressId(param.getLong("addressId"));
            updateVo.setUserNo(param.getString("userNo"));
            updateVo.setAddressName(address.getString("ADDRESS_NAME"));
            updateVo.setRecipientName(address.getString("RECIPIENT_NAME"));
            updateVo.setRecipientPhone(address.getString("RECIPIENT_PHONE"));
            updateVo.setZipCode(address.getString("ZIP_CODE"));
            updateVo.setAddressMain(address.getString("ADDRESS_MAIN"));
            updateVo.setAddressDetail(address.getString("ADDRESS_DETAIL"));
            updateVo.setIsDefault(1);
            updateVo.setMemo(address.getString("MEMO"));
            addressBookRepository.updateAddress(updateVo);
        }
    }
}
