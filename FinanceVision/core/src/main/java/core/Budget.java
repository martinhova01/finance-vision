package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains logic for a budget.
 */
public class Budget {
    private Map<String, Double> categoryLimits;

    /**
     * Creates a new budget.
     */
    public Budget() {
        this.categoryLimits = new HashMap<>();
    }

    /**
     * Sets a limit for a given category.
     *
     * @param category the given category
     * @param limit the given limit
     */
    public void addCategory(String category, double limit) {
        if (category.equals("")) {
            throw new IllegalArgumentException("empty category");
        }
        categoryLimits.put(category, limit);
    }

    public List<String> getCategories() {
        return new ArrayList<>(categoryLimits.keySet());
    }

    public double getLimit(String category) {
        return categoryLimits.get(category);
    }
}
