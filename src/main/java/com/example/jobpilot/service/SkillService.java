package com.example.jobpilot.service;

import com.example.jobpilot.entity.Skill;

import java.util.List;

public interface SkillService {
    Skill create(Long cvId, Skill skill);

    Skill getById(Long id);

    List<Skill> getByCvId(Long cvId);

    Skill update(Long id, Skill skill);

    void delete(Long id);
}
