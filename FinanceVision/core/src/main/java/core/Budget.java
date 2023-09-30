package core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Budget {
    private Map<String, Double> categoryLimits;

    public Budget() {
        this.categoryLimits = new HashMap<>();
    }

    public void addCategory(String category, double limit){
        categoryLimits.put(category, limit);
    }

    public Collection<String> getCategories(){
        return categoryLimits.keySet();
    }

    public double getLimit(String category){
        return categoryLimits.get(category);
    }
}
