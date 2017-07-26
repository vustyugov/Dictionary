package sample.model.Dictionary;

import java.util.*;
import java.util.regex.*;

/**
 * Created by VAUst on 30.10.2016.
 */
public class Dictionary {
    public static final String INCORRECT_CATEGORY = "запрещенные";
    public static final String ALL_CATEGORIES = "все";

    private List<Word> words;
    private List<Category> categories;
    private List<Word> oldWords;
    private List<Category> oldCategories;
    private static Dictionary ourInstance = new Dictionary();

    public static Dictionary getInstance() {
        Dictionary localInstance = ourInstance;
        if(localInstance == null) {
            synchronized (Dictionary.class) {
                localInstance = ourInstance;
                if (localInstance == null) {
                    ourInstance = localInstance = new Dictionary();
                }
            }
        }
        return ourInstance;
    }

    private Dictionary() {
        words = new LinkedList<Word>();
        categories = new LinkedList<Category>();
        oldWords = new LinkedList<Word> ();
        oldCategories = new LinkedList<Category>();
    }

    public void addWordInDictionary (Word word) {
        if (!this.containWord(word.getValue())) {
            words.add(word);
        }
        else {
            Word newWord = this.getWord(word.getValue());
            Word oldWord = new Word(newWord.getValue());
            oldWord.setDescription(newWord.getDescription());
            oldWord.setCategories(newWord.getCategories());

            newWord.setDescription(word.getDescription());
            newWord.getCategories().removeAll(newWord.getCategories());
            newWord.setCategories(word.getCategories());

            if (oldWords.size() != 0) {
                boolean contains = false;
                for (Word innerWord : oldWords) {
                    if (innerWord.getValue().equals(oldWord.getValue())) {
                        innerWord = oldWord;
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    oldWords.add(oldWord);
                }
            } else {
                oldWords.add(oldWord);
            }
        }
    }

    public void addWordsInDictionary(List<Word> list) {
        for (Word word: list) {
            addWordInDictionary(word);
        }
    }

    public void addCategoryInDictionary (Category category) {
        if (!this.containCategory(category.getValue())) {
            categories.add(category);
        }
        else {
            if (this.getCategories().contains(category)) {
                Category newCategory = this.getCategory(category.getValue());
                Category oldCategory = new Category(newCategory.getValue());
                oldCategory.setDescription(newCategory.getDescription());
                oldCategory = category;
                if (oldCategories.size() != 0) {
                    boolean contains = false;
                    for (Category innerCategory: oldCategories) {
                        if (innerCategory.getValue().equals(oldCategory.getValue())) {
                            innerCategory = oldCategory;
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        oldCategories.add(oldCategory);
                    }
                }
                else {
                    oldCategories.add(oldCategory);
                }

            }
        }
    }

    public void addCategoriesInDictionary(List<Category> list) {
        for (Category category: list) {
            addCategoryInDictionary(category);
        }
    }

    public Word getWord(String wordValue) {
        for (Word word: words) {
            if(word.getValue().equals(wordValue)) {
                return word;
            }
        }
        return null;
    }

    public boolean containWord (String wordValue) {
        if(this.getWord(wordValue)!=null) {
            return true;
        }
        return false;
    }

    public boolean containCategory (String categoryValue) {
        if (this.getCategory(categoryValue) != null) {
            return true;
        }
        return false;
    }

    public Collection<Word> getWords() {
        return this.words;
    }

    public Collection<String> getWords(String wordValue, List<String> categoryValue) {
        List<String> list = new LinkedList<String>();
        Pattern pattern = Pattern.compile(convertTemplate(wordValue));
        for (String category: categoryValue) {
            Collection<String> words = getWordsByCategory(category);
            for (String word: words) {
                Matcher matcher = pattern.matcher(word);
                if (matcher.matches()) {
                    list.add(word);
                }
            }
        }
        return list;
    }

    public Collection<String> getWords (String wordValue) {
        List<String> list = new LinkedList<String>();
        Pattern pattern = Pattern.compile(convertTemplate(wordValue));
        for(Word word: words) {
            Matcher matcher = pattern.matcher(word.getValue());
            if (matcher.matches()) {
                list.add(word.getValue());
            }
        }
        return list;
    }

    private String convertTemplate(String template) {
        String word = template.toUpperCase().trim();
        StringBuilder buf = new StringBuilder();
        for(int index = 0; index < word.length(); index++) {
            if(template.charAt(index) == '?') {
                buf.append('.');
            }
            else {
                buf.append(word.charAt(index));
            }
        }
        return buf.toString();
    }

    private Collection<String> getWordsByCategory(String... categoriesValue) {
        List<String> list = new LinkedList<String> ();
        for (String categoryValue: categoriesValue) {
            for(Word element: words) {
                if (element.containsCategory(categoryValue)) {
                    list.add(element.getValue());
                }
            }
        }
        return list;
    }

    public String getCategory (Category category) {
        return category.getValue();
    }

    public Category getCategory (String categoryValue) {
        for (Category category: categories) {
            if (category.getValue().equals(categoryValue)) {
                return category;
            }
        }
        return null;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public List<Word> getOldWords() {
        return oldWords;
    }

    public List<Category> getOldCategories() {
        return oldCategories;
    }
}
