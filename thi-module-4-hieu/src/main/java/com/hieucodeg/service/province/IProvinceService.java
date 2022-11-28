package com.hieucodeg.service.province;

import com.hieucodeg.model.Province;
import com.hieucodeg.service.IGeneralService;

import java.util.List;

public interface IProvinceService extends IGeneralService<Province> {
    List<Province> findAllByDeletedIsFalse();
}
