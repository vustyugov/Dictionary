package sample.model.Dictionary;

import java.io.Serializable;

/**
 * Created by VAUst on 30.10.2016.
 */
public class Category implements Comparable,Serializable {
    private int id;
    private String value;
    private String description;

    public Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCategory(String categoryName) {
        if(value!=null) {
            if (value.equals(categoryName)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null & this == null) {
            return false;
        } else if (obj != null) {
            if (obj instanceof Category) {
                if (this.value.equals(((Category) obj).getValue())
                        & this.description.equals(((Category) obj).getDescription())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    @Override
    public int compareTo(Object o) {
        int index = 0;
        if (o instanceof Category) {
            if (this.getValue().length()>((Category) o).getValue().length()) {
                for (int i = 0; i < this.getValue().length();i++){
                    index = this.getValue().compareTo(((Category) o).getValue());
                }

            }
        }
        return index;
    }
}
