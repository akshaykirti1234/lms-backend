package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.ModuleMasterDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.repository.ModuleMasterRepository;

@Service
public class ModuleMasterServiceImpl implements ModuleMasterService {

	@Autowired
	private ModuleMasterRepository moduleMasterRepository;

	@Override
	public List<ModuleMaster> getModuleMaster() {

		return moduleMasterRepository.findAllModule();
	}

	@Override
	public ModuleMaster saveModule(ModuleMasterDto moduleMasterDto) {

		ModuleMaster moduleMaster = new ModuleMaster();
		if (moduleMasterDto.getModuleId() != null) {
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
		return moduleMasterRepository.findById(moduleId).get();
	}

	@Override
	public void deleteModuleById(Integer moduleId) {
		moduleMasterRepository.deleteModuleById(moduleId);

	}

}
