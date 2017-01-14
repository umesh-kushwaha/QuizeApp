package umesh.singh.kushwaha.urbanclap;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import umesh.singh.kushwaha.urbanclap.model.QuestionList;

/**
 * Created by umesh on 7/1/17.
 */
public class Utils {

    public enum QuestionType{

        TEXT_INPUT(0),MULTI_SELECTION(1),SINGLE_SELECTION(2);
        int value;


         QuestionType(int vaule){
            this.value = vaule;
        }

        public int getValue(){
            return this.value;
        }
    }

    public static QuestionList parseQuestionJson(Context context){
        QuestionList questionList = null;
        String data = readJsonFromasset(context);
        if(data != null){
            Gson gson = new Gson();
            questionList = gson.fromJson(data,QuestionList.class);
        }

        return questionList;
    }

    private static String readJsonFromasset(Context context){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("questions.json")));

            // do reading, usually loop until end of file reading
            StringBuilder sb = new StringBuilder();
            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine); // process line
                mLine = reader.readLine();
            }
            reader.close();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
