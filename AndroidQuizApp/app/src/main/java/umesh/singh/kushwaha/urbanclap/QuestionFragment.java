package umesh.singh.kushwaha.urbanclap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import umesh.singh.kushwaha.urbanclap.model.Option;
import umesh.singh.kushwaha.urbanclap.model.Question;

/**
 * Created by umesh on 7/1/17.
 */
public class QuestionFragment extends Fragment{

    Question question;

    TextView questionTv;
    EditText editTextAns;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    List<Option> option;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable("question");
        option = question.getOptions();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        if(question.getQuestionType() == Utils.QuestionType.MULTI_SELECTION.getValue()){
            view = inflater.inflate(R.layout.f_multi_selection_questions,container,false);
            questionTv = (TextView) view.findViewById(R.id.question_tv);
            handleMultiSelectionView(view);
        }else if(question.getQuestionType() == Utils.QuestionType.SINGLE_SELECTION.getValue()){
            view = inflater.inflate(R.layout.f_single_selection,container,false);
            questionTv = (TextView) view.findViewById(R.id.question_tv);
            handleSingleSelectionView(view);

        }else if(question.getQuestionType() == Utils.QuestionType.TEXT_INPUT.getValue()){
            view = inflater.inflate(R.layout.f_written_answer,container,false);
            questionTv = (TextView) view.findViewById(R.id.question_tv);
            editTextAns = (EditText) view.findViewById(R.id.edt_answer);
        }
        questionTv.setText(question.getQuestion());
        return view;
    }


    private void handleMultiSelectionView(View view){
        checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4);
        checkBox1.setText(option.get(0).getTitle());
        checkBox2.setText(option.get(1).getTitle());
        checkBox3.setText(option.get(2).getTitle());
        checkBox4.setText(option.get(3).getTitle());
        if(question.isAnswered()){
            checkBox1.setChecked(option.get(0).isOptionSelected());
            checkBox2.setChecked(option.get(1).isOptionSelected());
            checkBox3.setChecked(option.get(2).isOptionSelected());
            checkBox4.setChecked(option.get(3).isOptionSelected());
        }

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                option.get(0).setOptionSelected(checkBox1.isChecked());

            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                option.get(1).setOptionSelected(checkBox2.isChecked());
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                option.get(2).setOptionSelected(checkBox3.isChecked());
                }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                option.get(3).setOptionSelected(checkBox4.isChecked());
            }
        });
    }


    private void handleSingleSelectionView(View view){
        radioButton1 = (RadioButton) view.findViewById(R.id.checkBox1);
        radioButton2 = (RadioButton) view.findViewById(R.id.checkBox2);
        radioButton3 = (RadioButton) view.findViewById(R.id.checkBox3);
        radioButton4 = (RadioButton) view.findViewById(R.id.checkBox4);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        radioButton1.setText(option.get(0).getTitle());
        radioButton2.setText(option.get(1).getTitle());
        radioButton3.setText(option.get(2).getTitle());
        radioButton4.setText(option.get(3).getTitle());

        if(question.isAnswered()){
            radioButton1.setChecked(option.get(0).isOptionSelected());
            radioButton2.setChecked(option.get(1).isOptionSelected());
            radioButton3.setChecked(option.get(2).isOptionSelected());
            radioButton4.setChecked(option.get(3).isOptionSelected());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                option.get(0).setOptionSelected(radioButton1.isChecked());
                option.get(1).setOptionSelected(radioButton2.isChecked());
                option.get(2).setOptionSelected(radioButton3.isChecked());
                option.get(3).setOptionSelected(radioButton4.isChecked());
                question.setAnswered(true);
            }
        });
    }

    public Question getQuestionAnswer(){
        if(question.getQuestionType() == Utils.QuestionType.TEXT_INPUT.getValue()
                &&   editTextAns.getText().toString().length() > 0 ){
            question.setAnswered(true);
            question.setAnswer(editTextAns.getText().toString());
        }else if(question.getQuestionType() == Utils.QuestionType.MULTI_SELECTION.getValue()){

            for(Option option1: option){
                if(option1.isOptionSelected()) {
                    question.setAnswered(true);
                    break;
                }
            }
            question.setOptions(option);
        }else{
            /**
             * for single selection update the option
             */
            question.setOptions(option);
        }


        return question;
    }
}
