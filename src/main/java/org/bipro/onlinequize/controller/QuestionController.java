package org.bipro.onlinequize.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bipro.onlinequize.model.Question;
import org.bipro.onlinequize.service.IQuestionService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quiz")
public class QuestionController {
    private IQuestionService iQuestionService;

    @PostMapping("/create-question")

    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        Question createdQuestion = iQuestionService.createQuestion(question);

        return  ResponseEntity.status(CREATED).body(createdQuestion);
    }

    @GetMapping("/all-questions")

    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questionList = iQuestionService.getAllQuestions();
        return ResponseEntity.ok(questionList);
    }


    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> question = iQuestionService.getQuestionById(id);
        if(question.isPresent()){
            return ResponseEntity.ok(question.get());
        } else{
            throw new ChangeSetPersister.NotFoundException();

        }
    }


    @PutMapping("/question/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {

        Question theQuestion = iQuestionService.updateQuestion(id,question);

        return ResponseEntity.ok(theQuestion);
    }

    @DeleteMapping("question/{id}")

    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        iQuestionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/subjects")

    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = iQuestionService.getAllSubjects();

        return ResponseEntity.ok(subjects);
    }

    @GetMapping("user-question")
    public ResponseEntity<List<Question>> getQuestionForUser(@RequestParam Integer numberOfQuestion,String subject){
        List<Question> allQuestions = iQuestionService.getQuestionForUser(numberOfQuestion,subject);
        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(numberOfQuestion,mutableQuestions.size());

        List<Question> randomQuestions = mutableQuestions.subList(0,availableQuestions);

        return ResponseEntity.ok(randomQuestions);
    }

}

