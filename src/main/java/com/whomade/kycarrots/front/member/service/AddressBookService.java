package com.whomade.kycarrots.front.member.service;

import com.whomade.kycarrots.entity.member.TbAddressBookVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import java.util.List;

public interface AddressBookService {
    List<DataMap> selectAddressList(DataMap param) throws Exception;

    DataMap selectAddress(DataMap param) throws Exception;

    DataMap selectDefaultAddress(DataMap param) throws Exception;

    void insertAddress(TbAddressBookVo vo) throws Exception;

    void updateAddress(TbAddressBookVo vo) throws Exception;

    void deleteAddress(DataMap param) throws Exception;

    void setDefaultAddress(DataMap param) throws Exception;
}
