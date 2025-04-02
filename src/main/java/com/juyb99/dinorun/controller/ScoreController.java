package com.juyb99.dinorun.controller;

import com.juyb99.dinorun.dto.ScoreRequestDTO;
import com.juyb99.dinorun.dto.ScoreResponseDTO;
import com.juyb99.dinorun.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public ResponseEntity<?> findAllScore() {
        List<ScoreResponseDTO> scores;
        try {
            scores = scoreService.findAllScore();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/{scoreId}")
    public ResponseEntity<?> findOneScore(@PathVariable(name = "scoreId") long id) {
        ScoreResponseDTO score;

        try {
            score = scoreService.findOneScore(id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().body(score);
    }

    @PostMapping
    public ResponseEntity<?> saveScore(@RequestBody ScoreRequestDTO score) {
        try {
            int rows = scoreService.saveScore(score);
            if (rows <= 0) {
                throw new Exception("Can't save score");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Score saved");
    }
}
