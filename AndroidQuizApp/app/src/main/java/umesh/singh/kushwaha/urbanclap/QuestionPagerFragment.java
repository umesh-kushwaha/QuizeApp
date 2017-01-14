package umesh.singh.kushwaha.urbanclap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import umesh.singh.kushwaha.urbanclap.model.Option;
import umesh.singh.kushwaha.urbanclap.model.Question;
import umesh.singh.kushwaha.urbanclap.model.QuestionList;

/**
 * Created by umesh on 7/1/17.
 */
public class QuestionPagerFragment extends Fragment {

    public interface OnquizeFinishListener{
        void onQuizeFinished(QuestionList questionList);
        void showMsg();
    }

    ViewPager mViewPager;
    QuestionList questionList;
    PagerAdapter adapter;
    Button btnNext;
    HashMap<Integer,Fragment> tags = new HashMap<>();
    List<Question> displayQuestionList = new ArrayList<>();
    TreeSet<Integer> displayQuestionIndex = new TreeSet<>();
    OnquizeFinishListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnquizeFinishListener) getActivity();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_question_pager,container,false);
        mViewPager = (ViewPager) view.findViewById(R.id.question_pager);
        btnNext = (Button) view.findViewById(R.id.btn_next);

        questionList = (QuestionList) getArguments().getSerializable("question_list");

        if(questionList != null){
            displayQuestionList.add(questionList.getQuestions().get(0));
            displayQuestionIndex.add(questionList.getQuestions().get(0).getId());
            adapter = new PagerAdapter(getChildFragmentManager());
            mViewPager.setAdapter(adapter);
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("test","next button clicked");
                nextButtonClicked();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(listener != null){
            listener.showMsg();
        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            QuestionFragment questionFragment = new QuestionFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("question",displayQuestionList.get(position));
            questionFragment.setArguments(bundle);

            tags.put(position,questionFragment);


            return questionFragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            tags.remove(position);
        }

        @Override
        public int getCount() {
            return displayQuestionList.size();
        }


    }


    public void nextButtonClicked(){
        /**
         * question is answered or not
         */
        Question question = null;
        Fragment fragment = tags.get(mViewPager.getCurrentItem());


        if(fragment != null){
            Log.i("test","next button clicked");
            question = ((QuestionFragment)fragment).getQuestionAnswer();
            Log.i("test","next button clicked" + question.getQuestion());
        }else{
            Log.i("test","next button clicked fragment null" + tags.get(mViewPager.getCurrentItem()));
        }

        if(question != null){

            if(question.isAnswered()){
                if(questionList.getQuestions().size() == displayQuestionList.size()){
                    /**
                     * show questionlist fragment with answer that user selected
                     */
                    if(listener != null){
                        questionList.setQuestions(displayQuestionList);
                        listener.onQuizeFinished(questionList);
                    }
                }else{
                    /**
                     * find which question should be displayed next
                     */
                    displayNextQuestion(question);
                }

            }else{
                /**
                 * show toast message to user
                 */
                Toast.makeText(getActivity(),"Please answer the question",Toast.LENGTH_LONG).show();
            }
        }
        /**
         * if question is answered then check, is last question. if not answered show toast please answer
         */

    }


    private void displayNextQuestion(Question question){

        if(question.getQuestionType() == Utils.QuestionType.SINGLE_SELECTION.getValue() ||
                question.getQuestionType() == Utils.QuestionType.MULTI_SELECTION.getValue()){
            /**
             * find the selected option and next id and check it is exit in display index if not add
             */
            boolean isNextQuestionSelected = false;
            List<Option> optionList = question.getOptions();
            for(Option option:optionList){
                Log.i("test","option : "+option.isOptionSelected() +", next id : "+option.getNextQId() + "list : " + displayQuestionIndex);
                if(option.isOptionSelected() && option.getNextQId()!= 0 && !displayQuestionIndex.contains(option.getNextQId())){
                    displayQuestionIndex.add(option.getNextQId());
                    displayQuestionList.add(findQuestionById(option.getNextQId()));
                    isNextQuestionSelected = true;
                    Log.i("test","option id: after add "+displayQuestionList.get(2).getOptions().get(0).getTitle());
                }
            }
            if(!isNextQuestionSelected){
                displayQuestionList.add(findNextQuestion());
            }
        }else {
            displayQuestionList.add(findNextQuestion());
        }

        updateAdapter();



    }

    private void updateAdapter(){
        if(adapter != null){
            int currentPosition = mViewPager.getCurrentItem();
            adapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(currentPosition+1);
        }
    }

    private Question findQuestionById(int id){
        List<Question> questionList1 = questionList.getQuestions();

        for(Question question: questionList1){
            if(question.getId() == id){
                Log.i("test","option : "+question.getId());

                return question;
            }
        }
        return null;
    }

    private Question findNextQuestion(){
        List<Question> questionList1 = questionList.getQuestions();

        for(Question question: questionList1){
            if(!displayQuestionIndex.contains(question.getId())){
                displayQuestionIndex.add(question.getId());
                return question;
            }
        }
        return null;
    }


}
