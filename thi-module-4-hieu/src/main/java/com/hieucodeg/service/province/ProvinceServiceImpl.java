package com.hieucodeg.service.province;

import com.hieucodeg.model.Province;
import com.hieucodeg.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class ProvinceServiceImpl implements IProvinceService{

    @Autowired
    private ProvinceRepository provinceRepository;
    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getById(Long id) {
        return provinceRepository.getReferenceById(id);
    }

    @Override
    public Optional<Province> findById(Long id) {
        return provinceRepository.findById(id);
    }

    @Override
    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<Province> findAllByDeletedIsFalse() {
        return provinceRepository.findAllByDeletedIsFalse();
    }
}
