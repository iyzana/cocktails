package de.randomerror.cocktails.backend.controller;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.dao.OrderDao;
import de.randomerror.cocktails.backend.dto.CreateOrderDto;
import de.randomerror.cocktails.backend.entity.CocktailOrder;
import de.randomerror.cocktails.backend.exception.NotFoundException;
import de.randomerror.cocktails.backend.service.InputService;
import de.randomerror.cocktails.backend.service.LoginService;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class OrderController {
    private static final String ROUTE = "/order";

    public static void registerRoutes() {
        get(ROUTE, (req, res) -> OrderDao.findByRequester(LoginService.getUniqueName(req)), App.gson::toJson);

        post(ROUTE, (req, res) -> {
            String requester = LoginService.getUniqueName(req);
            CreateOrderDto createOrder = App.gson.fromJson(req.body(), CreateOrderDto.class);
            CocktailOrder order = new CocktailOrder(requester, createOrder.getName(), InputService.buildInputs(createOrder.getInputs()));
            return OrderDao.save(order);
        }, App.gson::toJson);

        delete(ROUTE + "/:id", (req, res) -> {
            int id = parseInt(req.params("id"));
            CocktailOrder order = OrderDao.findById(id)
                    .orElseThrow(() -> new NotFoundException("Order", id));
            if (!order.getRequester().equals(LoginService.getUniqueName(req))) // check if allowed to delete the order
                throw new NotFoundException("Order", id);
            OrderDao.delete(order);
            MachineController.newOrder();
            res.status(204);
            return null;
        }, App.gson::toJson);
    }
}
