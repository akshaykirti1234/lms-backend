package com.csmtech.service;

import java.util.List;

import com.csmtech.dto.ModuleMasterDto;
import com.csmtech.entity.ModuleMaster;

public interface ModuleMasterService {

	List<ModuleMaster> getModuleMaster();

	ModuleMaster saveModule(ModuleMasterDto moduleMasterDto);

	ModuleMaster getModuleById(Integer moduleId);

	void deleteModuleById(Integer moduleId);

}
