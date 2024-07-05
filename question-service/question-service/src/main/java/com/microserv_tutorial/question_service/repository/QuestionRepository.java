package com.microserv_tutorial.question_service.repository;


import com.microserv_tutorial.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category = ? ORDER BY RAND() LIMIT ?", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
