package controllers;

import models.Task;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static Form<Task> taskForm = Form.form(Task.class);

	public static Result index() {
		return redirect(routes.Application.tasks(0));
	}

	@Transactional(readOnly=true)
	public static Result tasks(int page) {
		return ok(views.html.index.render(Task.all(page, 10), taskForm));
	}

	@Transactional
	public static Result newTask() {
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(Task.all(), filledForm));
		} else {
			Task.create(filledForm.get());
			return redirect(routes.Application.tasks(0));
		}
	}

	@Transactional
	public static Result deleteTask(Long id) {
		  Task.delete(id);
		  return redirect(routes.Application.tasks(0));
		}

}
