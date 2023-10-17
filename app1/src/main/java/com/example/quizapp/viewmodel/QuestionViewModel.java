package com.example.quizapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class QuestionViewModel extends ViewModel {
    private final MutableLiveData<HashMap<String, Long>> resultLiveData = new MutableLiveData<>();
    private String quizId;

    public LiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultLiveData;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
        // You can update the LiveData with actual results using Firebase or any other data source here.
        // For example, you can observe a Firebase database or make API calls.
        // Once you have the results, update the resultLiveData.
        // resultLiveData.setValue(yourResults);
    }

    public void getResults() {
    }

    public void addResults(HashMap<String, Object> resultMap) {
    }

    public void getQuestions() {
    }

    public LiveData<Object> getQuestionMutableLiveData() {
        return null;
    }

    public void fetchQuestions() {
    }

    public LiveData<Object> getQuestionLiveData() {
        return null;
    }
}
