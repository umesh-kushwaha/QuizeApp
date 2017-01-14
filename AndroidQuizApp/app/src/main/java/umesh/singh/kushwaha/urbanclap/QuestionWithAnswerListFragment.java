package umesh.singh.kushwaha.urbanclap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import umesh.singh.kushwaha.urbanclap.model.QuestionList;

/**
 * Created by umesh on 7/1/17.
 */
public class QuestionWithAnswerListFragment extends Fragment {

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_question_answer_list, container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(getActivity(),((QuestionList)getArguments().getSerializable("question_list")).getQuestions());
        recyclerView.setAdapter(adapter);
        return view;
    }
}
