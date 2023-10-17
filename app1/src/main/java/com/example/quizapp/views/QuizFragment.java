package com.example.quizapp.views;

import static com.example.quizapp.R.id.action_quizFragment_to_resultFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentQuizBinding;
import com.example.quizapp.viewmodel.QuestionViewModel;
public class QuizFragment extends Fragment implements View.OnClickListener {
    private FragmentQuizBinding binding;
    private QuestionViewModel viewModel;
    private NavController navController;
    private long totalQuestions;
    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private int notAnswered = 0;
    private int correctAnswer = 0;
    private int wrongAnswer = 0;
    private String answer = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.option1Btn.setOnClickListener(this);
        binding.option2Btn.setOnClickListener(this);
        binding.option3Btn.setOnClickListener(this);
        binding.nextQueBtn.setOnClickListener(this);

        binding.closeQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(action_quizFragment_to_resultFragment);
            }
        });

        QuizFragmentArgs args = QuizFragmentArgs.fromBundle(getArguments());
        totalQuestions = args.getTotalQueCount();
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())).get(QuestionViewModel.class);
        viewModel.setQuizId(args.getQuizId());
        viewModel.getQuestions();

        loadData();
    }

    private void loadData() {
        enableOptions();
        loadQuestions(1);
    }

    private void enableOptions() {
        binding.option1Btn.setVisibility(View.VISIBLE);
        binding.option2Btn.setVisibility(View.VISIBLE);
        binding.option3Btn.setVisibility(View.VISIBLE);
        binding.option1Btn.setEnabled(true);
        binding.option2Btn.setEnabled(true);
        binding.option3Btn.setEnabled(true);
        binding.ansFeedbackTv.setVisibility(View.INVISIBLE);
        binding.nextQueBtn.setVisibility(View.INVISIBLE);
    }

    private void loadQuestions(int i) {
        currentQueNo = i;
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), questionModels -> {
            if (questionModels != null && questionModels instanceof QuestionModel) {
                QuestionModel question = (QuestionModel) questionModels;
                binding.quizQuestionTv.setText(currentQueNo + ") " + question.getQuestion());
                binding.option1Btn.setText(String.valueOf(question.getOption_a()));
                binding.option2Btn.setText(String.valueOf(question.getOption_b()));
                binding.option3Btn.setText(String.valueOf(question.getOption_c()));
                timer = question.getTimer();
                answer = question.getAnswer();
                startTimer();
            }
        });
    }


    private void startTimer() {
        binding.countTimeQuiz.setText(String.valueOf(timer));
        binding.quizCoutProgressBar.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.countTimeQuiz.setText(millisUntilFinished / 1000 + "");
                long percent = millisUntilFinished / (timer * 10);
                binding.quizCoutProgressBar.setProgress((int) percent);
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                binding.ansFeedbackTv.setText("Time's Up! No answer selected");
                notAnswered++;
                showNextBtn();
            }
        }.start();
    }

    private void showNextBtn() {
        if (currentQueNo == totalQuestions) {
            binding.nextQueBtn.setText("Submit");
            binding.nextQueBtn.setEnabled(true);
            binding.nextQueBtn.setVisibility(View.VISIBLE);
        } else {
            binding.nextQueBtn.setVisibility(View.VISIBLE);
            binding.nextQueBtn.setEnabled(true);
            binding.ansFeedbackTv.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.option1Btn) {
            verifyAnswer(binding.option1Btn);
        } else if (v.getId() == R.id.option2Btn) {
            verifyAnswer(binding.option2Btn);
        } else if (v.getId() == R.id.option3Btn) {
            verifyAnswer(binding.option3Btn);
        } else if (v.getId() == R.id.nextQueBtn) {
            if (currentQueNo == totalQuestions) {
                submitResults();
            } else {
                currentQueNo++;
                loadQuestions(currentQueNo);
                resetOptions();
            }
        }
    }



    private void resetOptions() {
        binding.ansFeedbackTv.setVisibility(View.INVISIBLE);
        binding.nextQueBtn.setVisibility(View.INVISIBLE);
        binding.nextQueBtn.setEnabled(false);
        binding.option1Btn.setBackground(requireContext().getDrawable(R.color.light_sky));
        binding.option2Btn.setBackground(requireContext().getDrawable(R.color.light_sky));
        binding.option3Btn.setBackground(requireContext().getDrawable(R.color.light_sky));
    }

    private void submitResults() {
        // Implement submitting results logic
        // Create a HashMap with the results and navigate to the result fragment
    }

    private void verifyAnswer(Button button) {
        if (canAnswer) {
            if (answer.equals(button.getText())) {
                button.setBackground(requireContext().getDrawable(R.color.green));
                correctAnswer++;
                binding.ansFeedbackTv.setText("Correct Answer");
            } else {
                button.setBackground(requireContext().getDrawable(R.color.red));
                wrongAnswer++;
                binding.ansFeedbackTv.setText("Wrong Answer\nCorrect Answer: " + answer);
            }
        }
        canAnswer = false;
        countDownTimer.cancel();
        showNextBtn();
    }
}
