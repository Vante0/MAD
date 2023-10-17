package com.example.quizapp.views;

import android.app.MediaRouteButton;
import android.app.Notification;
import android.text.DynamicLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.BreakIterator;

public class FragmentQuizBinding {
    public View option1Btn;
    public View option2Btn;
    public View option3Btn;
    public View nextQueBtn;
    public View closeQuizBtn;
    public MediaRouteButton ansFeedbackTv;
    public BreakIterator quizQuestionTv;
    public BreakIterator countTimeQuiz;
    public Notification.Builder quizCoutProgressBar;
    public DynamicLayout.Builder quizQuestionsCount;

    public static FragmentQuizBinding inflate(LayoutInflater inflater, ViewGroup container, boolean b) {
        return null;
    }

    public View getRoot() {
        return null;
    }
}
