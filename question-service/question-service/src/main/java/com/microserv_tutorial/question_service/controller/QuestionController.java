package com.microserv_tutorial.question_service.controller;


import com.microserv_tutorial.question_service.model.Question;
import com.microserv_tutorial.question_service.model.QuestionWrapper;
import com.microserv_tutorial.question_service.model.Response;
import com.microserv_tutorial.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List <Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(
            @PathVariable String category
    ){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(
            @RequestBody Question question
    ){
      return questionService.addQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<String> updateQuestion(
            @RequestBody Question question
    ){
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/deleteQuestion/{questionId}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Integer questionId
    ){
        return questionService.deleteQuestion(questionId);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName,
            @RequestParam Integer numQ
    ){
        return questionService.getQuestionsForQuiz(categoryName, numQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(
            @RequestBody List<Integer> questionIds
    ){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(
            @RequestBody List<Response> responses
    ){
        return questionService.getScore(responses);
    }
}
