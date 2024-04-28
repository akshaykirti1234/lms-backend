package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.ModuleMasterDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.repository.ModuleMasterRepository;

@Service
public class ModuleMasterServiceImpl implements ModuleMasterService {
	
	Logger logger=LoggerFactory.getLogger(ModuleMasterServiceImpl.class);

	@Autowired
	private ModuleMasterRepository moduleMasterRepository;

	@Override
	public List<ModuleMaster> getModuleMaster() {

		return moduleMasterRepository.findAllModule();
	}

	@Override
	public ModuleMaster saveModule(ModuleMasterDto moduleMasterDto) {
		logger.info("saveModule method of ModuleMasterServiceImpl is executed");
		ModuleMaster moduleMaster = new ModuleMaster();
		if (moduleMasterDto.getModuleId() != 0) {
			moduleMaster.setModuleId(moduleMasterDto.getModuleId());
			moduleMaster.setUpdatedBy(1);

		}
		moduleMaster.setModuleName(moduleMasterDto.getModuleName());
		moduleMaster.setModuleDescription(moduleMasterDto.getModuleDescription());
		moduleMaster.setLogo(moduleMasterDto.getLogo());
		moduleMaster.setCreatedBy(1);

		return moduleMasterRepository.save(moduleMaster);
	}

	@Override
	public ModuleMaster getModuleById(Integer moduleId) {
		logger.info("getModuleById method of ModuleMasterServiceImpl is executed");
		return moduleMasterRepository.findById(moduleId).get();
	}

	@Override
	public void deleteModuleById(Integer moduleId) {
		logger.info("deleteModuleById method of ModuleMasterServiceImpl is executed");
		moduleMasterRepository.deleteModuleById(moduleId);

	}

}
