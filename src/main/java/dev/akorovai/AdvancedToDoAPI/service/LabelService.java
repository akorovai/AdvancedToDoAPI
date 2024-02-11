package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.LabelDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewLabelDto;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.DuplicateLabelException;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.EmptyLabelListException;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.LabelNotFoundException;

import java.util.List;

public interface LabelService {
    List<LabelDto> getAllLabels() throws EmptyLabelListException;

    LabelDto addNewLabel(NewLabelDto newLabelDto) throws DuplicateLabelException;

    LabelDto modifyLabel(Long IdLabel, NewLabelDto newLabelDto) throws DuplicateLabelException, LabelNotFoundException;

    void deleteLabelById(Long IdLabel);
}
