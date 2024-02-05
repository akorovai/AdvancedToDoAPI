package dev.akorovai.AdvancedToDoAPI.services;

import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.LabelDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewLabelDto;
import dev.akorovai.AdvancedToDoAPI.exceptions.*;

import java.util.List;

public interface LabelService {
    List<LabelDto> getAllLabels() throws EmptyLabelListException;

    LabelDto addNewLabel(NewLabelDto newLabelDto) throws DuplicateLabelException;

    LabelDto modifyLabel(Long IdLabel, NewLabelDto newLabelDto) throws DuplicateLabelException, LabelNotFoundException;
}
