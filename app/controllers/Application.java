package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import judger.Judger;
import models.Submit;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.OJException;
import views.html.test;

public class Application extends Controller {

    public static Result index() {
        return ok("Judger is running.");
    }

    public static Result test() {
        return ok(test.render());
    }

    public static Result submit() {
        ObjectNode result = Json.newObject();
        DynamicForm requestData = Form.form().bindFromRequest();
        try {
            String source = requestData.get("source");
            long id = Long.parseLong(requestData.get("id"));
            int language = Integer.parseInt(requestData.get("language"));
            int problemId = Integer.parseInt(requestData.get("problemId"));
            long problemTimeStamp = Long.parseLong(requestData.get("problemTimeStamp"));
            Submit find = Submit.find.byId(id);
            if (find != null) {
                throw new OJException(1001, "Submit is in queue.");
            }
            Submit submit = new Submit();
            submit.id = id;
            submit.problemId = problemId;
            submit.problemTimeStamp = problemTimeStamp;
            submit.source = source;
            System.out.println(submit.source);
            submit.save();
            Judger.start();
            result.put("error", 0);
        } catch (OJException ex) {
            result.put("error", ex.getCode());
            result.put("message", ex.getMessage());
        } catch (NumberFormatException ex) {
            result.put("error", 2001);
            result.put("message", "[NumberFormatException]" + ex.getMessage());
        }
        return ok(result);
    }

}
