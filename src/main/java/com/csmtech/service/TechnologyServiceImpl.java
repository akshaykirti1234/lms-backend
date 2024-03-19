
package com.csmtech.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;
import com.csmtech.repository.TechnologyRepository;

@Service
public class TechnologyServiceImpl implements TechnologyService {

	@Autowired
	private TechnologyRepository technologyRepository;

	@Override
	public List<Technology> getAllTechnologies() {
		return technologyRepository.getAllTechnologies();
	}

	@Override
	public Technology saveTechnology(TechnologyDto technologyDto) {
		Technology techMas = new Technology();
		techMas.setTechId(technologyDto.getTechId());
		techMas.setTechName(technologyDto.getTechName());
		techMas.setCreatedOn(new Date());
		techMas.setCreatedBy(1);
		techMas.setUpdatedOn(new Date());
		techMas.setUpdatedBy(1);
		techMas.setDeletedFlag(false);
		return technologyRepository.save(techMas);
	}

	@Override
	public void deleteTechnology(Integer tecId) {
		technologyRepository.deleteTechnology(tecId);
	}

	@Override
	public Technology editTechnology(Integer tecId) {
		return technologyRepository.findById(tecId).get();
	}

}
