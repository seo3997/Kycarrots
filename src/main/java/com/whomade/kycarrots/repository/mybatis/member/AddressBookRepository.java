package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.entity.member.TbAddressBookVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AddressBookRepository {
    List<DataMap> selectAddressList(DataMap param);

    DataMap selectAddress(DataMap param);

    DataMap selectDefaultAddress(DataMap param);

    void insertAddress(TbAddressBookVo vo);

    void updateAddress(TbAddressBookVo vo);

    void deleteAddress(DataMap param);

    void updateResetDefault(DataMap param);

    int selectAddressCount(DataMap param);
}
