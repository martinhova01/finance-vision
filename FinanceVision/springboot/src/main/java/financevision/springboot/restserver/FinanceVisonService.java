package financevision.springboot.restserver;

import core.Account;
import core.Expense;
import core.FinanceVisionModel;
import core.Income;
import core.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 * Stores and generates the model in the remote endpoint.
 */
@Service
public class FinanceVisonService {

    private FinanceVisionModel model;
    

    public FinanceVisonService() {
        this.model = createExampleModel();
    }

    public FinanceVisionModel getModel() {
        return model;
    }

    public void setModel(FinanceVisionModel model) {
        this.model = model;
    }

    private static FinanceVisionModel createExampleModel() {
        //TODO:  try to read from the exampleData.json file
        Account account = new Account(1000);
        Income vippsIncome =
            new Income("Vipps", 500, "Salary", LocalDateTime.now());
        Income income1 =
            new Income("Money from granny", 1000.0, "Other", LocalDateTime.now().minusDays(1));
        Income income2 = 
            new Income("Money from dad", 200.0, "Other", LocalDateTime.now().minusWeeks(1));
        Income income3 = 
            new Income("Salary", 2500.0, "Other", LocalDateTime.now().minusMonths(1));
        Income income4 = 
            new Income("Christmas present", 100.0, "Other", LocalDateTime.now().minusYears(1));
        Expense foodExpense = 
            new Expense("Food", 100, "Food", LocalDateTime.now());
        account.addTransaction(vippsIncome);
        account.addTransaction(income1);
        account.addTransaction(income2);
        account.addTransaction(income3);
        account.addTransaction(income4);
        account.addTransaction(foodExpense);
        User user = new User("testuser", "password", "Test User", "test@valid.com", account);
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);
        return model;
        
    }
    
}
