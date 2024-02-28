package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.*;
import com.competa.competademo.exceptions.CompetaNotFoundException;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.CompetaRepository;
import com.competa.competademo.service.CompetaService;
import com.competa.competademo.service.CtypeService;
import com.competa.competademo.service.IndustryService;
import com.competa.competademo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
@RequiredArgsConstructor
@Service("competaService")
public class CompetaServiceImpl implements CompetaService<CompetaDto> {

    private final CompetaRepository competaRepository;
    private final UserService userService;
    private final IndustryService industryService;
    private final CtypeService<CtypeDto> ctypeService;


    @Transactional
    @Override
    public void addToAuthUser(CompetaDto competa) {
        final User authUser = userService.getAuthUser();
        UserProfile userProfile = authUser.getUserProfile();

        IndustryDto industryDto = industryService.getByIdIndustry(competa.getSelectedIndustryId());
        Industry industry = industryDto.toEntity();

        CtypeDto ctypeDto = ctypeService.findById(competa.getCtypeId()).orElseThrow(() -> new RuntimeException("Competa type not found"));
        Ctype ctype = ctypeDto.toEntity();

        Competa entity = competa.toEntity(industry, ctype);
        entity.setUserProfile(userProfile);
        entity = competaRepository.save(entity);
        userProfile.getCompetas().add(entity);
        userService.saveUser(authUser);
    }

    @Transactional
    @Override
    public void update(final long competaId, final CompetaDto competa) {
        final Competa competaToEdit = findCompeta(competaId);
        competaToEdit.setTitle(competa.getTitle());
        competaToEdit.setDescription(competa.getDescription());
        competaToEdit.setDateOut(competa.getDateOut());
        competaToEdit.setStatus(competa.isStatus());

        IndustryDto industryDto = industryService.getByIdIndustry(competa.getSelectedIndustryId());
        Industry industry = industryDto.toEntity();

        CtypeDto ctypeDto = ctypeService.findById(competa.getCtypeId()).orElseThrow(() -> new RuntimeException("Competa type not found"));
        Ctype ctype = ctypeDto.toEntity();

        competaToEdit.setIndustry(industry);
        competaToEdit.setCtype(ctype);
        competaRepository.save(competaToEdit);
    }

    @Override
    public void remove(long competaId) {
        findCompeta(competaId);
        competaRepository.deleteById(competaId);
    }

    @Override
    public CompetaDto getById(long competaId) {
        return null;
    }

    @Override
    public Optional<CompetaDto> findById(long competaId) {
        return Optional.of(
                new CompetaDto(findCompeta(competaId))
        );
    }

    @Override
    public List<CompetaDto> findAllByAuthUser() {
        final User authUser = userService.getAuthUser();
        return competaRepository.findAllByUser(authUser.getUserProfile())
                .stream()
                .map(CompetaDto::new)
                .toList();
    }

    @Override
    public int countByAuthUser() {
        final User authUser = userService.getAuthUser();
        return (int) competaRepository.countByUserProfile(authUser.getUserProfile());
    }

    @Override
    public void addCompetaImage(long competaId, ImageInfo competaImage) {
        Competa competa = findCompeta(competaId);
        competa.setCompetaImage(competaImage);
        competaRepository.save(competa);
    }

    @Override
    public String getCompetaImage(Competa competa) {
        final var filePath = Path.of(competa.getCompetaImage().getUrl());
        try {
            final UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                byte[] imageBytes = Files.readAllBytes(filePath);
                return Base64.getEncoder().encodeToString(imageBytes);
            }
        } catch (final IOException e) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "Error reading the avatar");
        }
        return "";
    }

    private Competa findCompeta(long competaId) {
        return competaRepository.findById(competaId)
                .orElseThrow(() -> new CompetaNotFoundException("Competa with id '{}' not exists", competaId));
    }
}
