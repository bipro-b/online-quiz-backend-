package org.bipro.onlinequize.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String question;
    @NotBlank(message = "")
    private String subject;

    @NotBlank
    private String questionType;
    @NotBlank
    @ElementCollection
    private List<String> choices;
    @NotBlank
    @ElementCollection
    private List<String> correctAnswers;

}
