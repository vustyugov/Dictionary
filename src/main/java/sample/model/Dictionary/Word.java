package sample.model.Dictionary;

import java.io.Serializable;
import java.util.*;

/**
 * Created by VAUst on 30.10.2016.
 */
public class Word implements Comparable, Serializable {
    private String value;
    private List<Category> categories;
    private String description;

    public Word(String value) {
        this.value = value;
        categories = new LinkedList<Category> ();
    }

    public String getValue() {
        return value;
    }

    public void setCategories(List<Category> list) {
        if (list == null) {
            categories = null;
            return;
        }
        for (Category category: list) {
            categories.add(category);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategories (List<Category> list) {
        categories.addAll(list);
    }

    public void addCategory (Category category) {
        categories.add(category);
    }

    public boolean containsCategory(String categoryName) {
        for (Category category: categories) {
            if (category.isCategory(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public Category getCategory(String categoryName) {
        for (Category category: categories) {
            if (category.isCategory(categoryName)) {
                return category;
            }
        }
        return null;
    }

    public void removeCategory (String categoryName) {
        if (containsCategory(categoryName)) {
            categories.remove(getCategory(categoryName));
        }
    }

    public void removeCategory (Category category) {
        this.removeCategory(category.getValue());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Object o) {
        int index = 0;
        if (o instanceof Word) {
            if (this.getValue().length()>((Word) o).getValue().length()) {
                for (int i = 0; i < this.getValue().length();i++){
                    index = this.getValue().compareTo(((Word) o).getValue());
                }

            }
        }
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null & this == null) {
            return false;
        } else if (obj != null) {
            if (obj instanceof Word) {
                if (this.value.equals(((Word) obj).getValue())
                        & this.description.hashCode() == ((Word) obj).getDescription().hashCode()) {
                    boolean flag = false;
                    for (Category category: ((Word) obj).getCategories()) {
                        if (this.getCategories().contains(category)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        return true;
                    }
                    else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
