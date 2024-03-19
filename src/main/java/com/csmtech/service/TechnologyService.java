package com.csmtech.service;

import java.util.List;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;

public interface TechnologyService {

	List<Technology> getAllTechnologies();

	Technology saveTechnology(TechnologyDto technologyDto);

	void deleteTechnology(Integer tecId);

	Technology editTechnology(Integer tecId);

}
