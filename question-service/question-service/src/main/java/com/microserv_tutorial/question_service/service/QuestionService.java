package com.microserv_tutorial.question_service.service;


import com.microserv_tutorial.question_service.model.Question;
import com.microserv_tutorial.question_service.model.QuestionWrapper;
import com.microserv_tutorial.question_service.model.Response;
import com.microserv_tutorial.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionRepository.save(question);
            return new ResponseEntity<>("Succesfully Created", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Unable to Create", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer questionId) {
        try{
            questionRepository.deleteById(questionId);
            return new ResponseEntity<>("Question Deleted Succesfully", HttpStatusCode.valueOf(204));
        }catch (Exception e){
            e.printStackTrace();
        }
            return new ResponseEntity<>("Error while Deleting", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity updateQuestion(Question question) {
        try{
            questionRepository.save(question);
            return new ResponseEntity<>("Question Updated Succesfully", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("Error while Updating", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQ) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQ);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> questions = new ArrayList<>();
        for(Integer qid: questionIds){
            Optional<Question> question = questionRepository.findById(qid);
            QuestionWrapper qw = new QuestionWrapper(question.get().getId(),question.get().getQuestionTitle(),question.get().getOption1(),question.get().getOption2(), question.get().getOption3(), question.get().getOption4());
            questions.add(qw);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score = 0;
        for(Response response:responses){
            Optional<Question> question = questionRepository.findById(response.getId());
            if(question.get().getRightAnswer().equals(response.getResponse())){
                score += 1;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
