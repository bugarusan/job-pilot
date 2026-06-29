package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.Skill;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.SkillNotFoundException;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.SkillRepository;
import com.example.jobpilot.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final CvRepository cvRepository;

    public SkillServiceImpl(SkillRepository skillRepository, CvRepository cvRepository) {
        this.skillRepository = skillRepository;
        this.cvRepository = cvRepository;
    }


    @Override
    public Skill create(Long cvId, Skill skill) {
        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        skill.setCv(cv);
        return skillRepository.save(skill);
    }

    @Override
    public Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));
    }

    @Override
    public List<Skill> getByCvId(Long cvId) {
        cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        return skillRepository.findByCvId(cvId);
    }

    @Override
    public Skill update(Long id, Skill skill) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));
        existingSkill.setName(skill.getName());
        existingSkill.setLevel(skill.getLevel());

        return skillRepository.save(existingSkill);
    }

    @Override
    public void delete(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));
        skillRepository.delete(skill);
    }
}
