package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.LabelDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewLabelDto;
import dev.akorovai.AdvancedToDoAPI.entity.Label;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.DuplicateLabelException;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.EmptyLabelListException;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.LabelNotFoundException;
import dev.akorovai.AdvancedToDoAPI.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LabelServiceImpl implements LabelService{
    private final LabelRepository labelRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<LabelDto> getAllLabels() throws EmptyLabelListException {
        List<LabelDto> labelDtoList = labelRepository.findAll().stream().map((category) -> modelMapper.map(category, LabelDto.class)).toList();
        log.info("Retrieved all labels successfully.");
        return Optional.of(labelDtoList).filter(list -> !list.isEmpty()).orElseThrow(EmptyLabelListException::new);
    }

    @Override
    public LabelDto addNewLabel(NewLabelDto newLabelDto) throws DuplicateLabelException {
        if (labelRepository.existsByTitle(newLabelDto.getTitle())) {
            throw new DuplicateLabelException( newLabelDto.getTitle() );
        }
        Label savedLabel = labelRepository.save(modelMapper.map(newLabelDto, Label.class));
        log.info("New label added successfully: {}", savedLabel.getTitle());
        return modelMapper.map(savedLabel, LabelDto.class);
    }

    @Override
    public LabelDto modifyLabel(Long IdLabel, NewLabelDto modifiedLabelDto) throws DuplicateLabelException, LabelNotFoundException {
        if (labelRepository.existsByTitle(modifiedLabelDto.getTitle())) {
            throw new DuplicateLabelException( modifiedLabelDto.getTitle());
        }

        Optional<Label> oldLabelOptional = labelRepository.findById(IdLabel);

        if (oldLabelOptional.isEmpty()) {
            throw new LabelNotFoundException( IdLabel );
        }

        Label oldLabel = oldLabelOptional.get();
        oldLabel.setTitle(modifiedLabelDto.getTitle());

        Label modifiedLabel = labelRepository.save(oldLabel);
        log.info("Modified category successfully. ID: {}, New Name: {}", IdLabel, modifiedLabelDto.getTitle());

        return modelMapper.map(modifiedLabel, LabelDto.class);
    }

    @Override
    public void deleteLabelById(Long IdLabel) {
        Optional<Label> labelToDelete = labelRepository.findById(IdLabel);

        if (labelToDelete.isEmpty()) {
            throw new LabelNotFoundException( IdLabel );
        }

        labelRepository.deleteById(IdLabel);
    }
}
