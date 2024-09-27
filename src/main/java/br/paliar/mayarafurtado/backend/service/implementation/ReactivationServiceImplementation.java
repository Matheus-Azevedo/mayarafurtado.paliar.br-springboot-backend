package br.paliar.mayarafurtado.backend.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationModel;
import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationResponseDTO;
import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationRole;
import br.paliar.mayarafurtado.backend.repository.ReactivationRepository;
import br.paliar.mayarafurtado.backend.service.ReactivationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactivationServiceImplementation implements ReactivationService {

  private final ReactivationRepository reactivationRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public ReactivationServiceImplementation(ReactivationRepository reactivationRepository, ModelMapper modelMapper) {
    this.reactivationRepository = reactivationRepository;
    this.modelMapper = modelMapper;
  }

  public List<ReactivationResponseDTO> findAll() {
    List<ReactivationModel> reactivations = reactivationRepository.findAll();

    reactivations.forEach(reactivation -> {
      // Atualiza a classificação
      ReactivationRole classification = calculateClassification(reactivation.getLastService());
      reactivation.setClassification(classification);

      // Atualiza a data de reativação
      LocalDate reactivateIn = calculateReactivationDate(reactivation.getLastService());
      reactivation.setReactivateIn(reactivateIn);
    });

    // Salva as reativações atualizadas no banco de dados
    reactivationRepository.saveAll(reactivations);

    return reactivations.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public ReactivationResponseDTO toResponse(ReactivationModel reactivation) {
    ReactivationResponseDTO response = modelMapper.map(reactivation, ReactivationResponseDTO.class);
    if (reactivation.getPatient() != null) {
      response.setPatientId(reactivation.getPatient().getId());
      response.setPatientName(reactivation.getPatient().getName());
      response.setPatientPhone(reactivation.getPatient().getPhone());
    }

    return response;
  }

  private ReactivationRole calculateClassification(LocalDateTime lastService) {
    LocalDateTime now = LocalDateTime.now();
    long monthsDifference = ChronoUnit.MONTHS.between(lastService, now);

    if (monthsDifference <= 12) {
        return ReactivationRole.HOT; // Até 12 meses
    } else if (monthsDifference <= 24) {
        return ReactivationRole.QUIET; // Entre 1 e 2 anos
    } else {
        return ReactivationRole.COLD; // Mais de 2 anos
    }
  }

  private LocalDate calculateReactivationDate(LocalDateTime lastService) {
    // A próxima data de reativação é sempre 6 meses após o último atendimento
    return lastService.toLocalDate().plusMonths(6);
  }

  
}
