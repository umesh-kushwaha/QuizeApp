package umesh.singh.kushwaha.urbanclap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
public class QuestionAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Question> questionList ;
    Context context;

    public QuestionAnswerAdapter(Context context,List<Question> list){
        this.context = context;
        this.questionList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if(viewType == Utils.QuestionType.MULTI_SELECTION.getValue()){
            view = inflater.inflate(R.layout.view_multi_selection, parent, false);
            return new MultiSelectionViewHolder(view);
        }else if(viewType==Utils.QuestionType.SINGLE_SELECTION.getValue()){
            view = inflater.inflate(R.layout.view_single_selection, parent, false);
            return new SingleSlectionViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.f_written_answer, parent, false);
            return new TextInputViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == Utils.QuestionType.MULTI_SELECTION.getValue()){
            bindMultiViewHolder((MultiSelectionViewHolder) holder,position);
        }else if(getItemViewType(position)==Utils.QuestionType.SINGLE_SELECTION.getValue()){
            bindSingleSelectionViewHolder((SingleSlectionViewHolder) holder,position);
        }else{
            bindTextInputViewHolder((TextInputViewholder) holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return questionList.get(position).getQuestionType();
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    private void bindMultiViewHolder(MultiSelectionViewHolder viewHolder, int position){
        List<Option> optionList = questionList.get(position).getOptions();
        viewHolder.checkBox1.setText(optionList.get(0).getTitle());
        viewHolder.checkBox2.setText(optionList.get(1).getTitle());
        viewHolder.checkBox3.setText(optionList.get(2).getTitle());
        viewHolder.checkBox4.setText(optionList.get(3).getTitle());
        viewHolder.checkBox1.setChecked(optionList.get(0).isOptionSelected());
        viewHolder.checkBox2.setChecked(optionList.get(1).isOptionSelected());
        viewHolder.checkBox3.setChecked(optionList.get(2).isOptionSelected());
        viewHolder.checkBox4.setChecked(optionList.get(3).isOptionSelected());

        viewHolder.checkBox1.setEnabled(false);
        viewHolder.checkBox2.setEnabled(false);
        viewHolder.checkBox3.setEnabled(false);
        viewHolder.checkBox4.setEnabled(false);
        viewHolder.questionTv.setText(questionList.get(position).getQuestion());
    }

    private void bindSingleSelectionViewHolder(SingleSlectionViewHolder viewHolder, int position){
        List<Option> optionList = questionList.get(position).getOptions();
        viewHolder.radioButton1.setText(optionList.get(0).getTitle());
        viewHolder.radioButton2.setText(optionList.get(1).getTitle());
        viewHolder.radioButton3.setText(optionList.get(2).getTitle());
        viewHolder.radioButton4.setText(optionList.get(3).getTitle());
        viewHolder.radioButton1.setChecked(optionList.get(0).isOptionSelected());
        viewHolder.radioButton2.setChecked(optionList.get(1).isOptionSelected());
        viewHolder.radioButton3.setChecked(optionList.get(2).isOptionSelected());
        viewHolder.radioButton4.setChecked(optionList.get(3).isOptionSelected());

        viewHolder.radioButton1.setEnabled(false);
        viewHolder.radioButton2.setEnabled(false);
        viewHolder.radioButton3.setEnabled(false);
        viewHolder.radioButton4.setEnabled(false);
        viewHolder.questionTv.setText(questionList.get(position).getQuestion());
    }

    private void bindTextInputViewHolder(TextInputViewholder viewholder, int position){
        viewholder.questionTv.setText(questionList.get(position).getQuestion());
        viewholder.editTextAns.setText(questionList.get(position).getAnswer());
        viewholder.editTextAns.setEnabled(false);
    }
    class MultiSelectionViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
        TextView questionTv;
        View divider;
        public MultiSelectionViewHolder(View itemView) {
            super(itemView);
            questionTv = (TextView) itemView.findViewById(R.id.question_tv);
            checkBox1 = (CheckBox) itemView.findViewById(R.id.checkBox1);
            checkBox2 = (CheckBox) itemView.findViewById(R.id.checkBox2);
            checkBox3 = (CheckBox) itemView.findViewById(R.id.checkBox3);
            checkBox4 = (CheckBox) itemView.findViewById(R.id.checkBox4);
            divider = itemView.findViewById(R.id.divider);
            divider.setVisibility(View.VISIBLE);

        }
    }

    class SingleSlectionViewHolder extends RecyclerView.ViewHolder{
        RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
        TextView questionTv;
        View divider;
        public SingleSlectionViewHolder(View itemView) {
            super(itemView);
            questionTv = (TextView) itemView.findViewById(R.id.question_tv);
            radioButton1 = (RadioButton) itemView.findViewById(R.id.checkBox1);
            radioButton2 = (RadioButton) itemView.findViewById(R.id.checkBox2);
            radioButton3 = (RadioButton) itemView.findViewById(R.id.checkBox3);
            radioButton4 = (RadioButton) itemView.findViewById(R.id.checkBox4);
            divider = itemView.findViewById(R.id.divider);
            divider.setVisibility(View.VISIBLE);
        }
    }

    class TextInputViewholder extends RecyclerView.ViewHolder{
        TextView questionTv;
        EditText editTextAns;
        View divider;
        public TextInputViewholder(View itemView) {
            super(itemView);
            questionTv = (TextView) itemView.findViewById(R.id.question_tv);
            editTextAns = (EditText) itemView.findViewById(R.id.edt_answer);
            divider = itemView.findViewById(R.id.divider);
            divider.setVisibility(View.VISIBLE);
        }
    }
}
