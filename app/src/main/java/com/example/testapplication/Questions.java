package com.example.testapplication;

public enum Questions {

    QUESTION1(1, "Who was the legendary Benedictine monk who invented champagne?", "true;Dom Pérignon", "false;Benedict of Nursia", "false;Bernard of Clairvaux", "false;Bede Griffiths"),
    QUESTION2(2, "Great Whites and Hammerheads are what type of animals?", "false;Fish", "true;Shark", "false;Whale", "false;homosapien"),
    QUESTION3(3, "In which year was Alaska sold to the U.S.?", "false;1674", "false;2010", "true;1867", "false;1958"),
    QUESTION4(4, "Which famous nurse was known as 'The Lady Of The Lamp' during the crimean war?", "false;Cleopatra", "false;Mother Teresa", "false;Mary Seacole", "true;Florence Nightingale"),
    QUESTION5(5, "Where did the game of badminton originate?", "true;Europe", "false;America", "false;Brazil", "false;Japan"),
    QUESTION6(6, "Name the largest freshwater lake in the world?", "false;Lake Superior", "true;Lake Baikal", "false;Lake Titicaca", "false;Lake District"),
    QUESTION7(7, "What’s the coloured part of the human eye called?", "false;Lens", "false;Pupil", "true;Iris", "false;Retina"),
    QUESTION8(8, "By what name are the young of frogs and toads known?", "false;Frog", "false;Falcor", "false;Metamorphosis", "true;Tadpole"),
    QUESTION9(9, "Based on their original names, Karol Wojtyla, Joseph Alois Ratzinger and Jorge Mario Bergoglio served which religious position?", "true;Pope", "false;Monk", "false; Priest", "false;None"),
    QUESTION10(10, "'All the world's a stage, and all the men and women merely players.' is a quote from which Shakespeare play?", "false;The Comedy of Errors", "true;As You Like It", "false;Romeo and Juliet", "false;Hamlet"),
    QUESTION11(11, "Which author, known for the poem 'If' said 'East is East and West is West and never the twain shall meet'?", "false;Edgar Allan Poe", "false;Robert Frost", "true;Rudyard Kipling", "false;William Shakespeare"),
    QUESTION12(12, "The English social reformer Octavia Hill formed which environmental and conservation organisation in 1895?", "true;The National Trust", "false;The Bank", "false;Prince's Rainforests Projec", "false;Green Alliance"),
    QUESTION13(13, "Which political leader was elected President of South Africa in 1994?", "true;Nelson Mandela", "false;None", "false;Jacob Zuma", "false;Cyril Ramaphosa"),
    QUESTION14(14, "Which ‘A’ is a soft stone of type gypsum, commonly used for carving and decorative pieces?", "false;Allanite", "true;Alabaster", "false;Alexandrite", "false;Amazonite"),
    QUESTION15(15, "Which British king abdicated in favour of his brother George VI after falling in love with Wallis Simpson?", "false;James VI", "true;Edward VIII", "false;James I", "false;John, King of England"),
    QUESTION16(16, "In 1768, the first edition of which book was published?", "true;Encyclopaedia Britannica", "false;The Bible", "false;Madrid Codex", "false;The Quran"),
    QUESTION17(17, "Which singer known for the songs ‘My Way’ and ‘New York, New York’ died in 1998?", "true;Frank Sinatra ", "false;Madonna", "false;Diana Ross", "false;Aretha Franklin"),
    QUESTION18(18, "Which cyclist became the first Briton to win the Tour de France race in 2012?", "false;Chris Froome", "false;Mark Cavendish", "true;Bradley Wiggins", "false;Chris Hoy"),
    QUESTION19(19, "When a teenage girl is possessed by a mysterious entity, two priests are tasked with saving her. What is the film title?", "true;The Exorcist", "false;The Conjuring", "false;Annabelle", "false;The Nun"),
    QUESTION20(20, "In 1854 a series of fires and explosions killed 54 people and injured hundreds more in which two north east towns?", "false;Durham and Hexham", "true;Newcastle and Gateshead", "false;Newcastle and Durham", "false;Hexham and Sunderland"),
    ;

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
