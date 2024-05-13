
package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;
import com.csmtech.repository.TechnologyRepository;

@Service
public class TechnologyServiceImpl implements TechnologyService {
	
	private static final Logger logger=LoggerFactory.getLogger(TechnologyServiceImpl.class);

	@Autowired
	private TechnologyRepository technologyRepository;

	@Override
	public List<Technology> getAllTechnologies() {
		logger.info("getAllTechnologies method of TechnologyServiceImpl is executed");
		return technologyRepository.getAllTechnologies();
	}

	@Override
	public Technology saveTechnology(TechnologyDto technologyDto) {
		logger.info("saveTechnology method of TechnologyServiceImpl is executed");
		Technology techMas = new Technology();
		techMas.setTechId(technologyDto.getTechId());
		techMas.setTechName(technologyDto.getTechName());
		techMas.setCreatedBy(1);
		techMas.setUpdatedBy(1);
		return technologyRepository.save(techMas);
	}

	@Override
	public void deleteTechnology(Integer tecId) {
		logger.info("deleteTechnology method of TechnologyServiceImpl is executed");
		technologyRepository.deleteTechnology(tecId);
	}

	@Override
	public Technology editTechnology(Integer tecId) {
		logger.info("editTechnology method of TechnologyServiceImpl is executed");
		return technologyRepository.findById(tecId).get();
	}

}
