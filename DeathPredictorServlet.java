import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeathPredictorServlet")
public class DeathPredictorServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));
        String[] selectedFoods = request.getParameterValues("food");

        // Validate the age and food selection
        if (age < 0 || age > 150) {
            showError(response, "Age must be between 0 and 150.");
            return;
        }

        if (selectedFoods == null || selectedFoods.length < 1 || selectedFoods.length > 5) {
            showError(response, "Please select 1 to 5 foods.");
            return;
        }

        // Calculate average toxicity
        int totalToxicity = 0;
        for (String food : selectedFoods) {
            int toxicity = getToxicity(food);
            totalToxicity += toxicity;
        }
        double avgToxicity = totalToxicity / selectedFoods.length;

        // Prediction logic
        String prediction;
        int yearsLeft = 0;
        
        if (gender.equals("male")) {
            if (age >= 1 && age <= 5 && avgToxicity > 40) {
                prediction = "Reduce toxicity level.";
                yearsLeft = 5;
            } else if (age >= 6 && age <= 20 && avgToxicity > 60) {
                prediction = "You will live for another 40 years.";
                yearsLeft = 40;
            } else if (age >= 21 && age <= 40 && avgToxicity > 55) {
                prediction = "You will live for another 35 years.";
                yearsLeft = 35;
            } else if (age >= 41 && age <= 60 && avgToxicity > 50) {
                prediction = "You will live for another 30 years.";
                yearsLeft = 30;
            } else if (age > 60 && avgToxicity > 45) {
                prediction = "You will live for another 25 years.";
                yearsLeft = 25;
            } else {
                prediction = "Your health is stable!";
                yearsLeft = 100;
            }
        } else {
            if (age >= 1 && age <= 5 && avgToxicity > 40) {
                prediction = "Reduce toxicity level.";
                yearsLeft = 5;
            } else if (age >= 6 && age <= 20 && avgToxicity > 55) {
                prediction = "You will live for another 37 years.";
                yearsLeft = 37;
            } else if (age >= 21 && age <= 40 && avgToxicity > 55) {
                prediction = "You will live for another 32 years.";
                yearsLeft = 32;
            } else if (age >= 41 && age <= 60 && avgToxicity > 50) {
                prediction = "You will live for another 27 years.";
                yearsLeft = 27;
            } else if (age > 60 && avgToxicity > 45) {
                prediction = "You will live for another 22 years.";
                yearsLeft = 22;
            } else {
                prediction = "Your health is stable!";
                yearsLeft = 100;
            }
        }

        // Output the result
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Prediction Result</title></head><body>");
        out.println("<h1>" + prediction + "</h1>");
        out.println("<p>Predicted: " + prediction + "</p>");
        out.println("<p>Years left: " + yearsLeft + "</p>");
        out.println("</body></html>");
    }

    private int getToxicity(String food) {
        switch (food) {
            case "Noodles": return 90;
            case "Biryani": return 60;
            case "Idly": return 5;
            case "Idiyappam": return 5;
            case "Chicken Fried Rice": return 50;
            case "Veg Fried Rice": return 35;
            case "White Rice": return 10;
            case "Fish": return 30;
            case "Fruits": return 3;
            case "Veg Biryani": return 20;
            default: return 0;
        }
    }

    private void showError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Error</title></head><body>");
        out.println("<h1>Error: " + message + "</h1>");
        out.println("</body></html>");
    }
}
