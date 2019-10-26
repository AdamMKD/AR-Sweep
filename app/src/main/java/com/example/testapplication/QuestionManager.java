package com.example.testapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuestionManager {

    private static ArrayList<Integer> questionPick = new ArrayList<>();
    // todo: Need to sort out the questions so that it is not repeated.
    private Questions questions;
    private Random random = new Random();
    private ArrayList<String> displayOptions = new ArrayList<>();
    private ArrayList<String> allOptions = new ArrayList<>();
    private ArrayList<Integer> optionPick = new ArrayList<>(Arrays.asList(1, 2, 3, 4));


    public QuestionManager() {
        // This gets a random number based on the number of enums we have.
        int givenQuestion = random.nextInt(Questions.values().length) + 1;
        while (questionPick.contains(givenQuestion) && questionPick.size() != Questions.values().length) {
            givenQuestion = random.nextInt(Questions.values().length) + 1;
        }
        if (questionPick.size() == Questions.values().length) {
            Log.w("Full Array", "RAN OUT OF QUESTIONSSSSSSSSSSS");
            questionPick.clear();
        }

        questionPick.add(givenQuestion);

        // This sets the questions field based on the random number we got and puts it's answers in an array.
        for (Questions value : Questions.values()) {
            if (value.number == givenQuestion) {
                questions = value;
                displayOptions.add(questions.answer1);
                displayOptions.add(questions.answer2);
                displayOptions.add(questions.answer3);
                displayOptions.add(questions.answer4);
            }
        }
        allOptions.addAll(displayOptions);
    }

    public String getQuestion() {
        return questions.question;
    }

    public String pickOption() {
        int optionNumber = random.nextInt(optionPick.size());
        optionPick.remove(optionNumber);

        String option = displayOptions.get(optionNumber).split(";")[1];
        displayOptions.remove(optionNumber);

        return option;
    }

    public ArrayList<String> getDisplayOptions() {
        return allOptions;
    }

    public Questions getQuestionEnum() {
        return questions;
    }
}