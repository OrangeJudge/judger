package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Submit;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result submit() {
        ObjectNode result = Json.newObject();
        DynamicForm requestData = Form.form().bindFromRequest();
        try {
            String strId = requestData.get("id");
            String strProblemId = requestData.get("problemId");
            String strProblemTimeStamp = requestData.get("problemTimeStamp");
            String source = requestData.get("source");
            long id = Long.parseLong(strId);
            int problemId = Integer.parseInt(strProblemId);
            long problemTimeStamp = Long.parseLong(strProblemTimeStamp);
            Submit submit = new Submit();
            submit.id = id;
            submit.problemId = problemId;
            submit.problemTimeStamp = problemTimeStamp;
            submit.source = source;
            submit.save();
            result.put("error", 0);
        } catch (Exception e) {
            result.put("error", 1001);
            result.put("message", e.getMessage()    );
        }
        return ok(result);
    }

}
