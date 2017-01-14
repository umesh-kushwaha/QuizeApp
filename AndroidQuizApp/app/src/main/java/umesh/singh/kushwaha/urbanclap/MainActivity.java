package umesh.singh.kushwaha.urbanclap;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import umesh.singh.kushwaha.urbanclap.model.QuestionList;

public class MainActivity extends AppCompatActivity implements QuestionPagerFragment.OnquizeFinishListener{

    QuestionList questionList;
    private ProgressBar mProgressBar;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        frameLayout = (FrameLayout) findViewById(R.id.question_container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new ParseJsonAsyncTask().execute();

    }

    @Override
    public void onQuizeFinished(QuestionList questionList) {

        addQuestionAnswerListFragment(questionList);
    }

    @Override
    public void showMsg() {
        showSnackBar();
    }

    class ParseJsonAsyncTask extends AsyncTask<Void,Void,Void>{



        @Override
        protected Void doInBackground(Void... voids) {
            questionList = Utils.parseQuestionJson(MainActivity.this);
            if(questionList != null){
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            frameLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            addquestionPagerFragment();
        }
    }

    private void addquestionPagerFragment(){
        if(questionList != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            QuestionPagerFragment fragment = new QuestionPagerFragment();
            Bundle bundle =new Bundle();
            bundle.putSerializable("question_list",questionList);
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.question_container, fragment,
                    QuestionPagerFragment.class.getName()).commit();
        }
    }

    private void addQuestionAnswerListFragment(QuestionList list){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        QuestionWithAnswerListFragment fragment = new QuestionWithAnswerListFragment();
        Bundle bundle =new Bundle();
        bundle.putSerializable("question_list",questionList);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.question_container, fragment,
                QuestionPagerFragment.class.getName()).commit();
    }

    private void showSnackBar(){
            Snackbar.make(findViewById(android.R.id.content), "To go back swipe left", Snackbar.LENGTH_LONG)
                                       .show();
    }
}
