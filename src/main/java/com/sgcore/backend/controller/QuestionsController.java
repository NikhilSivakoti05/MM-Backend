package com.sgcore.backend.controller;



import com.sgcore.backend.model.Question;
import com.sgcore.backend.repository.QuestionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    private final QuestionRepository questionRepository;

    public QuestionsController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/{jobId}")
    public List<Question> getByJob(@PathVariable String jobId) {
        return questionRepository.findByJobId(jobId);
    }

    @PostMapping
    public Question create(@RequestBody Question q) {
        q.setId(null);
        return questionRepository.save(q);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> update(@PathVariable String id, @RequestBody Question updated) {
        Optional<Question> opt = questionRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Question q = opt.get();
        q.setQuestionText(updated.getQuestionText());
        q.setQuestionType(updated.getQuestionType());
        q.setOptions(updated.getOptions());
        questionRepository.save(q);
        return ResponseEntity.ok(q);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!questionRepository.existsById(id)) return ResponseEntity.notFound().build();
        questionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
