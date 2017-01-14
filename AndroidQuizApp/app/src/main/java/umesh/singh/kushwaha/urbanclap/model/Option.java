
package umesh.singh.kushwaha.urbanclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Option implements Serializable{

    @SerializedName("option_id")
    @Expose
    private Integer optionId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("next_q_id")
    @Expose
    private Integer nextQId;

    private boolean isOptionSelected = false;

    public boolean isOptionSelected() {
        return isOptionSelected;
    }

    public void setOptionSelected(boolean optionSelected) {
        isOptionSelected = optionSelected;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNextQId() {
        return nextQId;
    }

    public void setNextQId(Integer nextQId) {
        this.nextQId = nextQId;
    }

}
