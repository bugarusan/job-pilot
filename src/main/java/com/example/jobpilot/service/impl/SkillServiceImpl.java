package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.CreateSkillRequest;
import com.example.jobpilot.dto.request.UpdateSkillRequest;
import com.example.jobpilot.dto.response.SkillResponse;
import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.Skill;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.SkillNotFoundException;
import com.example.jobpilot.mapper.SkillMapper;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.SkillRepository;
import com.example.jobpilot.security.CurrentUserProvider;
import com.example.jobpilot.service.SkillService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final CvRepository cvRepository;
    private final SkillMapper skillMapper;
    private final CurrentUserProvider currentUserProvider;

    public SkillServiceImpl(SkillRepository skillRepository,
                            CvRepository cvRepository,
                            SkillMapper skillMapper,
                            CurrentUserProvider currentUserProvider) {
        this.skillRepository = skillRepository;
        this.cvRepository = cvRepository;
        this.skillMapper = skillMapper;
        this.currentUserProvider = currentUserProvider;
    }

    private void checkOwnership(Cv cv) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        if (!currentUserId.equals(cv.getUser().getId())) {
            throw new AccessDeniedException("Bu CV sənə aid deyil");
        }
    }

    @Override
    public SkillResponse create(Long cvId, CreateSkillRequest request) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(cv);

        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setLevel(request.getLevel());
        skill.setCv(cv);

        Skill saved = skillRepository.save(skill);

        return skillMapper.toResponse(saved);
    }

    @Override
    public SkillResponse getById(Long id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        checkOwnership(skill.getCv());

        return skillMapper.toResponse(skill);
    }

    @Override
    public List<SkillResponse> getByCvId(Long cvId) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(cv);

        return skillRepository.findByCvId(cvId)
                .stream()
                .map(skillMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SkillResponse update(Long id, UpdateSkillRequest request) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        checkOwnership(skill.getCv());

        if (request.getName() != null)
            skill.setName(request.getName());

        if (request.getLevel() != null)
            skill.setLevel(request.getLevel());

        Skill updated = skillRepository.save(skill);

        return skillMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        checkOwnership(skill.getCv());

        skillRepository.delete(skill);
    }
}