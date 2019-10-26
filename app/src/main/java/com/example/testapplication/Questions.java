package com.example.testapplication;

public enum Questions {

    QUESTION1(1, "Question1", "true;1A", "false;1B", "false;1C", "false;1D"),
    QUESTION2(2, "Question2", "false;2A", "true;2B", "false;2C", "false;2D"),
    QUESTION3(3, "Question3", "false;3A", "false;3B", "true;3C", "false;3D"),
    QUESTION4(4, "Question4", "false;4A", "false;4B", "false;4C", "true;4D"),
    QUESTION5(5, "Question5", "true;5A", "false;5B", "false;5C", "false;5D"),
    QUESTION6(6, "Question6", "false;6A", "true;6B", "false;6C", "false;6D"),
    QUESTION7(7, "Question7", "false;7A", "false;7B", "true;7C", "false;7D"),
    QUESTION8(8, "Question8", "false;8A", "false;8B", "false;8C", "true;8D");

    public final int number;
    public final String question;
    public final String answer2;
    public final String answer3;
    public final String answer1;
    public final String answer4;

    Questions(int number, String question, String answer1, String answer2, String answer3, String answer4) {
        this.number = number;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }
}
