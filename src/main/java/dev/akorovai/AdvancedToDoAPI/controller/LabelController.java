package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.LabelDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewLabelDto;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.DuplicateLabelException;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.EmptyLabelListException;
import dev.akorovai.AdvancedToDoAPI.exception.labelException.LabelNotFoundException;
import dev.akorovai.AdvancedToDoAPI.service.LabelService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<LabelDto>> getAllLabels() throws EmptyLabelListException {
        List<LabelDto> labelDtoList = labelService.getAllLabels();
        return ResponseEntity.ok().body(labelDtoList);
    }

    @PostMapping
    public ResponseEntity<LabelDto> addNewLabel(@RequestBody NewLabelDto newLabelDto) throws DuplicateLabelException {
        LabelDto newLabel = labelService.addNewLabel(newLabelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLabel);
    }

    @PutMapping("/{IdLabel}")
    public ResponseEntity<LabelDto> modifyLabelById(@PathVariable Long IdLabel, @RequestBody NewLabelDto modifiedLabelDto) throws DuplicateLabelException, LabelNotFoundException {
        LabelDto response = labelService.modifyLabel(IdLabel, modifiedLabelDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{IdLabel}")
    public ResponseEntity<?> deleteLabelById(@PathVariable Long IdLabel){
        labelService.deleteLabelById(IdLabel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
