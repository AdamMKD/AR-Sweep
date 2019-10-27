package com.example.testapplication;

public enum Questions {

    QUESTION1(1, "Who was the legendary Benedictine monk who invented champagne? Dom Perignon.", "true;1A", "false;1B", "false;1C", "false;1D"),
    QUESTION2(2, "Great Whites and Hammerheads are what type of animals?", "false;2A", "true;2B", "false;2C", "false;2D"),
    QUESTION3(3, "In which year was Alaska sold to the U.S.?", "false;3A", "false;3B", "true;3C", "false;3D"),
    QUESTION4(4, "Which famous nurse was known as “The Lady Of The Lamp” during the crimean war?", "false;4A", "false;4B", "false;4C", "true;4D"),
    QUESTION5(5, "Where did the game of badminton originate?", "true;5A", "false;5B", "false;5C", "false;5D"),
    QUESTION6(6, "Name the largest freshwater lake in the world?", "false;6A", "true;6B", "false;6C", "false;6D"),
    QUESTION7(7, "What’s the coloured part of the human eye called?", "false;7A", "false;7B", "true;7C", "false;7D"),
    QUESTION8(8, "By what name are the young of frogs and toads known?", "false;8A", "false;8B", "false;8C", "true;8D");

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
