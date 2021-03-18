package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealInMemoryDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    MealDAO mealDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        mealDAO = new MealInMemoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String id = request.getParameter("id");

        switch (action) {
            case "delete":
                mealDAO.delete(Long.parseLong(id));
                response.sendRedirect("meals");

                log.debug("delete id: " + id);
                break;
            case "update":
                long index = Long.parseLong(id);
                request.setAttribute("meal", mealDAO.getById(index));
                request.getRequestDispatcher("/edit-create-meal.jsp?text=Update").forward(request, response);

                log.debug("forward to update id: " + id);
                break;
            default:
                List<MealTo> mealList = MealsUtil.filteredByStreams(
                        mealDAO.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);

                request.setAttribute("mealList", mealList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

                log.debug("open list meal");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String id = request.getParameter("id");

        Meal meal = new Meal(dateTime, description, calories);

        if (id == null || id.isEmpty()) {
            mealDAO.add(meal);

            log.debug("add id: " + meal.getId());
        } else {
            meal.setId(Long.parseLong(id));
            mealDAO.update(meal.getId(), meal);

            log.debug("updated id: " + id);
        }

        response.sendRedirect("meals");
    }
}
