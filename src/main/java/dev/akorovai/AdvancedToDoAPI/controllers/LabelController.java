package dev.akorovai.AdvancedToDoAPI.controllers;

import dev.akorovai.AdvancedToDoAPI.dto.LabelDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewLabelDto;
import dev.akorovai.AdvancedToDoAPI.exceptions.DuplicateLabelException;
import dev.akorovai.AdvancedToDoAPI.exceptions.EmptyLabelListException;
import dev.akorovai.AdvancedToDoAPI.exceptions.LabelNotFoundException;
import dev.akorovai.AdvancedToDoAPI.services.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/labels")
public class LabelController {
    private final LabelService labelService;

    @GetMapping
    public ResponseEntity<List<LabelDto>> getAllCategories() throws EmptyLabelListException {
        List<LabelDto> labelDtoList = labelService.getAllLabels();
        return ResponseEntity.ok().body(labelDtoList);
    }

    @PostMapping
    public ResponseEntity<LabelDto> addNewCategory(@RequestBody NewLabelDto newLabelDto) throws DuplicateLabelException {
        LabelDto response = labelService.addNewLabel(newLabelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{IdLabel}")
    public ResponseEntity<LabelDto> modifyCategory(@PathVariable Long IdLabel, @RequestBody NewLabelDto modifiedLabelDto) throws DuplicateLabelException, LabelNotFoundException {
        LabelDto response = labelService.modifyLabel(IdLabel, modifiedLabelDto);
        return ResponseEntity.ok().body(response);
    }
}
